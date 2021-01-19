package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.models.Booking
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import java.util.*


fun <T : Any> diffUtil():DiffUtil.ItemCallback<T>{
    return object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            return oldItem == newItem
        }
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


class BookingItemAdapter : ListAdapter<StickyHeaderItems, StickyHeaderItemsViewHolder>(diffUtil<StickyHeaderItems>()){

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