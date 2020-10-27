package me.brad

import me.brad.dao.InventoryDao
import me.brad.model.InventoryItem
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import me.brad.service.OrderService

@SpringBootApplication
class Application(val orderService: OrderService, val inventoryDao: InventoryDao) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Populating database for demo purposes
        inventoryDao.save(InventoryItem("apple", 5))
        inventoryDao.save(InventoryItem("orange", 5))

        println("Welcome. Enter an empty line to exit.")
        println("Apples = $0.60, Oranges = $0.25")

        print("Enter order (space separated list): ")
        var order = readOrder()

        while (!order!!.containsKey("")) {
            orderService.makeOrder(order)
            print("Enter order (space separated list): ")
            order = readOrder()
        }
    }

    fun readOrder() = readLine()?.split(" ")?.map { it }?.groupingBy { it.toLowerCase() }?.eachCount()

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
