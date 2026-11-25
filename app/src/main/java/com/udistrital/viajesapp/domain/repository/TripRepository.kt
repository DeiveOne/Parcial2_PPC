package com.udistrital.viajesapp.domain.repository

import com.udistrital.viajesapp.domain.model.Place
import com.udistrital.viajesapp.domain.model.Trip

interface TripRepository {
    suspend fun getTrips(): Result<List<Trip>>
    suspend fun getTripById(id: String): Result<Trip>
    suspend fun createTrip(name: String, destination: String, startDate: String, endDate: String): Result<Trip>
    suspend fun createPlace(
        tripId: String,
        name: String,
        description: String,
        city: String,
        categoryId: Int,
        tentativeDate: String
    ): Result<Place>
}
