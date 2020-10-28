package me.brad

import me.brad.dao.InventoryDao
import me.brad.model.InventoryItem
import me.brad.service.OrderService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
@Profile("command")
class Runner(val orderService: OrderService, val inventoryDao: InventoryDao) : CommandLineRunner{

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
            println("Enter order (space separated list):")
            order = readOrder()
        }

        println("Exiting application.")
        exitProcess(0)
    }

    // reads console input, splits on " ", then maps each word to a count of its frequency
    fun readOrder() = readLine()?.split(" ")?.map { it }?.groupingBy { it.toLowerCase() }?.eachCount()

}
