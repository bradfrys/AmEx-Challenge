package me.brad.service

import me.brad.config.KafkaProducerProperties
import me.brad.model.KafkaMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class KafkaMessageService {

    @Autowired
    private lateinit var workUnitsKafkaTemplate: KafkaTemplate<String, KafkaMessage>
    @Autowired
    private lateinit var kafkaProducerProperties: KafkaProducerProperties

    fun publishOrderReceived() {
        val message = KafkaMessage(Random.nextInt().toString(), "Order submitted.")
        workUnitsKafkaTemplate.send("order-submitted", message.id, message)
    }

    fun publishOrderSuccess(deliveryDays: Int) {
        val message = KafkaMessage(Random.nextInt().toString(), deliveryDays.toString())
        workUnitsKafkaTemplate.send("order-success", message.id, message)
    }

    fun publishOrderFailure() {
        val message = KafkaMessage(Random.nextInt().toString(), "Order out of stock.")
        workUnitsKafkaTemplate.send("order-failure", message.id, message)
    }

}
