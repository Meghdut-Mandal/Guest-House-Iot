package `in`.iot.lab.ghouse

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

object Util {
    const val hour = 1000L * 60 * 60
    const val day = hour * 24
    const val week = day * 7

    fun getDates(startDate: Date, nDates: Int): List<Date> {
        val start = startDate.removeTime().time
        val days = nDates / 2
        return (-days..days).map { Date(start + day * it) }
    }

    private val df: SimpleDateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())

    val currentDate: Date
        get() = Calendar.getInstance().time

    fun Date.formatDayMonth() = df.format(this)

    fun Date.format(style: String): String {
        val f = SimpleDateFormat(style)
        return f.format(this)
    }

    fun Date.removeTime(): Date {
        val cal = Calendar.getInstance()
        cal.time = this
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.time
    }

    fun Long.toDate(): Date {
        return Date(this)
    }

    fun <T> List<T>.randomElement(): T {
        return this[Random.nextInt(size)]
    }


}


fun <T : Any> diffUtil(): DiffUtil.ItemCallback<T>{
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
