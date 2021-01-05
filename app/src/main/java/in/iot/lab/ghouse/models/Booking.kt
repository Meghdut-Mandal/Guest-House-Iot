package `in`.iot.lab.ghouse.models

import `in`.iot.lab.ghouse.MaterialColorPalette
import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.Util.formatDayMonth
import android.annotation.SuppressLint
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.room_item.view.*
import java.util.*


data class Booking(
    val id: String,
    val startTime: Long,
    val endTime: Long,
    val roomId: String,
    val customerId: String,
    val authorId: String,
    val paymentsId: String
)


@SuppressLint("ResourceType")
class BookingItem(val booking: Booking, val room: Room, val customer: Customer) :
    AbstractItem<BookingItem.ViewHolder>() {
    class ViewHolder(view: View) : FastAdapter.ViewHolder<BookingItem>(view) {
        override fun bindView(item: BookingItem, payloads: List<Any>) {
            itemView.room_number.text = item.room.roomCode
            itemView.customer_name.text = item.customer.name
            itemView.endtime.text = Date(item.booking.endTime).formatDayMonth()
            val randomColor = MaterialColorPalette.getRandomColor("100")
            itemView.setBackgroundColor(randomColor)
        }

        override fun unbindView(item: BookingItem) {
            itemView.room_number.text = ""
            itemView.customer_name.text = ""
            itemView.endtime.text = ""
        }
    }

    override val layoutRes: Int
        get() = R.layout.room_item
    override val type: Int
        get() = 34

    override fun getViewHolder(v: View) = ViewHolder(v)
}
