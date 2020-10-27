package me.brad.service

import org.springframework.stereotype.Service

@Service
class NotificationService {
    fun notifyUser(deliveryDays: Int) = println("Order received. Estimated delivery time: $deliveryDays days")
}
