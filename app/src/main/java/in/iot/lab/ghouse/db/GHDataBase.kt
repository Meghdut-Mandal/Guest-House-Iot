package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.models.*

interface GHDataBase {
    var loginStatus: Boolean
    var loggedInData: LoggedInData?

    fun getBookings(startTime: Long, endTime: Long): List<Booking>
    fun getRoom(id: String): Room
    fun getFreRooms(startTime: Long, endTime: Long): List<Room>
    fun getCustomer(id: String): Customer
    fun getActiveBookings(): List<Booking>
    fun getRecentPayments(): List<Payment>
}