package com.udistrital.viajesapp.ui.trips

data class CreateTripUiState(
    val name: String = "",
    val destination: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val nameError: String? = null,
    val destinationError: String? = null,
    val startDateError: String? = null,
    val endDateError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
