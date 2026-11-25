package com.udistrital.viajesapp.data.remote.dto

import com.google.gson.annotations.SerializedName


data class CreatePlaceRequest(
    @SerializedName("tripId") val tripId: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("city") val city: String,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("tentativeDate") val tentativeDate: String
)