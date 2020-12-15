package com.distance.route.domain

import com.distance.route.domain.enum.EventType
import java.util.*

data class Event(
        val id: Int? = null,
        val eventType: EventType,
        val `when`: Date = Date(),
        val stopId: Int
)