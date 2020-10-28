package me.brad.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kafka.producer")
class KafkaProducerProperties {

    lateinit var bootstrap: String
    lateinit var topic: String

}
