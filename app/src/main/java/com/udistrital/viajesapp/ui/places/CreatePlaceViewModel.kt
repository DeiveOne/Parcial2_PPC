package com.udistrital.viajesapp.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.viajesapp.data.repository.CategoryRepositoryImpl
import com.udistrital.viajesapp.data.repository.TripRepositoryImpl
import com.udistrital.viajesapp.domain.usecase.CreatePlaceUseCase
import com.udistrital.viajesapp.domain.usecase.GetCategoriesUseCase
import com.udistrital.viajesapp.domain.usecase.GetTripDetailUseCase
import com.udistrital.viajesapp.domain.usecase.ValidatePlaceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePlaceViewModel(
    private val getTripDetailUseCase: GetTripDetailUseCase = GetTripDetailUseCase(TripRepositoryImpl()),
    private val getCategoriesUseCase: GetCategoriesUseCase = GetCategoriesUseCase(CategoryRepositoryImpl()),
    private val createPlaceUseCase: CreatePlaceUseCase = CreatePlaceUseCase(TripRepositoryImpl()),
    private val validatePlaceUseCase: ValidatePlaceUseCase = ValidatePlaceUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePlaceUiState())
    val uiState: StateFlow<CreatePlaceUiState> = _uiState.asStateFlow()

    fun loadData(tripId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingTrip = true, isLoadingCategories = true, generalError = null) }

            getTripDetailUseCase(tripId).onSuccess { trip ->
                _uiState.update { it.copy(trip = trip, isLoadingTrip = false) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoadingTrip = false, generalError = error.message ?: "No se encontró el viaje.") }
            }

            getCategoriesUseCase().onSuccess { categories ->
                _uiState.update { it.copy(categories = categories, isLoadingCategories = false) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoadingCategories = false, generalError = error.message ?: "Error al obtener categorías.") }
            }
        }
    }

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(name = name, nameError = null, generalError = null) }
    }

    fun onDescriptionChanged(description: String) {
        _uiState.update { it.copy(description = description, descriptionError = null, generalError = null) }
    }

    fun onCityChanged(city: String) {
        _uiState.update { it.copy(city = city, cityError = null, generalError = null) }
    }

    fun onCategorySelected(categoryId: Int) {
        _uiState.update { it.copy(selectedCategoryId = categoryId, categoryError = null, generalError = null) }
    }

    fun onTentativeDateChanged(tentativeDate: String) {
        _uiState.update { it.copy(tentativeDate = tentativeDate, tentativeDateError = null, generalError = null) }
    }

    fun createPlace() {
        val state = _uiState.value
        val trip = state.trip ?: return

        val validation = validatePlaceUseCase(
            trip = trip,
            name = state.name,
            description = state.description,
            city = state.city,
            categoryId = state.selectedCategoryId,
            tentativeDate = state.tentativeDate
        )

        if (!validation.isValid) {
            _uiState.update {
                it.copy(
                    nameError = validation.nameError,
                    descriptionError = validation.descriptionError,
                    cityError = validation.cityError,
                    categoryError = validation.categoryError,
                    tentativeDateError = validation.tentativeDateError,
                    generalError = validation.generalError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, generalError = null) }
            createPlaceUseCase(
                trip = trip,
                name = state.name,
                description = state.description,
                city = state.city,
                categoryId = state.selectedCategoryId,
                tentativeDate = state.tentativeDate
            ).onSuccess {
                _uiState.update { s -> s.copy(isSubmitting = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update { s ->
                    s.copy(isSubmitting = false, generalError = error.message ?: "Error al registrar el lugar.")
                }
            }
        }
    }
}
