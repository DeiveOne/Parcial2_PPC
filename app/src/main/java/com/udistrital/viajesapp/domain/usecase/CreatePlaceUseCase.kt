package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Place
import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.repository.TripRepository

class CreatePlaceUseCase(
    private val tripRepository: TripRepository,
    private val validatePlaceUseCase: ValidatePlaceUseCase = ValidatePlaceUseCase()
) {
    suspend operator fun invoke(
        trip: Trip,
        name: String,
        description: String,
        city: String,
        categoryId: Int,
        tentativeDate: String
    ): Result<Place> {
        val validation = validatePlaceUseCase(
            trip = trip,
            name = name,
            description = description,
            city = city,
            categoryId = categoryId,
            tentativeDate = tentativeDate
        )
        if (!validation.isValid) {
            val firstError = validation.nameError
                ?: validation.descriptionError
                ?: validation.cityError
                ?: validation.categoryError
                ?: validation.tentativeDateError
                ?: validation.generalError
                ?: "Datos del lugar inválidos."
            return Result.failure(IllegalArgumentException(firstError))
        }
        return tripRepository.createPlace(
            tripId = trip.id,
            name = name.trim(),
            description = description.trim(),
            city = city.trim(),
            categoryId = categoryId,
            tentativeDate = tentativeDate.trim()
        )
    }
}
