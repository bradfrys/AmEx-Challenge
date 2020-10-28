package me.brad.config

import me.brad.model.KafkaMessage
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig {

    @Autowired
    private lateinit var kafkaProducerProperties: KafkaProducerProperties

    @Bean
    fun producerFactory(): ProducerFactory<String?, KafkaMessage?> =
        DefaultKafkaProducerFactory(producerConfigs(), stringKeySerializer(), kafkaMessageJsonSerlializer())

    @Bean
    fun producerConfigs(): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[BOOTSTRAP_SERVERS_CONFIG] = kafkaProducerProperties.bootstrap
        return props
    }

    @Bean
    fun kafkaMessageTemplate(): KafkaTemplate<String?, KafkaMessage?> {
        val kafkaTemplate: KafkaTemplate<String?, KafkaMessage?> = KafkaTemplate(producerFactory())
        kafkaTemplate.defaultTopic = kafkaProducerProperties.topic
        return kafkaTemplate
    }

    @Bean
    fun stringKeySerializer(): Serializer<String> = StringSerializer()

    @Bean
    fun kafkaMessageJsonSerlializer(): Serializer<KafkaMessage> = JsonSerializer()

}
