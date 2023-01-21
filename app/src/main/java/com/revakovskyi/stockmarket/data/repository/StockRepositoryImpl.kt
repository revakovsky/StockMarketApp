package com.revakovskyi.stockmarket.data.repository

import com.revakovskyi.stockmarket.data.local.CompanyListingEntity
import com.revakovskyi.stockmarket.data.local.StockDatabase
import com.revakovskyi.stockmarket.data.mapper.toCompanyListing
import com.revakovskyi.stockmarket.data.remote.StockApi
import com.revakovskyi.stockmarket.domain.model.CompanyListing
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
    val api: StockApi,
    val db: StockDatabase
) : StockRepository {

    val dao = db.dao

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

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(null, "Couldn't unpack the data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(null, "Couldn't load data"))
            }
        }
    }

}