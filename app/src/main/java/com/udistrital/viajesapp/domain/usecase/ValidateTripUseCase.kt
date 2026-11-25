package com.udistrital.viajesapp.domain.usecase

import java.time.LocalDate
import java.time.format.DateTimeParseException

data class TripValidationResult(
    val isValid: Boolean,
    val nameError: String? = null,
    val destinationError: String? = null,
    val startDateError: String? = null,
    val endDateError: String? = null,
    val generalError: String? = null
)

class ValidateTripUseCase {
    operator fun invoke(
        name: String,
        destination: String,
        startDate: String,
        endDate: String
    ): TripValidationResult {
        var nameErr: String? = null
        var destErr: String? = null
        var startErr: String? = null
        var endErr: String? = null
        var genErr: String? = null

        if (name.isBlank()) {
            nameErr = "El nombre del viaje es obligatorio."
        }
        if (destination.isBlank()) {
            destErr = "El destino es obligatorio."
        }

        var startLocalDate: LocalDate? = null
        var endLocalDate: LocalDate? = null

        if (startDate.isBlank()) {
            startErr = "La fecha de inicio es obligatoria."
        } else {
            try {
                startLocalDate = LocalDate.parse(startDate.trim())
            } catch (e: DateTimeParseException) {
                startErr = "Formato de fecha de inicio inválido (AAAA-MM-DD)."
            }
        }

        if (endDate.isBlank()) {
            endErr = "La fecha de finalización es obligatoria."
        } else {
            try {
                endLocalDate = LocalDate.parse(endDate.trim())
            } catch (e: DateTimeParseException) {
                endErr = "Formato de fecha de fin inválido (AAAA-MM-DD)."
            }
        }

        if (startLocalDate != null && endLocalDate != null) {
            if (endLocalDate.isBefore(startLocalDate)) {
                endErr = "La fecha final no puede ser anterior a la fecha inicial."
            }
        }

        val isValid = nameErr == null && destErr == null && startErr == null && endErr == null && genErr == null

        return TripValidationResult(
            isValid = isValid,
            nameError = nameErr,
            destinationError = destErr,
            startDateError = startErr,
            endDateError = endErr,
            generalError = genErr
        )
    }
}
