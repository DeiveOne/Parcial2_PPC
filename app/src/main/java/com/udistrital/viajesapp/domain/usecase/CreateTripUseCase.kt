package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.repository.TripRepository

class CreateTripUseCase(
    private val tripRepository: TripRepository,
    private val validateTripUseCase: ValidateTripUseCase = ValidateTripUseCase()
) {
    suspend operator fun invoke(
        name: String,
        destination: String,
        startDate: String,
        endDate: String
    ): Result<Trip> {
        val validation = validateTripUseCase(name, destination, startDate, endDate)
        if (!validation.isValid) {
            val firstError = validation.nameError
                ?: validation.destinationError
                ?: validation.startDateError
                ?: validation.endDateError
                ?: validation.generalError
                ?: "Datos del viaje inválidos."
            return Result.failure(IllegalArgumentException(firstError))
        }
        return tripRepository.createTrip(
            name = name.trim(),
            destination = destination.trim(),
            startDate = startDate.trim(),
            endDate = endDate.trim()
        )
    }
}
