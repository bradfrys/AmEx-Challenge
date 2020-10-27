package me.brad.service

import org.springframework.stereotype.Service

@Service
class NotificationService {
    fun notifyUserOfSuccess(deliveryDays: Int) = println("Order received. Estimated delivery time: $deliveryDays days")
    fun notifyUserOfFailure() = println("Order failed. Some items are out of stock.")
}
