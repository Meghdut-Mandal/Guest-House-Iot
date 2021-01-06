package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.Util
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Customer
import `in`.iot.lab.ghouse.models.LoggedInData
import `in`.iot.lab.ghouse.models.Room
import kotlin.random.Random

object SampleDB : GHDataBase {
    override var loginStatus: Boolean = false

    override var loggedInData: LoggedInData? = null

    val roomList = (1..7).map { Room("$it", "10$it", null, false) }
    val customerList = (1..9).map { Customer("$it", "pancard", "Ramu $it", "2323232323") }
    val bookingList = getBooking()

    private fun getBooking(): MutableList<Booking> {
        val initialTime = Util.currentDate.time - (15 * Util.day)

        return (1..30).toList()
            .map { initialTime + Random.nextInt(30) * Util.day }
            .map { startTime ->
                val endTime = startTime + Random.nextInt(6) * Util.day
                Booking(
                    "${Random.nextLong(startTime) + 1}",
                    startTime,
                    endTime,
                    roomList.randomElement().id,
                    customerList.randomElement().id,
                    "dsds",
                    "dsds"
                )
            }.toMutableList()
    }

    override fun getBookings(startTime: Long, endTime: Long): List<Booking> {
        return bookingList.filter { it.startTime >= startTime && endTime <= endTime }
    }

    override fun getRoom(id: String): Room {
        return roomList[id.hashCode() % roomList.size]
    }

    override fun getCustomer(id: String): Customer {
        return customerList[id.hashCode() % customerList.size]
    }

    private fun <T> List<T>.randomElement(): T {
        return this[Random.nextInt(size)]
    }

}