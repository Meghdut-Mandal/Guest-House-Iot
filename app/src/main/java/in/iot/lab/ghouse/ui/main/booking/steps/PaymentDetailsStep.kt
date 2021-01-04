package `in`.iot.lab.ghouse.ui.main.booking.steps

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.models.PaymentDetails
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.step_payment_layout.view.*


class PaymentDetailsStep: Step<PaymentDetails>("Payment ") {
    private var paymentDetails = PaymentDetails()
    override fun getStepData(): PaymentDetails {
        return paymentDetails
    }

    override fun getStepDataAsHumanReadableString(): String {
        return "${paymentDetails.type} "
    }

    override fun restoreStepData(data: PaymentDetails) {
        paymentDetails = data
    }

    override fun isStepDataValid(stepData: PaymentDetails): IsDataValid {
        if (stepData.info.isNotEmpty()){
            return IsDataValid(true)
        }
        return  IsDataValid(false,"Payment Info required")
    }

    override fun createStepContentLayout(): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.step_payment_layout, null, false)
        val spinnerArray = arrayListOf("Cash", "UPI", "Not Yet Done")
        val spinnerArrayAdapter = ArrayAdapter(
            context, android.R.layout.simple_spinner_dropdown_item, spinnerArray
        )
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