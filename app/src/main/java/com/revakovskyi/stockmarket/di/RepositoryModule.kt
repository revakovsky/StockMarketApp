package com.revakovskyi.stockmarket.di

import com.revakovskyi.stockmarket.data.csv.CSVParser
import com.revakovskyi.stockmarket.data.csv.CompanyListingsParser
import com.revakovskyi.stockmarket.data.repository.StockRepositoryImpl
import com.revakovskyi.stockmarket.domain.model.CompanyListing
import com.revakovskyi.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

}