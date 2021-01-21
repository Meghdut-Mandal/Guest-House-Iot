package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.MaterialColorPalette
import `in`.iot.lab.ghouse.Util.formatDayMonth
import `in`.iot.lab.ghouse.Util.toDate
import `in`.iot.lab.ghouse.models.RvItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.date_header.view.*
import kotlinx.android.synthetic.main.room_item.view.*

sealed class RvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindData(rvItem: RvItem)
    abstract var onClickListner: (RvItem) -> Unit

    class BookingItemViewHolder(itemView: View) : RvViewHolder(itemView) {

        override fun bindData(rvItem: RvItem) {
            val bookingItem = rvItem as RvItem.BookingItem
            itemView.room_number.text = bookingItem.booking.room
            itemView.customer_name.text = bookingItem.booking.customer?.name ?: ""
            itemView.endtime.text = bookingItem.booking.endTime.toDate().formatDayMonth()
            itemView.setOnClickListener {
                onClickListner(rvItem)
            }
        }

        override var onClickListner: (RvItem) -> Unit = {}
    }

    class DateItemViewHolder(itemView: View) : RvViewHolder(itemView) {

        override fun bindData(rvItem: RvItem) {
            val dateItem = rvItem as RvItem.DateItem
            itemView.header_field.text = dateItem.date.formatDayMonth()
            itemView.setBackgroundColor(
                MaterialColorPalette.getRandomColor(
                    "200",
                    dateItem.date.time
                )
            )
            itemView.setOnClickListener {
                onClickListner(rvItem)
            }
        }

        override var onClickListner: (RvItem) -> Unit = {}
    }
}


