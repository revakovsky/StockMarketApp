package com.revakovskyi.stockmarket.presentation.company_info

import com.revakovskyi.stockmarket.domain.model.CompanyInfo
import com.revakovskyi.stockmarket.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
