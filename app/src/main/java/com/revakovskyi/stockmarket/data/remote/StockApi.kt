package com.revakovskyi.stockmarket.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object {
        private const val API_KEY = "JATQQFXIK1T4G3JH"
        const val BASE_URL = "https://www.alphavantage.co/"
    }
}