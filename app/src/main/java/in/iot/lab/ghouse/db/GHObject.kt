package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Customer
import `in`.iot.lab.ghouse.models.LoggedInData
import `in`.iot.lab.ghouse.models.Room
import okhttp3.OkHttpClient

object GHObject : GHDataBase{

    private const val URL = "http://52.66.152.100"
    override var loginStatus: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}
    override var loggedInData: LoggedInData?
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun getBookings(checkIn: Long, checkOut: Long): List<Booking> {
        TODO("Not yet implemented")
    }

    override fun getRoom(id: String): Room {
        TODO("Not yet implemented")
    }

    override fun getFreRooms(startTime: Long, endTime: Long): List<Room> {
        TODO("Not yet implemented")
    }

    override fun getCustomer(id: String): Customer {
        TODO("Not yet implemented")
    }

    override fun getActiveBookings(): List<Booking> {
        TODO("Not yet implemented")
    }




}