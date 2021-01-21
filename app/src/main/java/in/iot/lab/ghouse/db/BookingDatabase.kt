package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Room
import `in`.iot.lab.ghouse.models.RvItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import java.util.*

@ExperimentalCoroutinesApi
class BookingDatabase {

    private val firestore = Firebase.firestore

    private val bookingRef = firestore.collection("bookings")
    private val roomsRef = firestore.collection("rooms")

    fun addBooking(booking: Booking) = callbackFlow {
        bookingRef.add(booking).addOnSuccessListener {
            offer(Resource.Success(it))
        }.addOnFailureListener {
            offer(Resource.Faliure(it))
        }
        awaitClose {

        }
    }


    fun getFreeRoom(duration: Pair<Date, Date>) = callbackFlow {
        getRooms().collect { roomResource ->
            if (roomResource is Resource.Faliure) {
                offer(roomResource)
            }
            val rooms = (roomResource as Resource.Success<List<Room>>).value.map { it.roomCode }
            println("in.iot.lab.ghouse.db>BookingDatabase>getFreeRoom  Founf roooms $rooms ")
            getBookings(duration).collect { bookingResource ->
                when (bookingResource) {
                    is Resource.Faliure -> {
                        offer(bookingResource)
                    }
                    is Resource.Success -> {
                        val bookedRooms = bookingResource.value.map { it.room ?: "" }
                        println("in.iot.lab.ghouse.db>BookingDatabase>getFreeRoom  Found booked rooms $bookedRooms ")
                        val freeRooms = rooms.filterNot { bookedRooms.contains(it) }
                        offer(Resource.Success(freeRooms))
                    }
                }

            }
        }

        awaitClose {

        }
    }


    fun getRooms() = callbackFlow {
        roomsRef.get().addOnSuccessListener {
            val toObjects = it.toObjects(Room::class.java)
            offer(Resource.Success(toObjects))
        }.addOnFailureListener {
            offer(Resource.Faliure(it))
        }
        awaitClose {

        }
    }

    fun getBookings(duration: Pair<Date, Date>) = callbackFlow {
        bookingRef
            .whereGreaterThanOrEqualTo("startTime", duration.first.time)
            .whereLessThanOrEqualTo("startTime", duration.second.time)
            .get().addOnSuccessListener {
                val mainList = it.toObjects(Booking::class.java)
                val list = mainList.filter { it.endTime <= duration.second.time }
                offer(Resource.Success(list))
            }.addOnFailureListener {
                offer(Resource.Faliure(it))
            }
        awaitClose {

        }
    }


    fun listenToBookings(duration: Pair<Date, Date>, checkEndTime: Boolean = true) = callbackFlow {
        val addSnapshotListener = bookingRef
            .whereGreaterThanOrEqualTo("startTime", duration.first.time)
            .whereLessThanOrEqualTo("startTime", duration.second.time)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    offer(Resource.Faliure(error))
                } else {
                    val mainList = value?.toObjects(Booking::class.java) ?: listOf()
                    val list =
                        if (checkEndTime) mainList.filter { it.endTime <= duration.second.time } else mainList
                    offer(Resource.Success(list))
                }
            }

        awaitClose {
            addSnapshotListener.remove()
        }
    }

    fun listenToBookingsItems(duration: Pair<Date, Date>, checkEndTime: Boolean = true) = callbackFlow {
        val addSnapshotListener = bookingRef
            .whereGreaterThanOrEqualTo("startTime", duration.first.time)
            .whereLessThanOrEqualTo("startTime", duration.second.time)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    offer(Resource.Faliure(error))
                } else {
                    val mainList = value?.toObjects(Booking::class.java) ?: listOf()
                    val list =
                        if (checkEndTime) mainList.filter { it.endTime <= duration.second.time } else mainList
                    val items =
                        list.map { RvItem.BookingItem(it, it.startTime) }.sortedBy { it.id }
                    offer(Resource.Success(items))
                }
            }

        awaitClose {
            addSnapshotListener.remove()
        }
    }

    fun saveRoom(room: Room) = callbackFlow {
        roomsRef.add(room).addOnSuccessListener {
            offer(Resource.Success(true))
        }.addOnFailureListener {
            offer(Resource.Faliure(it))
        }
        awaitClose {

        }
    }



}
