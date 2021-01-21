package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.diffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class GenericAdapter<T : Any>(
    @LayoutRes val layout: Int,
    val bindData: View.(T, Int) -> Unit
) :
    ListAdapter<T, GenericAdapter.GenericViewModel>(diffUtil<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewModel {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(layout, null, false)
        return GenericViewModel(layout)
    }

    override fun onBindViewHolder(holder: GenericViewModel, position: Int) {
        holder.itemView.bindData(getItem(position), position)
    }
    class GenericViewModel(itemView: View) : RecyclerView.ViewHolder(itemView)
}

