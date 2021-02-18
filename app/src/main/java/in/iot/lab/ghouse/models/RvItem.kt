package `in`.iot.lab.ghouse.models

import java.util.*

sealed class RvItem {
    class BookingItem(
        val booking: Booking,
        override val id: Long
    ) : RvItem()

    class DateItem(val date: Date) : RvItem() {
        override val id: Long
            get() = date.time
    }

    abstract val id: Long
}