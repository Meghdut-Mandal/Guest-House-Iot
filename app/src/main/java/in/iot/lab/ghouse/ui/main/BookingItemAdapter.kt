package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.MaterialColorPalette
import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.Util.formatDayMonth
import `in`.iot.lab.ghouse.models.Booking
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.date_header.view.*
import java.util.*


val diff: DiffUtil.ItemCallback<StickyHeaderItems> =
    object : DiffUtil.ItemCallback<StickyHeaderItems>() {
        override fun areItemsTheSame(
            oldItem: StickyHeaderItems,
            newItem: StickyHeaderItems
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: StickyHeaderItems,
            newItem: StickyHeaderItems
        ): Boolean {
            return oldItem == newItem
        }
    }


sealed class StickyHeaderItems {
    class BookingItem(
        val booking: Booking,
        val roomName: String,
        val customerName: String, override val id: Long
    ) : StickyHeaderItems()

    class DateItem(val date: Date) : StickyHeaderItems() {
        override val id: Long
            get() = date.time
    }

    abstract val id: Long
}


class BookingItemAdapter : ListAdapter<StickyHeaderItems, StickyHeaderItemsViewHolder>(diff){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyHeaderItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> {
                val inflatedView = inflater.inflate(R.layout.room_item, null, false)
                BookingItemViewHolder(inflatedView)
            }
            else -> {
                val inflatedView = inflater.inflate(R.layout.date_header, null, false)
                DateItemViewHolder(inflatedView)
            }
        }

    }

    override fun onBindViewHolder(holder: StickyHeaderItemsViewHolder, position: Int) {
        val item = getItem(position)
        getItemViewType(position)
        holder.bindData(item)
    }

    override fun getItemViewType(position: Int) =
        when (getItem(position)) {
            is StickyHeaderItems.BookingItem -> 1
            is StickyHeaderItems.DateItem -> 2
        }
}