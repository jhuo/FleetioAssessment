package com.jhuo.fleetioassessment.util

import com.jhuo.fleetioassessment.BuildConfig

object Constants {
    const val VEHICLE_DATABASE = "vehicle_database"
    const val VEHICLE_LIST_TABLE = "vehicle_list_table"
    const val VEHICLE_REMOTE_KEYS_TABLE = "vehicle_remote_keys_table"
    const val COMMENT_LIST_TABLE = "comment_list_table"


    const val BASE_URL = "https://secure.fleetio.com/api/"
    const val AUTH_HEADER: String = BuildConfig.FLEETIO_AUTH_TOKEN
    const val ACCOUNT_TOKEN: String = BuildConfig.FLEETIO_ACCOUNT_TOKEN
    const val PAGE_SIZE = 100
}