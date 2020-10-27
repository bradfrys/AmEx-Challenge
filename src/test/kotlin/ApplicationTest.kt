import org.junit.Test
import service.OffersService.Companion.calculateTotal

class ApplicationTest {

    @Test
    fun shouldIgnoreItemCase() {
        assert(calculateTotal(listOf("apple")) == 0.6)
        assert(calculateTotal(listOf("orange")) == 0.25)
        assert(calculateTotal(listOf("APPLE")) == 0.6)
        assert(calculateTotal(listOf("ORANGE")) == 0.25)
    }

    @Test
    fun shouldCalculateAppleSalePrice() {
        // Apples are buy 1, get 1 free
        assert(calculateTotal(listOf("apple", "apple")) == 0.6)
        assert(calculateTotal(listOf("apple", "apple", "apple")) == 1.2)
        assert(calculateTotal(listOf("apple", "apple", "apple", "apple")) == 1.2)
    }

    @Test
    fun shouldCalculateOrangeSalePrice() {
        // Oranges are 3 for the price of 2
        assert(calculateTotal(listOf("orange", "orange", "orange")) == 0.5)
        assert(calculateTotal(listOf("orange", "orange", "orange", "orange")) == 0.75)
        assert(calculateTotal(listOf("orange", "orange", "orange", "orange", "orange")) == 1.0)
        assert(calculateTotal(listOf("orange", "orange", "orange", "orange", "orange", "orange")) == 1.0)
    }

}
