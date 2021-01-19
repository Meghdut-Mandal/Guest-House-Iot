package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.Util
import `in`.iot.lab.ghouse.Util.currentDate
import `in`.iot.lab.ghouse.Util.removeTime
import `in`.iot.lab.ghouse.Util.toDate
import `in`.iot.lab.ghouse.db.BookingDatabase
import `in`.iot.lab.ghouse.db.Resource
import `in`.iot.lab.ghouse.db.SampleDB
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Payment
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.collect
import java.util.*
import java.util.concurrent.Executor
import kotlin.coroutines.CoroutineContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dataBase = SampleDB

    val freeRoomLiveData = MutableLiveData<List<String>>()
    val activeRoomsLiveData = MutableLiveData<List<StickyHeaderItems.BookingItem>>()
    private val recentPaymentLiveData = MutableLiveData<List<Payment>>()
    private val bookingDb = BookingDatabase()
    private val executor: Executor
        get() = Dispatchers.IO.asExecutor()

    init {

        loadBookingTime(currentDate.time)
    }


    fun loadBookingTime(startTime: Long) {


    }

    fun addBooking(booking: Booking) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        bookingDb.addBooking(booking).collect {
            emit(it)
        }

    }

    fun loadFreeRooms(duration: Pair<Date, Date>) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        bookingDb.getFreeRoom(duration).collect {
            emit(it)
        }
    }


    fun loadActiveRooms() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        val date = Date().removeTime()
        val tmmrw = (date.time + Util.day).toDate()
        bookingDb.listenToBookings(date to tmmrw).collect{
            emit(it)
        }
    }

    fun loadPayments() {
        val payments = dataBase.getRecentPayments()
        recentPaymentLiveData.postValue(payments)
    }


}