package com.distance.route.event

import com.distance.route.domain.AwayEquipment
import com.distance.route.domain.enum.EventType
import com.distance.route.repository.AwayEquipmentRepository
import com.distance.route.stream.NotificationMobileDTO
import com.distance.route.util.haversineDistance
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class EventAwayEquipment(
        val awayEquipmentRepository: AwayEquipmentRepository
):Observer {

    private val log = LoggerFactory.getLogger(EventAwayEquipment::class.java)

    fun processEvent(notificationMobileDTO: NotificationMobileDTO){
        val distance = haversineDistance(
                notificationMobileDTO.lastCoordinate.latitude,
                notificationMobileDTO.lastCoordinate.longitude,
                notificationMobileDTO.lastCoordinateMobile.latitude,
                notificationMobileDTO.lastCoordinateMobile.longitude,
                )
        log.info("distance [{}]", distance)
        if(distance > 0.5){
            val away = awayEquipmentRepository.getAwayEquipmentByRoute_Id(notificationMobileDTO.lastCoordinate.route.id)
            if(away.isPresent){
                val newAwayEquipment = AwayEquipment(null, notificationMobileDTO.lastCoordinate.route, `when` = Date(), active = true, type = EventType.AWAY)
                awayEquipmentRepository.save(newAwayEquipment)

            }else{
                // criar awayEquipment
            }
        }


    }
    override fun update(o: Observable?, notificationMobileDTO: Any?) {
       if(notificationMobileDTO!=null && notificationMobileDTO is NotificationMobileDTO){
           log.info("Chegouuu [{}]", notificationMobileDTO )
           processEvent(notificationMobileDTO)
       }
    }
}