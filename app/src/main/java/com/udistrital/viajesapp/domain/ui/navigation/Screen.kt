package com.udistrital.viajesapp.domain.ui.navigation

sealed class Screen(val route: String) {
    data object TripList : Screen("trip_list")
    data object CreateTrip : Screen("create_trip")

    data object TripDetail : Screen("trip_detail/{tripId}") {
        fun createRoute(tripId: String): String = "trip_detail/$tripId"
    }

    data object CreatePlace : Screen("create_place/{tripId}") {
        fun createRoute(tripId: String): String = "create_place/$tripId"
    }
}