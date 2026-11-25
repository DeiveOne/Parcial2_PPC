package com.udistrital.viajesapp.ui.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.viajesapp.data.repository.TripRepositoryImpl
import com.udistrital.viajesapp.domain.usecase.CreateTripUseCase
import com.udistrital.viajesapp.domain.usecase.ValidateTripUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateTripViewModel(
    private val createTripUseCase: CreateTripUseCase = CreateTripUseCase(TripRepositoryImpl()),
    private val validateTripUseCase: ValidateTripUseCase = ValidateTripUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTripUiState())
    val uiState: StateFlow<CreateTripUiState> = _uiState.asStateFlow()

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(name = name, nameError = null, generalError = null) }
    }

    fun onDestinationChanged(destination: String) {
        _uiState.update { it.copy(destination = destination, destinationError = null, generalError = null) }
    }

    fun onStartDateChanged(startDate: String) {
        _uiState.update { it.copy(startDate = startDate, startDateError = null, generalError = null) }
    }

    fun onEndDateChanged(endDate: String) {
        _uiState.update { it.copy(endDate = endDate, endDateError = null, generalError = null) }
    }

    fun createTrip() {
        val currentState = _uiState.value
        val validation = validateTripUseCase(
            name = currentState.name,
            destination = currentState.destination,
            startDate = currentState.startDate,
            endDate = currentState.endDate
        )

        if (!validation.isValid) {
            _uiState.update {
                it.copy(
                    nameError = validation.nameError,
                    destinationError = validation.destinationError,
                    startDateError = validation.startDateError,
                    endDateError = validation.endDateError,
                    generalError = validation.generalError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, generalError = null) }
            createTripUseCase(
                name = currentState.name,
                destination = currentState.destination,
                startDate = currentState.startDate,
                endDate = currentState.endDate
            ).onSuccess {
                _uiState.update { state -> state.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update { state ->
                    state.copy(isLoading = false, generalError = error.message ?: "Error al crear el viaje.")
                }
            }
        }
    }
}
