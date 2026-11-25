package com.udistrital.viajesapp.domain.usecase

import com.udistrital.viajesapp.domain.model.Category
import com.udistrital.viajesapp.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): Result<List<Category>> {
        return categoryRepository.getCategories()
    }
}
