package com.udistrital.viajesapp.data.repository

import com.udistrital.viajesapp.data.mapper.toDomain
import com.udistrital.viajesapp.data.remote.RetrofitClient
import com.udistrital.viajesapp.data.remote.api.ViajesApiService
import com.udistrital.viajesapp.data.remote.dto.CreatePlaceRequest
import com.udistrital.viajesapp.data.remote.dto.CreateTripRequest
import com.udistrital.viajesapp.domain.model.Place
import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.repository.TripRepository

class TripRepositoryImpl(
    private val api: ViajesApiService = RetrofitClient.apiService
) : TripRepository {

    override suspend fun getTrips(): Result<List<Trip>> =
        safeApiCall({ api.getTrips() }) { dtos -> dtos.toDomain() }

    override suspend fun getTripById(id: String): Result<Trip> =
        safeApiCall({ api.getTripById(id) }) { dto -> dto.toDomain() }

    override suspend fun createTrip(
        name: String,
        destination: String,
        startDate: String,
        endDate: String
    ): Result<Trip> {
        val request = CreateTripRequest(
            name = name,
            destination = destination,
            startDate = startDate,
            endDate = endDate
        )
        return safeApiCall({ api.createTrip(request) }) { dto -> dto.toDomain() }
    }

    override suspend fun createPlace(
        tripId: String,
        name: String,
        description: String,
        city: String,
        categoryId: Int,
        tentativeDate: String
    ): Result<Place> {
        val request = CreatePlaceRequest(
            tripId = tripId,
            name = name,
            description = description,
            city = city,
            categoryId = categoryId,
            tentativeDate = tentativeDate
        )
        return safeApiCall({ api.createPlace(request) }) { dto -> dto.toDomain() }
    }
}