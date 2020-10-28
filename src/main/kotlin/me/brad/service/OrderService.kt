package me.brad.service

import me.brad.dao.InventoryDao
import me.brad.model.InventoryItem
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class OrderService(val offersService: OffersService, val inventoryDao: InventoryDao, val kafkaMessageService: KafkaMessageService) {

    fun makeOrder(order: Map<String, Int>) {
        kafkaMessageService.publishOrderReceived()

        // return true if all orderItems are present in the database AND are stocked for the purchase
        val allItemsInStock = order.all { orderItem ->
            inventoryDao.existsById(orderItem.key) && orderItem.value <= inventoryDao.getOne(orderItem.key).itemCount
        }

        if (allItemsInStock) {
            println("Order total = $${"%.2f".format(offersService.calculateTotal(order))}")
            order.forEach { orderItem -> decrementInventoryCount(orderItem.key, orderItem.value) }
            kafkaMessageService.publishOrderSuccess(Random.nextInt(1, 14))
        } else {
            kafkaMessageService.publishOrderFailure()
        }
    }

    fun decrementInventoryCount(item: String, orderCount: Int) {
        val inventoryCount = inventoryDao.getOne(item).itemCount
        inventoryDao.save(InventoryItem(item, inventoryCount - orderCount))
    }

}
