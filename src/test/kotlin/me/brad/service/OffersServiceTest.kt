package me.brad.service

import junit.framework.Assert.assertEquals
import org.junit.Test

class OffersServiceTest {

    val offersService = OffersService()

    @Test
    fun shouldIgnoreItemCase() {
        assertEquals(offersService.calculateTotal(mapOf("apple" to 1)), 0.6)
        assertEquals(offersService.calculateTotal(mapOf("orange" to 1)), 0.25)
    }

    @Test
    fun shouldCalculateAppleSalePrice() {
        // Apples are buy 1, get 1 free
        assertEquals(offersService.calculateTotal(mapOf("apple" to 2)), 0.6)
        assertEquals(offersService.calculateTotal(mapOf("apple" to 3)), 1.2)
        assertEquals(offersService.calculateTotal(mapOf("apple" to 4)), 1.2)
    }

    @Test
    fun shouldCalculateOrangeSalePrice() {
        // Oranges are 3 for the price of 2
        assertEquals(offersService.calculateTotal(mapOf("orange" to 2)), 0.5)
        assertEquals(offersService.calculateTotal(mapOf("orange" to 4)), 0.75)
        assertEquals(offersService.calculateTotal(mapOf("orange" to 5)), 1.0)
        assertEquals(offersService.calculateTotal(mapOf("orange" to 6)), 1.0)
    }

}
