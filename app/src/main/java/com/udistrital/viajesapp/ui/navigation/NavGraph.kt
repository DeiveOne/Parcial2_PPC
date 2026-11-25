package com.udistrital.viajesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udistrital.viajesapp.ui.detail.TripDetailScreen
import com.udistrital.viajesapp.ui.places.CreatePlaceScreen
import com.udistrital.viajesapp.ui.trips.CreateTripScreen
import com.udistrital.viajesapp.ui.trips.TripListScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.TripList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.TripList.route) {
            TripListScreen(
                onAddTripClick = {
                    navController.navigate(Screen.CreateTrip.route)
                },
                onTripClick = { tripId ->
                    navController.navigate(Screen.TripDetail.createRoute(tripId))
                }
            )
        }

        composable(route = Screen.CreateTrip.route) {
            CreateTripScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onTripCreated = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.TripDetail.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId").orEmpty()
            TripDetailScreen(
                tripId = tripId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAddPlaceClick = { id ->
                    navController.navigate(Screen.CreatePlace.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.CreatePlace.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId").orEmpty()
            CreatePlaceScreen(
                tripId = tripId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onPlaceCreated = {
                    navController.popBackStack()
                }
            )
        }
    }
}
