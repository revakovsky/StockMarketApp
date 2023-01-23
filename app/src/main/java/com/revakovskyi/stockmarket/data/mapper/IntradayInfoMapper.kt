package com.revakovskyi.stockmarket.data.mapper

import android.annotation.SuppressLint
import com.revakovskyi.stockmarket.data.remote.dto.IntradayInfoDto
import com.revakovskyi.stockmarket.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)

    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}