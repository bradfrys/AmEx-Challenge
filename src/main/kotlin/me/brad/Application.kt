package me.brad

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import me.brad.service.NotificationService
import me.brad.service.OffersService
import kotlin.random.Random

@SpringBootApplication
class Application(val offersService: OffersService, val notificationService: NotificationService) : CommandLineRunner{

    override fun run(vararg args: String?) {
        println("Welcome. Enter an empty line to exit.")
        println("Apples = $0.60, Oranges = $0.25")

        print("Enter order (space separated list): ")
        var order = readOrder()

        while ((!order?.get(0)?.isEmpty()!!)) {
            println("Order total = $${"%.2f".format(offersService.calculateTotal(order))}")
            notificationService.notifyUser(Random.nextInt(1, 14))

            print("Enter order (space separated list): ")
            order = readOrder()
        }
    }

    fun readOrder() = readLine()?.split(" ")?.map { it }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
