package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Trip
import java.time.LocalDate
import java.time.format.DateTimeParseException

data class PlaceValidationResult(
    val isValid: Boolean,
    val nameError: String? = null,
    val descriptionError: String? = null,
    val cityError: String? = null,
    val categoryError: String? = null,
    val tentativeDateError: String? = null,
    val generalError: String? = null
)

class ValidatePlaceUseCase {
    operator fun invoke(
        trip: Trip,
        name: String,
        description: String,
        city: String,
        categoryId: Int,
        tentativeDate: String
    ): PlaceValidationResult {
        var nameErr: String? = null
        var descErr: String? = null
        var cityErr: String? = null
        var catErr: String? = null
        var dateErr: String? = null
        var genErr: String? = null

        if (name.isBlank()) {
            nameErr = "El nombre del lugar es obligatorio."
        }
        if (description.isBlank()) {
            descErr = "La descripción es obligatoria."
        }
        if (city.isBlank()) {
            cityErr = "La ciudad es obligatoria."
        }
        if (categoryId <= 0) {
            catErr = "Debe seleccionar una categoría válida."
        }

        var tentativeLocalDate: LocalDate? = null
        if (tentativeDate.isBlank()) {
            dateErr = "La fecha tentativa es obligatoria."
        } else {
            try {
                tentativeLocalDate = LocalDate.parse(tentativeDate.trim())
            } catch (e: DateTimeParseException) {
                dateErr = "Formato de fecha inválido (AAAA-MM-DD)."
            }
        }

        if (tentativeLocalDate != null) {
            try {
                val tripStart = LocalDate.parse(trip.startDate.trim())
                val tripEnd = LocalDate.parse(trip.endDate.trim())

                if (tentativeLocalDate.isBefore(tripStart) || tentativeLocalDate.isAfter(tripEnd)) {
                    dateErr = "La fecha tentativa ($tentativeDate) debe estar entre ${trip.startDate} y ${trip.endDate}."
                }
            } catch (e: Exception) {
                genErr = "Error al verificar las fechas del viaje."
            }
        }

        val isValid = nameErr == null && descErr == null && cityErr == null &&
                catErr == null && dateErr == null && genErr == null

        return PlaceValidationResult(
            isValid = isValid,
            nameError = nameErr,
            descriptionError = descErr,
            cityError = cityErr,
            categoryError = catErr,
            tentativeDateError = dateErr,
            generalError = genErr
        )
    }
}
