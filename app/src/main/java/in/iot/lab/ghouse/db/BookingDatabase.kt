package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.Util.day
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
            offer(Resource.Failure(it))
        }
        awaitClose {

        }
    }


    fun getFreeRoom(duration: Pair<Date, Date>) = callbackFlow {
        getRooms().collect { roomResource ->
            if (roomResource is Resource.Failure) {
                offer(roomResource)
            }
            val rooms = (roomResource as Resource.Success<List<Room>>).value.map { it.roomCode }
            println("in.iot.lab.ghouse.db>BookingDatabase>getFreeRoom  Founf roooms $rooms ")
            getBookings(duration).collect { bookingResource ->
                when (bookingResource) {
                    is Resource.Failure -> {
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


    private fun getRooms() = callbackFlow {
        roomsRef.get().addOnSuccessListener {
            val toObjects = it.toObjects(Room::class.java)
            offer(Resource.Success(toObjects))
        }.addOnFailureListener {
            offer(Resource.Failure(it))
        }
        awaitClose {

        }
    }

    private fun getBookings(duration: Pair<Date, Date>) = callbackFlow {
        bookingRef
            .whereLessThanOrEqualTo("startTime", duration.second.time)
            .whereGreaterThanOrEqualTo("startTime", duration.first.time)
            .get().addOnSuccessListener {
                val mainList = it.toObjects(Booking::class.java)
                offer(Resource.Success(mainList))
            }.addOnFailureListener {
                offer(Resource.Failure(it))
            }
        awaitClose {

        }
    }

    fun getBookings(phoneNumber: String) = callbackFlow {
        bookingRef
            .whereEqualTo("customer.phoneNumber", phoneNumber)
            .get().addOnSuccessListener {
                val sortedBy =
                    it.toObjects(Booking::class.java).map { RvItem.BookingItem(it, it.startTime) }
                        .sortedBy { it.id }
                offer(Resource.Success(sortedBy))
            }.addOnFailureListener {
                offer(Resource.Failure(it))
            }
        awaitClose {

        }
    }


    fun listenToBookings(duration: Pair<Date, Date>, checkEndTime: Boolean = true) = callbackFlow {
        val addSnapshotListener = bookingRef
            .whereLessThanOrEqualTo("startTime", duration.second.time)
            .whereGreaterThanOrEqualTo("startTime", duration.first.time)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    offer(Resource.Failure(error))
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

    fun listenToBookingsItems(duration: Pair<Date, Date>, checkEndTime: Boolean = true) =
        callbackFlow {
            val addSnapshotListener = bookingRef
                .whereGreaterThanOrEqualTo("startTime", duration.first.time)
                .whereLessThanOrEqualTo("startTime", duration.second.time)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        offer(Resource.Failure(error))
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
            offer(Resource.Failure(it))
        }
        awaitClose {

        }
    }

    fun listenToActiveRooms(today: Date) = callbackFlow {
        val addSnapshotListener = bookingRef
            .whereLessThanOrEqualTo("startTime", today.time)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    offer(Resource.Failure(error))
                } else {
                    val mainList = value?.toObjects(Booking::class.java) ?: listOf()
                    val list =
                        mainList.filter { it.endTime >= (today.time + day) }
                    val items =
                        list.map { RvItem.BookingItem(it, it.startTime) }.sortedBy { it.id }
                    offer(Resource.Success(items))
                }
            }

        awaitClose {
            addSnapshotListener.remove()
        }
    }

    fun removeBooking(bookingId: String) {
        bookingRef
            .whereEqualTo("id", bookingId).addSnapshotListener { value, error ->
                value?.documents?.forEach {
                    it.reference.delete()
                }
            }
    }


}
