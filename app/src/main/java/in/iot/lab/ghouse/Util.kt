package `in`.iot.lab.ghouse

import java.text.SimpleDateFormat
import java.util.*

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

    fun Date.removeTime(): Date {
        val cal = Calendar.getInstance()
        cal.time = this
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.time
    }

}