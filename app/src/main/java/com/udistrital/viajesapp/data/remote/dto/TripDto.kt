package com.udistrital.viajesapp.data.remote.dto

import com.google.gson.annotations.SerializedName


data class TripDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("destination") val destination: String?,
    @SerializedName("startDate") val startDate: String?,
    @SerializedName("endDate") val endDate: String?,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("places") val places: List<PlaceDto>? = null,
    @SerializedName("placesCount") val placesCount: Int? = null
)