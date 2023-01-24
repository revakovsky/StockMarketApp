package com.revakovskyi.stockmarket.data.csv

import android.annotation.SuppressLint
import com.opencsv.CSVReader
import com.revakovskyi.stockmarket.data.mapper.toIntradayInfo
import com.revakovskyi.stockmarket.data.remote.dto.IntradayInfoDto
import com.revakovskyi.stockmarket.domain.model.IntradayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo> {

    @SuppressLint("NewApi")
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))

        return withContext(Dispatchers.IO) {
            csvReader.readAll().drop(n = 1).mapNotNull { line ->
                val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                val close = line.getOrNull(4) ?: return@mapNotNull null

                val dto = IntradayInfoDto(timestamp = timestamp, close = close.toDouble())
                dto.toIntradayInfo()
            }
                .filter { it.date.dayOfMonth == LocalDate.now().minusDays(1).dayOfMonth }
                .sortedBy { it.date.hour }
                .also { csvReader.close() }
        }
    }
}