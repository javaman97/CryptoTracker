package com.aman.cryptotracker.api

import com.aman.cryptotracker.models.Crypto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("coins/markets")
    suspend fun getCryptoList(
        @Query("vs_currency") currency: String = "inr",
        @Query("order") order: String = "market_cap_desc",
//        @Query("per_page") perPage: Int = 20,
//        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("price_change_percentage") priceChange: String = "24h,7d"
    ): List<Crypto>
}