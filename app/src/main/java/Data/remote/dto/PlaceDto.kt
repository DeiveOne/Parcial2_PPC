package com.udistrital.viajesapp.data.remote.dto

import com.google.gson.annotations.SerializedName


data class PlaceDto(
    @SerializedName("id") val id: String,
    @SerializedName("tripId") val tripId: String,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("tentativeDate") val tentativeDate: String?,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("category") val category: CategoryDto? = null
)