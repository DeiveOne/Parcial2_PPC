package com.udistrital.viajesapp.domain.usecase

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

class CreateTripUseCaseTest {

    private val tripRepository: TripRepository = mockk()
    private val validateTripUseCase = ValidateTripUseCase()
    private lateinit var createTripUseCase: CreateTripUseCase

    @Before
    fun setUp() {
        createTripUseCase = CreateTripUseCase(tripRepository, validateTripUseCase)
    }

    @Test
    fun `when trip data is invalid, return failure without calling repository`() = runTest {
        val result = createTripUseCase(
            name = "",
            destination = "Italia",
            startDate = "2026-06-10",
            endDate = "2026-06-20"
        )

        assertTrue(result.isFailure)
        coVerify(exactly = 0) { tripRepository.createTrip(any(), any(), any(), any()) }
    }

    @Test
    fun `when trip data is valid, call repository and return success`() = runTest {
        val createdTrip = Trip("t1", "Europa 2026", "Italia", "2026-06-10", "2026-06-20")
        coEvery {
            tripRepository.createTrip("Europa 2026", "Italia", "2026-06-10", "2026-06-20")
        } returns Result.success(createdTrip)

        val result = createTripUseCase(
            name = "Europa 2026",
            destination = "Italia",
            startDate = "2026-06-10",
            endDate = "2026-06-20"
        )

        assertTrue(result.isSuccess)
        assertEquals(createdTrip, result.getOrNull())
        coVerify(exactly = 1) {
            tripRepository.createTrip("Europa 2026", "Italia", "2026-06-10", "2026-06-20")
        }
    }
}
