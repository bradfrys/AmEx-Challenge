package me.brad.service

import me.brad.model.KafkaMessage
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class NotificationService {

    companion object {
        const val ANSI_GREEN = "\u001B[32m"
        const val ANSI_RED = "\u001B[31m"
        const val ANSI_RESET = "\u001B[0m"
    }

    @KafkaListener(topics = ["order-submitted"])
    fun notifyUserOnOrderConfirmation() = println("${ANSI_GREEN}Order received.${ANSI_RESET}")

    @KafkaListener(topics = ["order-success"])
    fun notifyUserOfSuccess(kafkaMessage: KafkaMessage) = println("${ANSI_GREEN}Order successful. Estimated delivery time: ${kafkaMessage.message} days${ANSI_RESET}")

    @KafkaListener(topics = ["order-failure"])
    fun notifyUserOfFailure() = println("${ANSI_RED}Order failed. Some items are out of stock.${ANSI_RESET}")

}
