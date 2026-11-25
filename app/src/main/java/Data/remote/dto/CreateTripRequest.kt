package com.udistrital.viajesapp.data.remote.dto

import com.google.gson.annotations.SerializedName


data class CreateTripRequest(
    @SerializedName("name") val name: String,
    @SerializedName("destination") val destination: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String
)