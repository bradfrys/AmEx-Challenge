import org.junit.Test

class ApplicationTest {

    @Test
    fun shouldCalculateOrderTotal() {
        assert(calculateTotal(listOf("Apple")) == 0.6)
        assert(calculateTotal(listOf("Orange")) == 0.25)
        assert(calculateTotal(listOf("APPLE", "APPLE", "ORANGE", "APPLE")) == 2.05)
        assert(calculateTotal(listOf("apple", "apple", "orange", "apple")) == 2.05)
    }

}