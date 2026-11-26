package com.udistrital.viajesapp.presentation

import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.usecase.GetTripsUseCase
import com.udistrital.viajesapp.ui.trips.TripListUiState
import com.udistrital.viajesapp.ui.trips.TripListViewModel
import com.udistrital.viajesapp.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TripListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getTripsUseCase: GetTripsUseCase = mockk()

    @Test
    fun `when loadTrips succeeds, update uiState to Success`() = runTest {
        val tripsList = listOf(Trip("1", "Europa 2026", "Italia", "2026-06-10", "2026-06-20"))
        coEvery { getTripsUseCase() } returns Result.success(tripsList)

        val viewModel = TripListViewModel(getTripsUseCase)

        val state = viewModel.uiState.value
        assertTrue(state is TripListUiState.Success)
        assertEquals(tripsList, (state as TripListUiState.Success).trips)
    }

    @Test
    fun `when loadTrips fails, update uiState to Error`() = runTest {
        coEvery { getTripsUseCase() } returns Result.failure(Exception("Error de conexión"))

        val viewModel = TripListViewModel(getTripsUseCase)

        val state = viewModel.uiState.value
        assertTrue(state is TripListUiState.Error)
        assertEquals("Error de conexión", (state as TripListUiState.Error).message)
    }

    @Test
    fun `when refreshing trips, request the list again`() = runTest {
        coEvery { getTripsUseCase() } returns Result.success(emptyList())

        val viewModel = TripListViewModel(getTripsUseCase)
        viewModel.loadTrips()

        coVerify(exactly = 2) { getTripsUseCase() }
    }
}
