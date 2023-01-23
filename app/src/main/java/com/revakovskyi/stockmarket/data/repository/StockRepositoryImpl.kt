package com.revakovskyi.stockmarket.data.repository

import com.revakovskyi.stockmarket.data.csv.CSVParser
import com.revakovskyi.stockmarket.data.local.CompanyListingEntity
import com.revakovskyi.stockmarket.data.local.StockDatabase
import com.revakovskyi.stockmarket.data.mapper.toCompanyInfo
import com.revakovskyi.stockmarket.data.mapper.toCompanyListing
import com.revakovskyi.stockmarket.data.mapper.toCompanyListingEntity
import com.revakovskyi.stockmarket.data.remote.StockApi
import com.revakovskyi.stockmarket.domain.model.CompanyInfo
import com.revakovskyi.stockmarket.domain.model.CompanyListing
import com.revakovskyi.stockmarket.domain.model.IntradayInfo
import com.revakovskyi.stockmarket.domain.repository.StockRepository
import com.revakovskyi.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings: List<CompanyListingEntity> = dao.searchCompanyListings(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDBEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDBEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(null, "Couldn't unpack the data"))
                null

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(null, "Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(listings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(
                    dao.searchCompanyListings("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(null, "Couldn't unpack the intraday info")

        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(null, "Couldn't load intraday info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(null, "Couldn't unpack the company info")

        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(null, "Couldn't load company info")
        }
    }
}