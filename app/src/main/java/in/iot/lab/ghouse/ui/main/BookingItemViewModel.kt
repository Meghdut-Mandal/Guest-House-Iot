package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.MaterialColorPalette
import `in`.iot.lab.ghouse.Util.formatDayMonth
import `in`.iot.lab.ghouse.Util.toDate
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.date_header.view.*
import kotlinx.android.synthetic.main.room_item.view.*

sealed class StickyHeaderItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindData(stickyHeaderItems: StickyHeaderItems)
}


class BookingItemViewHolder(itemView: View) : StickyHeaderItemsViewHolder(itemView) {

    override fun bindData(stickyHeaderItems: StickyHeaderItems) {
        val bookingItem = stickyHeaderItems as StickyHeaderItems.BookingItem
        itemView.room_number.text = bookingItem.roomName
        itemView.customer_name.text = bookingItem.customerName
        itemView.endtime.text = bookingItem.booking.endTime.toDate().formatDayMonth()
    }
}

class DateItemViewHolder(itemView: View) : StickyHeaderItemsViewHolder(itemView) {

    override fun bindData(stickyHeaderItems: StickyHeaderItems) {
        val dateItem = stickyHeaderItems as StickyHeaderItems.DateItem
        itemView.header_field.text = dateItem.date.formatDayMonth()
        itemView.setBackgroundColor(MaterialColorPalette.getRandomColor("200",dateItem.date.time))
    }
}