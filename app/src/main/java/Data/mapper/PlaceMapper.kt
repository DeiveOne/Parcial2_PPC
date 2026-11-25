package com.udistrital.viajesapp.data.mapper

import com.udistrital.viajesapp.data.remote.dto.PlaceDto
import com.udistrital.viajesapp.domain.model.Place

fun PlaceDto.toDomain(): Place = Place(
    id = id,
    tripId = tripId,
    name = name.orEmpty(),
    description = description.orEmpty(),
    city = city.orEmpty(),
    categoryId = categoryId,
    tentativeDate = tentativeDate.orEmpty(),
    category = category?.toDomain()
)

fun List<PlaceDto>.toDomain(): List<Place> = map { it.toDomain() }