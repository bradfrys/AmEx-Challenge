package me.brad.config

import me.brad.model.KafkaMessage
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
@EnableKafka
class KafkaConsumerConfig {

    @Autowired
    private lateinit var kafkaConsumerProperties: KafkaConsumerProperties

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> =
            ConcurrentKafkaListenerContainerFactory<String, KafkaMessage>()
        factory.setConcurrency(1)
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String?, KafkaMessage?> =
        DefaultKafkaConsumerFactory(consumerProps(), stringKeyDeserializer(), kafkaMessageJsonDeserializer())

    @Bean
    fun consumerProps(): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[BOOTSTRAP_SERVERS_CONFIG] = kafkaConsumerProperties.bootstrap
        props[GROUP_ID_CONFIG] = kafkaConsumerProperties.group
        props[ENABLE_AUTO_COMMIT_CONFIG] = true
        props[AUTO_COMMIT_INTERVAL_MS_CONFIG] = "100"
        props[SESSION_TIMEOUT_MS_CONFIG] = "15000"
        return props
    }

    @Bean
    fun stringKeyDeserializer(): Deserializer<String> = StringDeserializer()

    @Bean
    fun kafkaMessageJsonDeserializer(): Deserializer<KafkaMessage> = JsonDeserializer(KafkaMessage::class.java)

}
