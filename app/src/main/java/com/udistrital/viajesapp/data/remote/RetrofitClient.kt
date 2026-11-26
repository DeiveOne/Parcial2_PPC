package com.udistrital.viajesapp.data.remote

import com.udistrital.viajesapp.data.remote.api.ViajesApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // Cambia BASE_URL según tu entorno:
    // - Emulador Android: "http://10.0.2.2:3000/"
    // - Dispositivo físico (Celular en la misma red Wi-Fi): "http://192.168.20.136:3000/"
    const val BASE_URL = "http://192.168.20.136:3000/"

    private const val TIMEOUT_SECONDS = 30L

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ViajesApiService by lazy {
        retrofit.create(ViajesApiService::class.java)
    }
}