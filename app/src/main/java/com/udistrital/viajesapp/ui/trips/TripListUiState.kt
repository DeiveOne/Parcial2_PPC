package com.udistrital.viajesapp.ui.trips

import com.udistrital.viajesapp.domain.model.Trip

sealed interface TripListUiState {
    data object Loading : TripListUiState
    data class Success(val trips: List<Trip>) : TripListUiState
    data class Error(val message: String) : TripListUiState
}
