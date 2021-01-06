package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.Util.currentDate
import `in`.iot.lab.ghouse.Util.getDates
import `in`.iot.lab.ghouse.Util.toDate
import `in`.iot.lab.ghouse.Util.week
import `in`.iot.lab.ghouse.db.GHDataBase
import `in`.iot.lab.ghouse.db.SampleDB
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.LoggedInData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val dataBase: GHDataBase = SampleDB

    val stickeyItemLiveData = MutableLiveData<List<StickyHeaderItems>>()

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
        val bookings = dataBase.getBookings(startTime, startTime - week).toMutableList()
        val stickyItems = arrayListOf<StickyHeaderItems>()
        val dateList = getDates(startTime.toDate(), 30)
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


}