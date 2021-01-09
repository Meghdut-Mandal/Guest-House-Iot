package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.models.*
import okhttp3.OkHttpClient

object NetworkDB : GHDataBase{

    private const val URL = "http://52.66.152.100"


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

    override fun getRecentPayments(): List<Payment> {
        TODO("Not yet implemented")
    }


}