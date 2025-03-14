package com.jhuo.fleetioassessment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jhuo.fleetioassessment.presentation.comment_listings.CommentListScreen
import com.jhuo.fleetioassessment.presentation.vehicle_details.VehicleDetailsScreen
import com.jhuo.fleetioassessment.presentation.vehicle_listings.VehicleListScreen
import com.jhuo.fleetioassessment.ui.theme.FleetioAssessmentTheme
import com.jhuo.fleetioassessment.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FleetioAssessmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.VehicleList.rout
                    ) {
                        composable(Screen.VehicleList.rout) {
                            VehicleListScreen(navController)
                        }

                        composable(
                            Screen.VehicleDetails.rout + "/{vehicleId}",
                            arguments = listOf(
                                navArgument("vehicleId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            VehicleDetailsScreen(navController)
                        }

                        composable(
                            Screen.CommentList.rout + "/{vehicleId}",
                            arguments = listOf(
                                navArgument("vehicleId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            CommentListScreen(navController)
                        }
                    }
                }
            }
        }
    }
}