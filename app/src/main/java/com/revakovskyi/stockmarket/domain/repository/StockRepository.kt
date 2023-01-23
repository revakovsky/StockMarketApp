package com.revakovskyi.stockmarket.domain.repository

import com.revakovskyi.stockmarket.domain.model.CompanyInfo
import com.revakovskyi.stockmarket.domain.model.CompanyListing
import com.revakovskyi.stockmarket.domain.model.IntradayInfo
import com.revakovskyi.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo>

}