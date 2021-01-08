package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.Util
import `in`.iot.lab.ghouse.Util.randomElement
import `in`.iot.lab.ghouse.models.*
import kotlin.random.Random

object SampleDB : GHDataBase {
    override var loginStatus: Boolean = false

    override var loggedInData: LoggedInData? = null
    private val names = arrayListOf("Roshan Singh", "Sambit Majhi", "Nilanjan Manna", "Anmol Jain")
    private val roomList = (10..30).map { Room("$it", "10$it", null, false) }
    private val customerList =
        (1..9).map { Customer("$it", "pancard", names.randomElement(), "2323232323") }
    private val bookingList = getBooking()
    private val paymentTypes = arrayListOf("UPI", "Cash", "None")
    val paymentsList = bookingList.map {
        Payment(
            it.paymentsId,
            paymentTypes.randomElement(),
            it.customerId,
            it.startTime,
            Random.nextDouble(100.0,1000.0)
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

    override fun getFreRooms(startTime: Long, endTime: Long): List<Room> {
        return roomList.filter { Random.nextBoolean() }
    }

    override fun getCustomer(id: String): Customer {
        return customerList[id.hashCode() % customerList.size]
    }

    override fun getActiveBookings(): List<Booking> {
        return getBookings(Util.currentDate.time, Util.currentDate.time - Util.hour)
    }

    override fun getRecentPayments(): List<Payment> {
       return  paymentsList.subList(0,10)
    }


}