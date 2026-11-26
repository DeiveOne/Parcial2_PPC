package com.udistrital.viajesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udistrital.viajesapp.ui.detail.TripDetailScreen
import com.udistrital.viajesapp.ui.detail.TripDetailViewModel
import com.udistrital.viajesapp.ui.places.CreatePlaceScreen
import com.udistrital.viajesapp.ui.trips.CreateTripScreen
import com.udistrital.viajesapp.ui.trips.TripListScreen
import com.udistrital.viajesapp.ui.trips.TripListViewModel

private const val KEY_TRIP_CREATED = "trip_created"
private const val KEY_PLACE_CREATED = "place_created"

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
        composable(route = Screen.TripList.route) { backStackEntry ->
            val viewModel: TripListViewModel = viewModel()
            val tripCreated by backStackEntry.savedStateHandle
                .getStateFlow(KEY_TRIP_CREATED, false)
                .collectAsState()

            LaunchedEffect(tripCreated) {
                if (tripCreated) {
                    viewModel.loadTrips(showLoading = false)
                    backStackEntry.savedStateHandle[KEY_TRIP_CREATED] = false
                }
            }

            TripListScreen(
                viewModel = viewModel,
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
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(KEY_TRIP_CREATED, true)
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
            val viewModel: TripDetailViewModel = viewModel()
            val placeCreated by backStackEntry.savedStateHandle
                .getStateFlow(KEY_PLACE_CREATED, false)
                .collectAsState()

            // Al volver de registrar un lugar: recargar el detalle y consumir el aviso.
            LaunchedEffect(placeCreated) {
                if (placeCreated) {
                    viewModel.loadTripDetail(tripId, showLoading = false)
                    backStackEntry.savedStateHandle[KEY_PLACE_CREATED] = false
                }
            }

            TripDetailScreen(
                tripId = tripId,
                viewModel = viewModel,
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
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(KEY_PLACE_CREATED, true)
                    navController.popBackStack()
                }
            )
        }
    }
}