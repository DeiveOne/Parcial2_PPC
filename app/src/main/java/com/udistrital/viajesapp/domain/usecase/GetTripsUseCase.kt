package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.repository.TripRepository

class GetTripsUseCase(private val tripRepository: TripRepository) {
    suspend operator fun invoke(): Result<List<Trip>> {
        return tripRepository.getTrips()
    }
}
