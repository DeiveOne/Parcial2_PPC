package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Category
import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.repository.CategoryRepository
import com.udistrital.viajesapp.domain.repository.TripRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetTripDataUseCasesTest {

    private val tripRepository: TripRepository = mockk()
    private val categoryRepository: CategoryRepository = mockk()

    @Test
    fun `when trip id is valid, return the trip detail from repository`() = runTest {
        val trip = Trip("trip-1", "Europa", "Italia", "2026-06-10", "2026-06-20")
        coEvery { tripRepository.getTripById("trip-1") } returns Result.success(trip)

        val result = GetTripDetailUseCase(tripRepository)("trip-1")

        assertTrue(result.isSuccess)
        assertEquals(trip, result.getOrNull())
        coVerify(exactly = 1) { tripRepository.getTripById("trip-1") }
    }

    @Test
    fun `when trip id is blank, do not query the repository`() = runTest {
        val result = GetTripDetailUseCase(tripRepository)(" ")

        assertTrue(result.isFailure)
        coVerify(exactly = 0) { tripRepository.getTripById(" ") }
    }

    @Test
    fun `when categories are available, return them from repository`() = runTest {
        val categories = listOf(Category(1, "Cultural", "Museos y expresiones culturales"))
        coEvery { categoryRepository.getCategories() } returns Result.success(categories)

        val result = GetCategoriesUseCase(categoryRepository)()

        assertTrue(result.isSuccess)
        assertEquals(categories, result.getOrNull())
        coVerify(exactly = 1) { categoryRepository.getCategories() }
    }
}
