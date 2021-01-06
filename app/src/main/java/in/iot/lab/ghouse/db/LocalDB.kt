package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Customer
import `in`.iot.lab.ghouse.models.LoggedInData
import `in`.iot.lab.ghouse.models.Room
import io.paperdb.Book
import io.paperdb.Paper

object LocalDB : GHDataBase {
    override fun getBookings(startTime: Long, endTime: Long): List<Booking> {
        val read = book.read("bookings", arrayListOf<Booking>())
        return read.filter { it.startTime in startTime..endTime }
    }

    private val book: Book by lazy { Paper.book() }

    override var loginStatus: Boolean
        get() = book.read("login", false)
        set(value) {
            book.write("login", value)
        }

    override var loggedInData: LoggedInData?
        get() = book.read("loggedinData")
        set(value) {
            book.write("loggedinData", value)
        }

    override fun getRoom(id: String): Room {
        val read = book.read("rooms", arrayListOf<Room>())
        return read.first { it.id == id }
    }

    override fun getFreRooms(startTime: Long, endTime: Long): List<Room> {
        return arrayListOf()
    }

    override fun getCustomer(id: String): Customer {
        val read = book.read("customers", arrayListOf<Customer>())
        return read.first { it.id == id }
    }

    override fun getActiveBookings(): List<Booking> {
       return arrayListOf()
    }


}