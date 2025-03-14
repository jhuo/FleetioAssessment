package com.jhuo.fleetioassessment.util

sealed class Screen(val rout: String) {
    object VehicleList : Screen("vehicleList")
    object VehicleDetails : Screen("details")
    object CommentList : Screen("commentList")
}