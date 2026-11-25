package com.udistrital.viajesapp.data.mapper

import com.udistrital.viajesapp.data.remote.dto.TripDto
import com.udistrital.viajesapp.domain.model.Trip

fun TripDto.toDomain(): Trip = Trip(
    id = id,
    name = name.orEmpty(),
    destination = destination.orEmpty(),
    startDate = startDate.orEmpty(),
    endDate = endDate.orEmpty(),
    places = places?.toDomain().orEmpty()
)

@JvmName("tripDtoListToDomain")
fun List<TripDto>.toDomain(): List<Trip> = map { it.toDomain() }