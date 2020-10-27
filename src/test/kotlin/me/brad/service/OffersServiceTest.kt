package me.brad.service

import junit.framework.Assert.assertEquals
import org.junit.Test

class OffersServiceTest {

    val offersService = OffersService()

    @Test
    fun shouldIgnoreItemCase() {
        assertEquals(offersService.calculateTotal(listOf("apple")), 0.6)
        assertEquals(offersService.calculateTotal(listOf("orange")), 0.25)
        assertEquals(offersService.calculateTotal(listOf("APPLE")), 0.6)
        assertEquals(offersService.calculateTotal(listOf("ORANGE")), 0.25)
    }

    @Test
    fun shouldCalculateAppleSalePrice() {
        // Apples are buy 1, get 1 free
        assertEquals(offersService.calculateTotal(listOf("apple", "apple")), 0.6)
        assertEquals(offersService.calculateTotal(listOf("apple", "apple", "apple")), 1.2)
        assertEquals(offersService.calculateTotal(listOf("apple", "apple", "apple", "apple")), 1.2)
    }

    @Test
    fun shouldCalculateOrangeSalePrice() {
        // Oranges are 3 for the price of 2
        assertEquals(offersService.calculateTotal(listOf("orange", "orange", "orange")), 0.5)
        assertEquals(offersService.calculateTotal(listOf("orange", "orange", "orange", "orange")), 0.75)
        assertEquals(offersService.calculateTotal(listOf("orange", "orange", "orange", "orange", "orange")), 1.0)
        assertEquals(offersService.calculateTotal(listOf("orange", "orange", "orange", "orange", "orange", "orange")), 1.0)
    }

}
