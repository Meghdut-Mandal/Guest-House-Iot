package `in`.iot.lab.ghouse.ui.main.booking.steps

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.Util
import `in`.iot.lab.ghouse.Util.format
import `in`.iot.lab.ghouse.Util.formatDayMonth
import android.view.LayoutInflater
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.step_duration_layout.view.*
import java.util.*


class DurationStep : Step<Pair<Date, Date>>("Duration of Stay ") {

    var dates = Date() to Date()

    var onDurationDone: (Pair<Date, Date>) -> Unit = {}

    override fun getStepData(): Pair<Date, Date> {
        return dates
    }

    override fun getStepDataAsHumanReadableString(): String {
        return dates.first.formatDayMonth() + " to " + dates.second.formatDayMonth()
    }

    override fun restoreStepData(data: Pair<Date, Date>) {
        dates = data
    }

    override fun isStepDataValid(stepData: Pair<Date, Date>): IsDataValid {
        if (stepData.first.before(stepData.second) && (stepData.second.time - stepData.first.time) > Util.day) {
            return IsDataValid(true)
        }
        return IsDataValid(false)
    }

    override fun createStepContentLayout(): View {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.step_duration_layout, null, false)
        layout.editStartDate.setOnClickListener {
            MaterialDialog(context).show {
                datePicker(  minDate = Calendar.getInstance()) { dialog, calendar ->
                    dates = calendar.time to dates.second
                    layout.startDateField.text=calendar.time.format("dd.mm.yy")
                    markAsCompletedOrUncompleted(true)

                }
            }
        }

        layout.editEndDate.setOnClickListener {
            MaterialDialog(context).show {
                datePicker(
                     minDate = Calendar.getInstance()
                ) { dialog, calendar ->
                    dates = dates.first to calendar.time
                    layout.endDateField.text=calendar.time.format("dd.mm.yy")
                    markAsCompletedOrUncompleted(true)

                }
            }
        }
        return layout
    }

    override fun onStepOpened(animated: Boolean) {


    }

    override fun onStepClosed(animated: Boolean) {
        onDurationDone(dates)
    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {

    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {


    }


}