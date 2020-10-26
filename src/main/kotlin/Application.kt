fun main(args: Array<String>) {
    println("Welcome. Enter an empty line to exit.")
    println("Apples = $0.60, Oranges = $0.25")

    print("Enter order (space separated list): ")
    var order = readOrder()

    while ((!order?.get(0)?.isEmpty()!!)) {
        println("Order total = $${calculateTotal(order)}")

        print("Enter order (space separated list): ")
        order = readOrder()
    }
}

fun readOrder() = readLine()?.split(" ")?.map { it }

fun calculateTotal(order: List<String>): Double {
    var orderTotal = 0.0
    order.forEach {
        if (it.equals("apple", true))
            orderTotal += 0.6
        else if (it.equals("orange", true))
            orderTotal += 0.25
    }
    return orderTotal
}
