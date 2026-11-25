package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Place
import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.repository.TripRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CreatePlaceUseCaseTest {

    private val tripRepository: TripRepository = mockk()
    private val validatePlaceUseCase = ValidatePlaceUseCase()
    private lateinit var createPlaceUseCase: CreatePlaceUseCase

    private val sampleTrip = Trip("t1", "Europa 2026", "Italia", "2026-06-10", "2026-06-20")

    @Before
    fun setUp() {
        createPlaceUseCase = CreatePlaceUseCase(tripRepository, validatePlaceUseCase)
    }

    @Test
    fun `when place date is outside trip bounds, return failure`() = runTest {
        val result = createPlaceUseCase(
            trip = sampleTrip,
            name = "Coliseo",
            description = "Anfiteatro",
            city = "Roma",
            categoryId = 1,
            tentativeDate = "2026-06-30"
        )

        assertTrue(result.isFailure)
        coVerify(exactly = 0) { tripRepository.createPlace(any(), any(), any(), any(), any(), any()) }
    }

    @Test
    fun `when place data is valid, call repository and return success`() = runTest {
        val createdPlace = Place("p1", "t1", "Coliseo", "Anfiteatro", "Roma", 1, "2026-06-12")
        coEvery {
            tripRepository.createPlace("t1", "Coliseo", "Anfiteatro", "Roma", 1, "2026-06-12")
        } returns Result.success(createdPlace)

        val result = createPlaceUseCase(
            trip = sampleTrip,
            name = "Coliseo",
            description = "Anfiteatro",
            city = "Roma",
            categoryId = 1,
            tentativeDate = "2026-06-12"
        )

        assertTrue(result.isSuccess)
        assertEquals(createdPlace, result.getOrNull())
        coVerify(exactly = 1) {
            tripRepository.createPlace("t1", "Coliseo", "Anfiteatro", "Roma", 1, "2026-06-12")
        }
    }
}
