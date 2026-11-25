package com.udistrital.viajesapp.domain.repository

import com.udistrital.viajesapp.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
}
