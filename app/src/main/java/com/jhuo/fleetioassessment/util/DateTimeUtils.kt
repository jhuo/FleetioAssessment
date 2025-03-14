package com.jhuo.fleetioassessment.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm:ss a")

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDateTime(dateTimeString: String): OffsetDateTime {
        return OffsetDateTime.parse(dateTimeString)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(dateTime: OffsetDateTime): String {
        return dateTime.format(formatter)
    }
}