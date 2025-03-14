package com.jhuo.fleetioassessment.util

sealed class Screen(val rout: String) {
    object VehicleList : Screen("list")
    object VehicleOverview : Screen("overview")
}