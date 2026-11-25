package com.udistrital.viajesapp.data.repository

import com.udistrital.viajesapp.data.mapper.toDomain
import com.udistrital.viajesapp.data.remote.RetrofitClient
import com.udistrital.viajesapp.data.remote.api.ViajesApiService
import com.udistrital.viajesapp.domain.model.Category
import com.udistrital.viajesapp.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val api: ViajesApiService = RetrofitClient.apiService
) : CategoryRepository {

    override suspend fun getCategories(): Result<List<Category>> =
        safeApiCall({ api.getCategories() }) { dtos -> dtos.toDomain() }
}