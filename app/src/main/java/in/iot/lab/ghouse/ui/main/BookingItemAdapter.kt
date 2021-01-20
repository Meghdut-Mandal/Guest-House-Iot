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


class BookingItemAdapter : ListAdapter<RvItem, RvViewHolder>(diffUtil<RvItem>()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
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

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        val item = getItem(position)
        getItemViewType(position)
        holder.bindData(item)
    }

    override fun getItemViewType(position: Int) =
        when (getItem(position)) {
            is RvItem.BookingItem -> 1
            is RvItem.DateItem -> 2
        }
}