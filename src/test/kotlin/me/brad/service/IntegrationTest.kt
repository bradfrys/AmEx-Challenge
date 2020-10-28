package me.brad.service

import com.nhaarman.mockitokotlin2.any
import me.brad.dao.InventoryDao
import me.brad.model.InventoryItem
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.junit4.SpringRunner
import java.lang.Thread.*

@SpringBootTest
@RunWith(SpringRunner::class)
class IntegrationTest {

    @SpyBean lateinit var notificationService: NotificationService

    @Autowired lateinit var inventoryDao: InventoryDao
    @Autowired lateinit var orderService: OrderService

    @Test
    fun shouldCompleteSuccessfulOrder() {
        inventoryDao.save(InventoryItem("apple", 5))
        inventoryDao.save(InventoryItem("orange", 5))
        orderService.makeOrder(mapOf("apple" to 1, "orange" to 1))

        sleep(5000)

        verify(notificationService).notifyUserOnOrderConfirmation()
        verify(notificationService).notifyUserOfSuccess(any())
    }

    @Test
    fun shouldCompleteFailedOrder() {
        inventoryDao.save(InventoryItem("apple", 1))
        inventoryDao.save(InventoryItem("orange", 1))
        orderService.makeOrder(mapOf("apple" to 2, "orange" to 2))

        sleep(5000)

        verify(notificationService).notifyUserOnOrderConfirmation()
        verify(notificationService).notifyUserOfFailure()
    }

}
