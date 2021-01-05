package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.Util.currentDate
import `in`.iot.lab.ghouse.Util.getDates
import `in`.iot.lab.ghouse.Util.week
import `in`.iot.lab.ghouse.db.LocalDB
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.BookingItem
import `in`.iot.lab.ghouse.models.DateItem
import `in`.iot.lab.ghouse.models.LoggedInData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private var bookings: MutableList<Booking> = arrayListOf()

    val bookingItemLiveData = MutableLiveData<List<BookingItem>>()
    val dates = MutableLiveData<List<DateItem>>()

    init {
        loadBookingTime(currentDate.time)
    }


    fun setLogin(status: Boolean) {
        LocalDB.loginStatus = status
    }

    fun setLoginData(account: GoogleSignInAccount?) {
        LocalDB.loginStatus = true
        val data = LoggedInData(account?.email ?: "nil", account?.displayName ?: "nill")
        LocalDB.loggedInData = data
    }

    fun loadBookingTime(startTime: Long) {
        bookings = LocalDB.getBookings(startTime, startTime - week).toMutableList()
        val dateList = getDates(currentDate, 30)
        val dateItems = dateList.map { DateItem(it) }
        dates.postValue(dateItems)
        val bookingItems = dateList.map { it.time }.flatMap { date ->
            bookings.filter { it.startTime <= date && it.endTime >= date }
                .map { getBookingItem(it) }
        }
        bookingItemLiveData.postValue(bookingItems)

    }

    fun getBookingItem(booking: Booking): BookingItem {
        val room = LocalDB.getRoom(booking.roomId)
        val customer = LocalDB.getCustomer(booking.customerId)
        return BookingItem(booking, room, customer)
    }


}