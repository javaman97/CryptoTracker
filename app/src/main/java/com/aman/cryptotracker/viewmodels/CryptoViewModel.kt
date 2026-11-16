package com.aman.cryptotracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.cryptotracker.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository()

    private val _uiState = MutableStateFlow<CryptoUiState>(CryptoUiState.Loading)

    val uiState: StateFlow<CryptoUiState> = _uiState.asStateFlow()

    init {
        fetchCrypto()
    }

    private fun fetchCrypto() {
        viewModelScope.launch {
            _uiState.value = CryptoUiState.Loading

            try {
                val cryptoList = repository.getCryptoList()
                _uiState.value = CryptoUiState.Success(cryptoList)
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    429 -> "Too many requests. Please try again later."
                    401, 403 -> "API authentication failed."
                    else -> "Server error: ${e.code()}"
                }
                _uiState.value = CryptoUiState.Error(message)
            } catch (e: Exception) {
                _uiState.value = CryptoUiState.Error(e.message ?: "Something went wrong.")
            }
        }
    }
}
