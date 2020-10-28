package me.brad.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kafka.consumer")
class KafkaConsumerProperties {

    lateinit var bootstrap: String
    lateinit var group: String
    lateinit var topic: String

}
