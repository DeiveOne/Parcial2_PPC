package com.udistrital.viajesapp.ui.detail

import com.udistrital.viajesapp.domain.model.Trip

sealed interface TripDetailUiState {
    data object Loading : TripDetailUiState
    data class Success(val trip: Trip) : TripDetailUiState
    data class Error(val message: String) : TripDetailUiState
}
