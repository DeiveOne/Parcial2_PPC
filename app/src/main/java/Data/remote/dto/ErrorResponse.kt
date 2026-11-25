package com.udistrital.viajesapp.data.remote.dto

import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @SerializedName("message") val message: String?
)