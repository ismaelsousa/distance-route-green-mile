package com.distance.route.event

import com.distance.route.stream.NotificationMobileDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class EventAwayEquipment():Observer {

    private val log = LoggerFactory.getLogger(EventAwayEquipment::class.java)

    override fun update(o: Observable?, notificationMobileDTO: Any?) {
       if(notificationMobileDTO!=null && notificationMobileDTO is NotificationMobileDTO){
           log.info("Chegouuu [{}]", notificationMobileDTO )
       }
    }
}