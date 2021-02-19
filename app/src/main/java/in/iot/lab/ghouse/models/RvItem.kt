package `in`.iot.lab.ghouse.models

import androidx.annotation.Keep
import java.util.*

@Keep
sealed class RvItem {
    @Keep
    class BookingItem(
        val booking: Booking,
        override val id: Long
    ) : RvItem()

    @Keep
    class DateItem(val date: Date) : RvItem() {
        override val id: Long
            get() = date.time
    }

    abstract val id: Long
}