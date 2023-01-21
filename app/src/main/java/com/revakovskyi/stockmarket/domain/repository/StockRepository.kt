package com.revakovskyi.stockmarket.domain.repository

import com.revakovskyi.stockmarket.domain.model.CompanyListing
import com.revakovskyi.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

}