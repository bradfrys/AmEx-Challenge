package me.brad.service

import org.springframework.stereotype.Service

@Service
class OffersService {

    fun calculateTotal(orderMap: Map<String, Int>): Double {
        // Apples are buy 1, get 1 free
        // Oranges are buy 2, get 1 free
        return calculateSalePrice(0.6, 0.6, 2, orderMap.getOrDefault("apple", 0)) +
                calculateSalePrice(0.25, 0.5, 3, orderMap.getOrDefault("orange", 0))
    }

    /**
     * This function is a general equation for "buy x, get 1 free".
     * Integer division is used to find the price of all sale items,
     * and modulus is used to find the price of the remaining items.
     */
    private fun calculateSalePrice(unitPrice: Double, salePrice: Double, saleQuantity: Int, purchaseQuantity: Int): Double
            = ((purchaseQuantity / saleQuantity) * salePrice) + ((purchaseQuantity % saleQuantity) * unitPrice)

}
