package com.distance.route.repository

import com.distance.route.domain.AwayEquipment
import org.springframework.data.jpa.repository.JpaRepository

interface AwayEquipmentRepository:JpaRepository<AwayEquipment, Int> {
}