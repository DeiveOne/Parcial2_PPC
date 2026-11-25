package com.udistrital.viajesapp.data.repository

import com.udistrital.viajesapp.data.remote.dto.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException


internal suspend fun <T, R> safeApiCall(
    call: suspend () -> Response<T>,
    transform: (T) -> R
): Result<R> {
    return try {
        val response = call()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.success(transform(body))
        } else {
            Result.failure(ApiException(response.code(), parseErrorMessage(response)))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: IOException) {
        Result.failure(IOException("No se pudo conectar con el servidor. Verifica tu conexión.", e))
    } catch (e: Exception) {
        Result.failure(Exception("Error inesperado: ${e.message}", e))
    }
}

private fun parseErrorMessage(response: Response<*>): String {
    val fallback = when (response.code()) {
        400 -> "Datos inválidos."
        404 -> "Recurso no encontrado."
        in 500..599 -> "Error interno del servidor."
        else -> "Error HTTP ${response.code()}."
    }
    return try {
        val raw = response.errorBody()?.string()
        if (raw.isNullOrBlank()) fallback
        else Gson().fromJson(raw, ErrorResponse::class.java)?.message ?: fallback
    } catch (e: Exception) {
        fallback
    }
}


class ApiException(val code: Int, message: String) : Exception(message)