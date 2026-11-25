package com.udistrital.viajesapp.data.remote.api

import com.udistrital.viajesapp.data.remote.dto.CategoryDto
import com.udistrital.viajesapp.data.remote.dto.CreatePlaceRequest
import com.udistrital.viajesapp.data.remote.dto.CreateTripRequest
import com.udistrital.viajesapp.data.remote.dto.PlaceDto
import com.udistrital.viajesapp.data.remote.dto.TripDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ViajesApiService {

    @GET("api/trips")
    suspend fun getTrips(): Response<List<TripDto>>

    @POST("api/trips")
    suspend fun createTrip(@Body request: CreateTripRequest): Response<TripDto>

    @GET("api/trips/{id}")
    suspend fun getTripById(@Path("id") id: String): Response<TripDto>

    @POST("api/places")
    suspend fun createPlace(@Body request: CreatePlaceRequest): Response<PlaceDto>

    @GET("api/categories")
    suspend fun getCategories(): Response<List<CategoryDto>>
}