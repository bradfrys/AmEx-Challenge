package me.brad.service

import com.nhaarman.mockitokotlin2.any
import me.brad.dao.InventoryDao
import me.brad.model.InventoryItem
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.*
import java.lang.Thread.sleep

class OrderServiceTest {

    @Mock lateinit var inventoryDao: InventoryDao
    @Mock lateinit var offersService: OffersService
    @Mock lateinit var kafkaMessageService: KafkaMessageService

    @InjectMocks
    lateinit var orderService: OrderService

    @Before
    fun initMocks() {
        initMocks(this)
        `when`(offersService.calculateTotal(anyMap())).thenReturn(0.0)
    }

    @Test
    fun shouldAllowOrderWhenItemsInStock() {
        `when`(inventoryDao.existsById("apple")).thenReturn(true)
        `when`(inventoryDao.existsById("orange")).thenReturn(true)
        `when`(inventoryDao.getOne("apple")).thenReturn(InventoryItem("apple", 999))
        `when`(inventoryDao.getOne("orange")).thenReturn(InventoryItem("orange", 999))

        orderService.makeOrder(mapOf("apple" to 5, "orange" to 5))
        sleep(5000)

        verify(kafkaMessageService).publishOrderReceived()
        verify(kafkaMessageService).publishOrderSuccess(anyInt())
        verify(inventoryDao).save(InventoryItem("apple", 994))
        verify(inventoryDao).save(InventoryItem("orange", 994))
    }

    @Test
    fun shouldNotAllowOrderWhenItemsOutOfStock() {
        `when`(inventoryDao.getOne("apple")).thenReturn(InventoryItem("apple", 0))
        `when`(inventoryDao.getOne("orange")).thenReturn(InventoryItem("orange", 1))

        orderService.makeOrder(mapOf("apple" to 1, "orange" to 2))
        sleep(5000)

        verify(kafkaMessageService).publishOrderReceived()
        verify(kafkaMessageService).publishOrderFailure()
        verify(inventoryDao, never()).save(any())
    }

}
