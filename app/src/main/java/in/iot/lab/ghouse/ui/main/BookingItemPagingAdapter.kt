package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.diffUtil
import `in`.iot.lab.ghouse.models.RvItem
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil



class BookingItemPagingAdapter : PagedListAdapter<RvItem, RvViewHolder>(diffUtil<RvItem>()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> {
                val inflatedView = inflater.inflate(R.layout.room_item, null, false)
                RvViewHolder.BookingItemViewHolder(inflatedView)
            }
            else -> {
                val inflatedView = inflater.inflate(R.layout.date_header, null, false)
                RvViewHolder.DateItemViewHolder(inflatedView)
            }
        }

    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        val item = getItem(position)?: return
        getItemViewType(position)
        holder.bindData(item)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return 0
        return when (item) {
            is RvItem.BookingItem -> 1
            is RvItem.DateItem -> 2
        }
    }
}

