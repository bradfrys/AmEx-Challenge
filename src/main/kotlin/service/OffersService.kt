package service

class OffersService {

    companion object {
        fun calculateTotal(orderList: List<String>): Double {
            val orderMap = orderList.groupingBy { it.toLowerCase() }.eachCount()
            // Apples are buy 1, get 1 free
            // Oranges are buy 2, get 1 free
            return calculateSalePrice(0.6, 0.6, 2, orderMap.getOrDefault("apple", 0)) +
                    calculateSalePrice(0.25, 0.5, 3, orderMap.getOrDefault("orange", 0))
        }

        fun calculateSalePrice(unitPrice: Double, salePrice: Double, saleQuantity: Int, purchaseQuantity: Int): Double
                = ((purchaseQuantity / saleQuantity) * salePrice) + ((purchaseQuantity % saleQuantity) * unitPrice)
    }

}
