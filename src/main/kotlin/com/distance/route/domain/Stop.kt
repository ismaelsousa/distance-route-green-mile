package com.distance.route.domain

import java.util.*

data class Stop(
        val id:Int,
        val latitude: Double,
        val longitude:Double,
        val arrivalAt:Date,
        val departureAt:Date,
        val address: String
)