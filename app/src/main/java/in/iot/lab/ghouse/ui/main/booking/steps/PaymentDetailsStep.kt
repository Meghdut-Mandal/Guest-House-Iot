package `in`.iot.lab.ghouse.ui.main.booking.steps

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.models.Payment
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.step_payment_layout.view.*


class PaymentDetailsStep : Step<Payment>("Payment ") {
    private var info = ""
    private var method = ""
    override fun getStepData(): Payment {
        return Payment(method, info)
    }

    override fun getStepDataAsHumanReadableString(): String {
        return "$method $info"
    }

    override fun restoreStepData(data: Payment) {
        info = data.info
        method = data.paymentMethod
    }

    override fun isStepDataValid(stepData: Payment): IsDataValid {
        if (stepData.info.isNotEmpty()) {
            return IsDataValid(true)
        }
        return IsDataValid(false, "Payment Info required")
    }

    override fun createStepContentLayout(): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.step_payment_layout, null, false)
        val spinnerArray = arrayListOf("Cash", "UPI", "None")
        val spinnerArrayAdapter = ArrayAdapter(
            context, android.R.layout.simple_spinner_dropdown_item, spinnerArray
        )
        view.paymentInfo.doOnTextChanged { text, start, before, count ->
            info = text.toString()
            markAsCompletedOrUncompleted(true)
        }
        view.paymentType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                method = spinnerArray[pos]
                markAsCompletedOrUncompleted(true)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        view.paymentType.setSelection(2)
        view.paymentType.adapter = spinnerArrayAdapter
        return view
    }

    override fun onStepOpened(animated: Boolean) {
    }

    override fun onStepClosed(animated: Boolean) {
    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }
}