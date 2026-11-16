package com.aman.cryptotracker.viewmodels

import com.aman.cryptotracker.models.Crypto

sealed interface CryptoUiState {

    data object Loading : CryptoUiState

    data class Success(
        val cryptoList: List<Crypto>,
    ) : CryptoUiState

    data class Error(
        val message: String,
    ) : CryptoUiState

}
