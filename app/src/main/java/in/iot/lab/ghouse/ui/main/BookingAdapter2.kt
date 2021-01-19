package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.models.Booking
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter


class BookingAdapter2 : PagedListAdapter<Booking, BookingItemViewHolder>
    (diffUtil<Booking>()) {
    var onCLick: (Booking) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(R.layout.room_item, null, false)
        return BookingItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: BookingItemViewHolder, position: Int) {
        val item = getItem(position)!!
//        holder.bindData(item)
    }

}