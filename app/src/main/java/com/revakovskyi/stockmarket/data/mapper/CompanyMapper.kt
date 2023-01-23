package com.revakovskyi.stockmarket.data.mapper

import com.revakovskyi.stockmarket.data.local.CompanyListingEntity
import com.revakovskyi.stockmarket.data.remote.dto.CompanyInfoDto
import com.revakovskyi.stockmarket.domain.model.CompanyInfo
import com.revakovskyi.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        symbol = symbol,
        name = name,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        symbol = symbol,
        name = name,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = this.symbol ?: "",
        name = this.name ?: "",
        description = this.description ?: "",
        country = this.country ?: "",
        industry = this.industry ?: "",
    )
}
