package `in`.iot.lab.ghouse.ui.main.adapters

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.diffUtil
import `in`.iot.lab.ghouse.models.RvItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class BookingItemAdapter : ListAdapter<RvItem, RvViewHolder>(diffUtil<RvItem>()) {

    private var onClickListener: (RvItem) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> {
                val inflatedView = inflater.inflate(R.layout.room_item, null, false)
                RvViewHolder.BookingItemViewHolder(inflatedView,onClickListener)
            }
            else -> {
                val inflatedView = inflater.inflate(R.layout.date_header, null, false)
                RvViewHolder.DateItemViewHolder(inflatedView,onClickListener)
            }
        }

    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        val item = getItem(position) ?: return
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