package me.brad.service

import me.brad.dao.InventoryDao
import me.brad.model.InventoryItem
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.*

class OrderServiceTest {

    @Mock lateinit var inventoryDao: InventoryDao
    @Mock lateinit var offersService: OffersService
    @Mock lateinit var notificationService: NotificationService

    @InjectMocks
    lateinit var orderService: OrderService

    @Before
    fun initMocks() {
        initMocks(this)
        `when`(offersService.calculateTotal(anyMap())).thenReturn(0.0)
    }

    @Test
    fun shouldAllowOrderWhenItemsInStock() {
        `when`(inventoryDao.getOne("apple")).thenReturn(InventoryItem("apple", 999))
        `when`(inventoryDao.getOne("orange")).thenReturn(InventoryItem("orange", 999))
        orderService.makeOrder(mapOf("apple" to 5, "orange" to 5))
        verify(notificationService).notifyUserOfSuccess(anyInt())
        verify(inventoryDao).save(InventoryItem("apple", 994))
        verify(inventoryDao).save(InventoryItem("orange", 994))
    }

    @Test
    fun shouldNotAllowOrderWhenItemsOutOfStock() {
        `when`(inventoryDao.getOne("apple")).thenReturn(InventoryItem("apple", 0))
        `when`(inventoryDao.getOne("orange")).thenReturn(InventoryItem("orange", 1))
        orderService.makeOrder(mapOf("apple" to 1, "orange" to 2))
        verify(notificationService).notifyUserOfFailure()
        verify(inventoryDao, never()).save(any())
    }

}
