package `in`.iot.lab.ghouse.db

import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import java.util.*

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
                if (bookingResource is Resource.Faliure) {
                    offer(bookingResource)
                }
                val bookedRooms =
                    (bookingResource as Resource.Success<List<Booking>>).value.map { it.room ?: "" }
                println("in.iot.lab.ghouse.db>BookingDatabase>getFreeRoom  Found booked rooms $bookedRooms ")
                val freeRooms = rooms.filterNot { bookedRooms.contains(it) }
                offer(Resource.Success(freeRooms))
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


    fun listenToBookings(duration: Pair<Date, Date>) = callbackFlow {
        val addSnapshotListener = bookingRef
            .whereGreaterThanOrEqualTo("startTime", duration.first.time)
            .whereLessThanOrEqualTo("startTime", duration.second.time)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    offer(Resource.Faliure(error))
                } else {
                    val mainList = value?.toObjects(Booking::class.java) ?: listOf()
                    val list = mainList.filter { it.endTime <= duration.second.time }
                    offer(Resource.Success(list))
                }
            }

        awaitClose {
            addSnapshotListener.remove()
        }
    }


}
/*
Added Preet to Speed up Devlopement,40% work left on frontend ,
API's completed ;
13th jan Moving to FIreBase Firestore , due to issues with hosting
Blockers: Preet is having his inter
 */