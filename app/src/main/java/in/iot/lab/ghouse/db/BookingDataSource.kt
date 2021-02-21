package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.Util.day
import `in`.iot.lab.ghouse.Util.toDate
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.RvItem
import androidx.paging.PageKeyedDataSource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class BookingDataSource(startTime: Long) : PageKeyedDataSource<Int, RvItem>() {

    private var initialQuery: Query
    private var lastVisible: DocumentSnapshot? = null
    private var lastPageReached = false
    private var pageNumber = 1
    private val bookingRef = Firebase.firestore.collection("bookings")
    private val itemsPerPage = 50L

    init {
        initialQuery = if (startTime > 0) {
            bookingRef.whereGreaterThanOrEqualTo("startTime", startTime)
                .orderBy("startTime", Query.Direction.ASCENDING).limit(itemsPerPage)
        } else {
            bookingRef.orderBy("startTime", Query.Direction.ASCENDING).limit(itemsPerPage)
        }
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, RvItem>
    ) {
        println("in.iot.lab.ghouse.db>BookingDataSource>loadInitial   ")
        initialQuery.get().addOnCompleteListener { initialTask ->
            if (initialTask.isSuccessful) {

                val bookingList: List<Booking> =
                    initialTask.result?.toObjects(Booking::class.java) ?: listOf()
                val items = getItems(bookingList)
                println("in.iot.lab.ghouse.db>BookingDataSource>loadInitial  ${items.size} ")
                callback.onResult(items, null, pageNumber)

                if (bookingList.isNotEmpty()) {
                    lastVisible = initialTask.result?.documents?.last()
                }
            } else {
                logError(initialTask.exception ?: Exception("NOT DEFINED!!"))
            }

        }


    }



    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RvItem>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RvItem>) {
        println("in.iot.lab.ghouse.db>BookingDataSource>loadAfter  $pageNumber ")
        val nextQuerry = initialQuery.startAfter(lastVisible)
        nextQuerry.get().addOnCompleteListener { nextTask ->
            if (nextTask.isSuccessful) {
                if (!lastPageReached) {
                    val bookingList: List<Booking> =
                        nextTask.result?.toObjects(Booking::class.java) ?: listOf()
                    val items = getItems(bookingList)
                    callback.onResult(items, pageNumber)
                    pageNumber++

                    if (bookingList.size < itemsPerPage) {
                        lastPageReached = true
                    } else {
                        lastVisible = nextTask.result?.documents?.last()
                    }

                }
            } else {
                logError(nextTask.exception ?: Exception("NOT DEFINED!!"))
            }

        }

    }

    private fun getItems(bookingList: List<Booking>): List<RvItem> {
        if (bookingList.isEmpty())
            return emptyList()

        val minDate = bookingList.minOf { it.startTime } - day
        val maxDate = bookingList.maxOf { it.endTime } + day

        val dateItems = (minDate..maxDate step day).map { RvItem.DateItem(it.toDate()) }
        val bookingItems: MutableList<RvItem> = dateItems.map { it.date.time }.flatMap { time ->
            bookingList.filter { it.startTime <= time && it.endTime >= time }
                .map { RvItem.BookingItem(it, time + 100) }
        }.toMutableList()

        bookingItems.addAll(dateItems)
        return bookingItems.sortedBy { it.id }
    }

    private fun logError(exception: Exception) {
        println("in.iot.lab.ghouse.db>BookingDataSource>loadInitial  ERROR ${exception.message} ")
        exception.printStackTrace()
    }

}