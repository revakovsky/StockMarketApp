package com.revakovskyi.stockmarket.domain.model

data class CompanyListing(
    val symbol: String,
    val name: String,
    val exchange: String
)
