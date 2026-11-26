package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Trip
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidatePlaceUseCaseTest {

    private lateinit var validatePlaceUseCase: ValidatePlaceUseCase
    private val sampleTrip = Trip(
        id = "trip-1",
        name = "Europa 2026",
        destination = "Italia",
        startDate = "2026-06-10",
        endDate = "2026-06-20"
    )

    @Before
    fun setUp() {
        validatePlaceUseCase = ValidatePlaceUseCase()
    }

    @Test
    fun `when tentativeDate is before trip startDate, return validation error`() {
        val result = validatePlaceUseCase(
            trip = sampleTrip,
            name = "Coliseo Romano",
            description = "Anfiteatro histórico",
            city = "Roma",
            categoryId = 1,
            tentativeDate = "2026-06-05"
        )

        assertFalse(result.isValid)
        assertNotNull(result.tentativeDateError)
    }

    @Test
    fun `when tentativeDate is after trip endDate, return validation error`() {
        val result = validatePlaceUseCase(
            trip = sampleTrip,
            name = "Coliseo Romano",
            description = "Anfiteatro histórico",
            city = "Roma",
            categoryId = 1,
            tentativeDate = "2026-06-25"
        )

        assertFalse(result.isValid)
        assertNotNull(result.tentativeDateError)
    }

    @Test
    fun `when categoryId is zero or invalid, return validation error`() {
        val result = validatePlaceUseCase(
            trip = sampleTrip,
            name = "Coliseo Romano",
            description = "Anfiteatro histórico",
            city = "Roma",
            categoryId = 0,
            tentativeDate = "2026-06-12"
        )

        assertFalse(result.isValid)
        assertNotNull(result.categoryError)
    }

    @Test
    fun `when required place fields are blank, return validation errors`() {
        val result = validatePlaceUseCase(
            trip = sampleTrip,
            name = " ",
            description = "",
            city = " ",
            categoryId = 0,
            tentativeDate = ""
        )

        assertFalse(result.isValid)
        assertNotNull(result.nameError)
        assertNotNull(result.descriptionError)
        assertNotNull(result.cityError)
        assertNotNull(result.categoryError)
        assertNotNull(result.tentativeDateError)
    }

    @Test
    fun `when tentativeDate is inside trip dates range, return success`() {
        val result = validatePlaceUseCase(
            trip = sampleTrip,
            name = "Coliseo Romano",
            description = "Anfiteatro histórico",
            city = "Roma",
            categoryId = 1,
            tentativeDate = "2026-06-12"
        )

        assertTrue(result.isValid)
        assertNull(result.nameError)
        assertNull(result.descriptionError)
        assertNull(result.cityError)
        assertNull(result.categoryError)
        assertNull(result.tentativeDateError)
    }
}
