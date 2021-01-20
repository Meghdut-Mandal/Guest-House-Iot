package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.Util
import `in`.iot.lab.ghouse.Util.removeTime
import `in`.iot.lab.ghouse.Util.toDate
import `in`.iot.lab.ghouse.db.BookingDataSourceFactory
import `in`.iot.lab.ghouse.db.BookingDatabase
import `in`.iot.lab.ghouse.db.Resource
import `in`.iot.lab.ghouse.db.SampleDB
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Room
import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.util.*

@ExperimentalCoroutinesApi

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val freeRoomLiveData = MutableLiveData<List<String>>()
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
        bookingDb.listenToBookings(date to tmmrw,false).collect {
            emit(it)
        }
    }

    fun saveRoom(room: Room)= liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        bookingDb.saveRoom(room).collect {
            emit(it)
        }
    }

    fun loadPayments() {
    }


}