package com.udistrital.viajesapp.data.mapper

import com.udistrital.viajesapp.data.remote.dto.CategoryDto
import com.udistrital.viajesapp.domain.model.Category

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name.orEmpty(),
    description = description.orEmpty()
)

fun List<CategoryDto>.toDomain(): List<Category> = map { it.toDomain() }