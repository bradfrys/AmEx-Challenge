package me.brad.service

import me.brad.dao.InventoryDao
import me.brad.model.InventoryItem
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class OrderService(val offersService: OffersService, val notificationService: NotificationService, val inventoryDao: InventoryDao) {

    fun makeOrder(order: Map<String, Int>) {
        val allItemsInStock = order.all { orderItem -> orderItem.value <= inventoryDao.getOne(orderItem.key).itemCount }

        if (allItemsInStock) {
            println("Order total = $${"%.2f".format(offersService.calculateTotal(order))}")
            order.forEach { orderItem -> decrementInventoryCount(orderItem.key, orderItem.value) }
            notificationService.notifyUserOfSuccess(Random.nextInt(1, 14))
        } else {
            notificationService.notifyUserOfFailure()
        }
    }

    fun decrementInventoryCount(item: String, orderCount: Int) {
        val inventoryCount = inventoryDao.getOne(item).itemCount
        inventoryDao.save(InventoryItem(item, inventoryCount - orderCount))
    }

}
