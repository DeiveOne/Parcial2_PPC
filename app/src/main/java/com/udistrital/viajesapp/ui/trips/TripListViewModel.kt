package com.udistrital.viajesapp.ui.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.viajesapp.data.repository.TripRepositoryImpl
import com.udistrital.viajesapp.domain.usecase.GetTripsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TripListViewModel(
    private val getTripsUseCase: GetTripsUseCase = GetTripsUseCase(TripRepositoryImpl())
) : ViewModel() {

    private val _uiState = MutableStateFlow<TripListUiState>(TripListUiState.Loading)
    val uiState: StateFlow<TripListUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadTrips()
    }

    fun loadTrips(showLoading: Boolean = true) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            if (showLoading || _uiState.value !is TripListUiState.Success) {
                _uiState.value = TripListUiState.Loading
            }
            getTripsUseCase()
                .onSuccess { trips ->
                    _uiState.value = TripListUiState.Success(trips)
                }
                .onFailure { error ->
                    _uiState.value = TripListUiState.Error(error.message ?: "Error al cargar los viajes.")
                }
        }
    }
}