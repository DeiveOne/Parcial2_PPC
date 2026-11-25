package com.udistrital.viajesapp.ui.places

import com.udistrital.viajesapp.domain.model.Category
import com.udistrital.viajesapp.domain.model.Trip

data class CreatePlaceUiState(
    val trip: Trip? = null,
    val categories: List<Category> = emptyList(),
    val name: String = "",
    val description: String = "",
    val city: String = "",
    val selectedCategoryId: Int = 0,
    val tentativeDate: String = "",
    val nameError: String? = null,
    val descriptionError: String? = null,
    val cityError: String? = null,
    val categoryError: String? = null,
    val tentativeDateError: String? = null,
    val generalError: String? = null,
    val isLoadingTrip: Boolean = true,
    val isLoadingCategories: Boolean = true,
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false
)
