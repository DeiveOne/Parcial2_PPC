package com.udistrital.viajesapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.viajesapp.data.repository.TripRepositoryImpl
import com.udistrital.viajesapp.domain.usecase.GetTripDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TripDetailViewModel(
    private val getTripDetailUseCase: GetTripDetailUseCase = GetTripDetailUseCase(TripRepositoryImpl())
) : ViewModel() {

    private val _uiState = MutableStateFlow<TripDetailUiState>(TripDetailUiState.Loading)
    val uiState: StateFlow<TripDetailUiState> = _uiState.asStateFlow()

    fun loadTripDetail(tripId: String) {
        viewModelScope.launch {
            _uiState.value = TripDetailUiState.Loading
            getTripDetailUseCase(tripId)
                .onSuccess { trip ->
                    _uiState.value = TripDetailUiState.Success(trip)
                }
                .onFailure { error ->
                    _uiState.value = TripDetailUiState.Error(error.message ?: "Error al obtener detalle del viaje.")
                }
        }
    }
}
