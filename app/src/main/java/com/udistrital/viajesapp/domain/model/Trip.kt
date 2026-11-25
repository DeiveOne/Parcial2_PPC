package com.udistrital.viajesapp.domain.model

data class Trip(
    val id: String,
    val name: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val places: List<Place> = emptyList()
)
