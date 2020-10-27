import service.OffersService

fun main(args: Array<String>) {
    println("Welcome. Enter an empty line to exit.")
    println("Apples = $0.60, Oranges = $0.25")

    print("Enter order (space separated list): ")
    var order = readOrder()

    while ((!order?.get(0)?.isEmpty()!!)) {
        println("Order total = $${OffersService.calculateTotal(order)}")

        print("Enter order (space separated list): ")
        order = readOrder()
    }
}

fun readOrder() = readLine()?.split(" ")?.map { it }
