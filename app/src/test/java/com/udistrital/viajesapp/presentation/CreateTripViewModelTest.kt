package com.udistrital.viajesapp.presentation

import com.udistrital.viajesapp.domain.model.Trip
import com.udistrital.viajesapp.domain.usecase.CreateTripUseCase
import com.udistrital.viajesapp.domain.usecase.ValidateTripUseCase
import com.udistrital.viajesapp.ui.trips.CreateTripViewModel
import com.udistrital.viajesapp.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateTripViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val createTripUseCase: CreateTripUseCase = mockk()
    private val validateTripUseCase = ValidateTripUseCase()

    @Test
    fun `when createTrip called with empty name, set nameError in state`() = runTest {
        val viewModel = CreateTripViewModel(createTripUseCase, validateTripUseCase)

        viewModel.onDestinationChanged("Italia")
        viewModel.onStartDateChanged("2026-06-10")
        viewModel.onEndDateChanged("2026-06-20")

        viewModel.createTrip()

        val state = viewModel.uiState.value
        assertNotNull(state.nameError)
        assertEquals("El nombre del viaje es obligatorio.", state.nameError)
    }

    @Test
    fun `when createTrip succeeds, update isSuccess to true`() = runTest {
        val createdTrip = Trip("1", "Europa 2026", "Italia", "2026-06-10", "2026-06-20")
        coEvery {
            createTripUseCase("Europa 2026", "Italia", "2026-06-10", "2026-06-20")
        } returns Result.success(createdTrip)

        val viewModel = CreateTripViewModel(createTripUseCase, validateTripUseCase)

        viewModel.onNameChanged("Europa 2026")
        viewModel.onDestinationChanged("Italia")
        viewModel.onStartDateChanged("2026-06-10")
        viewModel.onEndDateChanged("2026-06-20")

        viewModel.createTrip()

        val state = viewModel.uiState.value
        assertTrue(state.isSuccess)
    }
}
