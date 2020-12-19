package com.distance.route.domain

import com.distance.route.domain.enum.EventType
import java.util.*
import javax.persistence.*

@Entity
class AwayEquipment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Int?=null,
        @OneToOne
        @JoinColumn(name="route_id", referencedColumnName = "id")
        val route: Route,
        val `when`: Date=Date(),
        val active:Boolean=false,
        type: EventType
)