package com.distance.route.event

import com.distance.route.domain.AwayEquipment
import com.distance.route.domain.Event
import com.distance.route.domain.enum.EventType
import com.distance.route.repository.AwayEquipmentRepository
import com.distance.route.repository.RouteRepository
import com.distance.route.stream.NotificationMobileDTO
import com.distance.route.util.haversineDistance
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*


@Component
class EventAwayEquipment(
        val awayEquipmentRepository: AwayEquipmentRepository,
        val routeRepository: RouteRepository
):Observer {

    private val log = LoggerFactory.getLogger(EventAwayEquipment::class.java)

    fun processEvent(notificationMobileDTO: NotificationMobileDTO){



        val distance = haversineDistance(
                notificationMobileDTO.lastCoordinate.latitude,
                notificationMobileDTO.lastCoordinate.longitude,
                notificationMobileDTO.lastCoordinateMobile.latitude,
                notificationMobileDTO.lastCoordinateMobile.longitude,
                )

        if(distance >= 0.5){
            /*
               Tratar caso em que o motorista está na hora de almoço
               Ou tempo livre
               Baseado na hora que a coordenada foi enviada
            */

            val hourSentCoordinate = notificationMobileDTO.lastCoordinateMobile.`when`.hours
            val scheduleWork = (hourSentCoordinate >= 7 &&  hourSentCoordinate <= 12) || ( hourSentCoordinate >= 14 && hourSentCoordinate<=18)
            if( !scheduleWork ){
                log.info("Não está na hora do trabalho")
                return
            }

            /*
                Trata caso que esteja longe do caminhão mas é um ponto de parada
                Ai o motorista pode sair do veículo para entregar ou chamar o cliente
            */

            val route = routeRepository.getRouteByEquipment_Id(notificationMobileDTO.lastCoordinate.route.id).get()
            val stops = route.stops.filter { stop ->
                val distanceStop = haversineDistance(
                        stop.latitude,
                        stop.longitude,
                        notificationMobileDTO.lastCoordinate.latitude,
                        notificationMobileDTO.lastCoordinate.longitude,
                )
                distanceStop <= geoFence
            }

            if(stops.isNotEmpty()){
                log.info("Está em uma parada, não precisa mandar evento de alerta")
                return
            }

            /*
               A partir daqui é necessário criar um alerta
            */

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