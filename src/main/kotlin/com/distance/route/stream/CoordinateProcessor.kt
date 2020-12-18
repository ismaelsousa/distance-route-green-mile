package com.distance.route.stream

import com.distance.route.domain.Coordinate
import com.distance.route.domain.Equipment
import com.distance.route.domain.LastCoordinate
import com.distance.route.repository.LastCoordinateRepository
import com.distance.route.repository.RouteRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class CoordinateProcessor(
        private val routeRepository: RouteRepository,
        private val lastCoordinateRepository: LastCoordinateRepository
):Observable() {

    private  val log = LoggerFactory.getLogger(CoordinateProcessor::class.java)
    /*
    * Está recebendo dados do kafka
    * */
    fun receiveCoordinate(coordinate: Coordinate){
        log.info("Coordinate received: [{}] ", coordinate)
        val routeOptional = routeRepository.getRouteByEquipment_Id(coordinate.equipmentId)
        if(routeOptional.isPresent){
            val route = routeOptional.get()
            val lastCoordinateOptional = lastCoordinateRepository.getLastCoordinateByEquipment_Id(coordinate.equipmentId)
            val lastCoordinate = if(lastCoordinateOptional.isPresent){
                val lastCoordinate = lastCoordinateOptional.get()
                val updatedLastCoordinate = lastCoordinate.copy(latitude = coordinate.latitude, longitude = coordinate.longitude, `when`=coordinate.datePing)
                lastCoordinateRepository.save(updatedLastCoordinate)
                lastCoordinate
            } else{
                val equipment = Equipment(id=coordinate.equipmentId)
                val last = LastCoordinate(id = null, equipment = equipment, longitude = coordinate.longitude, latitude = coordinate.latitude, route = route)
                lastCoordinateRepository.save(last)
            }
            setChanged()
            notifyObservers(NotificationDto(coordinate, lastCoordinate))

        }

    }

}