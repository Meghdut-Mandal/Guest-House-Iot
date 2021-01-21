package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.Util
import `in`.iot.lab.ghouse.Util.randomElement
import `in`.iot.lab.ghouse.models.*
import kotlin.random.Random

object SampleDB  {

    private val names = arrayListOf("Roshan Singh", "Sambit Majhi", "Nilanjan Manna", "Anmol Jain")
    private val roomList = (10..30).map { Room("$it", "10$it", "") }
    private val customerList =
        (1..9).map { Customer(names.randomElement(), "2323232323") }
    private val bookingList by lazy { getBooking() }
    private val paymentTypes = arrayListOf("UPI", "Cash", "None")
    private val paymentsList = (1..10).map {
        Payment(
            paymentTypes.randomElement(),
            Random.nextDouble(100.0, 1000.0).toString()
        )
    }


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
                    customerList.randomElement(),
                    "megdhut",
                    roomList.randomElement().roomCode,
                    paymentsList[0]
                )
            }.toMutableList()
    }

     private fun getBookings(startTime: Long, endTime: Long): List<Booking> {
        return bookingList.filter { it.startTime >= startTime && endTime <= endTime }
    }

     fun getRoom(id: String): Room {
        return roomList[id.hashCode() % roomList.size]
    }

     fun getFreeRooms(startTime: Long, endTime: Long): List<Room> {
        return roomList
    }

     fun getCustomer(id: String): Customer {
        return customerList[id.hashCode() % customerList.size]
    }

     fun getActiveBookings(): List<Booking> {
        return getBookings(Util.currentDate.time, Util.currentDate.time - Util.hour)
    }

     fun getRecentPayments(): List<Payment> {
        return paymentsList.subList(0, 10)
    }


}