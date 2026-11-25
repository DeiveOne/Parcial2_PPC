package com.udistrital.viajesapp.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidateTripUseCaseTest {

    private lateinit var validateTripUseCase: ValidateTripUseCase

    @Before
    fun setUp() {
        validateTripUseCase = ValidateTripUseCase()
    }

    @Test
    fun `when name is blank, return validation error`() {
        val result = validateTripUseCase(
            name = "   ",
            destination = "Italia",
            startDate = "2026-06-10",
            endDate = "2026-06-20"
        )

        assertFalse(result.isValid)
        assertNotNull(result.nameError)
        assertEquals("El nombre del viaje es obligatorio.", result.nameError)
    }

    @Test
    fun `when destination is blank, return validation error`() {
        val result = validateTripUseCase(
            name = "Europa 2026",
            destination = "",
            startDate = "2026-06-10",
            endDate = "2026-06-20"
        )

        assertFalse(result.isValid)
        assertNotNull(result.destinationError)
        assertEquals("El destino es obligatorio.", result.destinationError)
    }

    @Test
    fun `when endDate is before startDate, return validation error`() {
        val result = validateTripUseCase(
            name = "Europa 2026",
            destination = "Italia",
            startDate = "2026-06-20",
            endDate = "2026-06-10"
        )

        assertFalse(result.isValid)
        assertNotNull(result.endDateError)
        assertEquals("La fecha final no puede ser anterior a la fecha inicial.", result.endDateError)
    }

    @Test
    fun `when startDate format is invalid, return validation error`() {
        val result = validateTripUseCase(
            name = "Europa 2026",
            destination = "Italia",
            startDate = "10/06/2026",
            endDate = "2026-06-20"
        )

        assertFalse(result.isValid)
        assertNotNull(result.startDateError)
    }

    @Test
    fun `when all trip fields are valid, return success`() {
        val result = validateTripUseCase(
            name = "Europa 2026",
            destination = "Italia",
            startDate = "2026-06-10",
            endDate = "2026-06-20"
        )

        assertTrue(result.isValid)
        assertNull(result.nameError)
        assertNull(result.destinationError)
        assertNull(result.startDateError)
        assertNull(result.endDateError)
    }
}
