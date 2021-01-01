package `in`.iot.lab.ghouse.models

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.Util.formatDayMonth
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.date_header.view.*
import java.util.*

class DateItem(val date: Date) : AbstractItem<DateItem.ViewHolder>() {

    companion object {
        val UniqueID = 2323
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<DateItem>(view) {
        override fun bindView(item: DateItem, payloads: List<Any>) {
            itemView.header_field.text = item.date.formatDayMonth()
        }

        override fun unbindView(item: DateItem) {
            itemView.header_field.text = "NONE"
        }

    }

    override val layoutRes: Int
        get() = R.layout.date_header
    override val type: Int
        get() = UniqueID

    override fun getViewHolder(v: View) = ViewHolder(v)

}