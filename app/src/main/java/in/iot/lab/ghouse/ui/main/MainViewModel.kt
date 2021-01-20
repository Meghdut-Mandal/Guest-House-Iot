package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.Util
import `in`.iot.lab.ghouse.Util.currentDate
import `in`.iot.lab.ghouse.Util.removeTime
import `in`.iot.lab.ghouse.Util.toDate
import `in`.iot.lab.ghouse.db.BookingDataSourceFactory
import `in`.iot.lab.ghouse.db.BookingDatabase
import `in`.iot.lab.ghouse.db.Resource
import `in`.iot.lab.ghouse.db.SampleDB
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Payment
import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.collect
import java.util.*
import java.util.concurrent.Executor
@ExperimentalCoroutinesApi

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dataBase = SampleDB

    val freeRoomLiveData = MutableLiveData<List<String>>()
    val activeRoomsLiveData = MutableLiveData<List<RvItem.BookingItem>>()
    private val recentPaymentLiveData = MutableLiveData<List<Payment>>()
    private val bookingDb = BookingDatabase()
    private val config = PagedList.Config.Builder().build()
    private var bookingLiveData: LiveData<PagedList<RvItem>>? = null

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

    fun loadBooking(
        lifecycleOwner: LifecycleOwner,
        startTime: Long = -1
    ): LiveData<PagedList<RvItem>> {

        bookingLiveData?.removeObservers(lifecycleOwner)
        val factory = BookingDataSourceFactory(startTime)
        bookingLiveData = LivePagedListBuilder(factory, config).build()
        return bookingLiveData!!
    }


    fun loadActiveRooms() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        val date = Date().removeTime()
        val tmmrw = (date.time + Util.day).toDate()
        bookingDb.listenToBookings(date to tmmrw).collect {
            emit(it)
        }
    }

    fun loadPayments() {
        val payments = dataBase.getRecentPayments()
        recentPaymentLiveData.postValue(payments)
    }


}