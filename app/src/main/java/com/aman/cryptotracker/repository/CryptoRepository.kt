package com.aman.cryptotracker.repository

import com.aman.cryptotracker.api.RetrofitInstance
import com.aman.cryptotracker.models.Crypto

class CryptoRepository {

    private val apiService = RetrofitInstance.api

    suspend fun getCryptoList(): List<Crypto> {
        return apiService.getCryptoList()
    }

}
