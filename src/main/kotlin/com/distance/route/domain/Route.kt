package com.distance.route.domain

import java.util.*

data class Route (
     val id:Int,
     val equipment: Equipment,
     val stops: List<Stop>,
     val datePlans:Date
)