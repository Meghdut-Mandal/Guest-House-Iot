package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.Util
import `in`.iot.lab.ghouse.Util.currentDate
import `in`.iot.lab.ghouse.Util.getDates
import `in`.iot.lab.ghouse.Util.toDate
import `in`.iot.lab.ghouse.Util.week
import `in`.iot.lab.ghouse.db.GHDataBase
import `in`.iot.lab.ghouse.db.SampleDB
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.LoggedInData
import `in`.iot.lab.ghouse.models.Payment
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val dataBase: GHDataBase = SampleDB

    val stickeyItemLiveData = MutableLiveData<List<StickyHeaderItems>>()
    val freeRoomLiveData = MutableLiveData<List<String>>()
    val activeRoomsLiveData = MutableLiveData<List<StickyHeaderItems.BookingItem>>()
    val recentPaymentLiveData = MutableLiveData<List<Payment>>()

    init {

        loadBookingTime(currentDate.time)
    }


    fun setLogin(status: Boolean) {
        dataBase.loginStatus = status
    }

    fun setLoginData(account: GoogleSignInAccount?) {
        dataBase.loginStatus = true
        val data = LoggedInData(account?.email ?: "nil", account?.displayName ?: "nill")
        dataBase.loggedInData = data
    }

    fun loadBookingTime(startTime: Long) {
        val bookings = dataBase.getBookings(startTime, startTime - (30 * Util.day)).toMutableList()

        val startDate = bookings.minOf { it.startTime }
        val endDate = bookings.maxOf { it.endTime }
        val stickyItems = arrayListOf<StickyHeaderItems>()
        val dateList = (startDate..endDate step Util.day).map { it.toDate() }
        stickyItems.addAll(dateList.map { StickyHeaderItems.DateItem(it) })


        val bookingItems = dateList.map { it.time }.flatMap { date ->
            bookings.filter { it.startTime <= date && it.endTime >= date }
                .map { getBookingItem(it, date) }
        }
        stickyItems.addAll(bookingItems)
        stickyItems.sortBy { it.id }
        stickeyItemLiveData.postValue(stickyItems)
    }

    fun getBookingItem(booking: Booking, date: Long): StickyHeaderItems.BookingItem {
        val room = dataBase.getRoom(booking.roomId)
        val customer = dataBase.getCustomer(booking.customerId)
        return StickyHeaderItems.BookingItem(booking, room.roomCode, customer.name, date + 10)
    }

    fun loadFreeRooms(duration: Pair<Date, Date>) {
        val freeRooms = dataBase.getFreRooms(duration.first.time, duration.second.time)
        freeRoomLiveData.postValue(freeRooms.map { it.roomCode })
    }

    fun loadActiveRooms() {
        val bookings = dataBase.getActiveBookings()
        activeRoomsLiveData.postValue(bookings.map { getBookingItem(it, currentDate.time) })
    }

    fun loadPayments() {
        val payments = dataBase.getRecentPayments()
        recentPaymentLiveData.postValue(payments)
//        payments.ma
    }


}