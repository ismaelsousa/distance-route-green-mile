package com.distance.route.domain
import java.util.*
data class LastCoordinate(
        val id:Int?=null,
        val equipment: Equipment,
        val latitude:Double,
        val longitude:Double,
        val `when`: Date = Date(),
        val route: Route


)