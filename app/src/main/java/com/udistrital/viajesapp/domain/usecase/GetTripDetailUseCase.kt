package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.repository.TripRepository

class GetTripDetailUseCase(private val tripRepository: TripRepository) {
    suspend operator fun invoke(id: String): Result<Trip> {
        if (id.isBlank()) {
            return Result.failure(IllegalArgumentException("El ID del viaje no puede estar vacío."))
        }
        return tripRepository.getTripById(id)
    }
}
