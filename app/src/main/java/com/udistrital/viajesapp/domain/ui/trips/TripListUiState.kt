package com.udistrital.viajesapp.ui.trips

import com.udistrital.viajesapp.domain.model.Trip

data class TripListUiState(
    val trips: List<Trip> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)