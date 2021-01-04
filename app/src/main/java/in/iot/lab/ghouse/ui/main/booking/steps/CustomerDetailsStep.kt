package `in`.iot.lab.ghouse.ui.main.booking.steps

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.models.CustomerDetails
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doOnTextChanged
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.step_customer_layout.view.*

class CustomerDetailsStep : Step<CustomerDetails>("Customer Details") {

    val customerDetails = CustomerDetails()

    override fun getStepData() = customerDetails

    override fun getStepDataAsHumanReadableString() = customerDetails.name

    override fun restoreStepData(data: CustomerDetails) {
        customerDetails.name = data.name
        customerDetails.phoneNumber = data.phoneNumber
    }

    override fun isStepDataValid(stepData: CustomerDetails): IsDataValid {
        if (stepData.name.isBlank()) {
            return IsDataValid(false, "Customer Name required")
        } else if (stepData.phoneNumber.isBlank()) {
            return IsDataValid(false, "PhoneNumber Required")
        } else if (!"^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d\$".toRegex()
                .matches(stepData.phoneNumber)
        ) {
            return IsDataValid(false, "Give Proper phone number, Eg 9800000001")
        }
        return IsDataValid(true)
    }

    override fun createStepContentLayout(): View {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.step_customer_layout, null, false)
        layout.customerNameField.doOnTextChanged { text, start, before, count ->
            customerDetails.name = text.toString()
        }
        layout.customerPhoneField.doOnTextChanged { text, start, before, count ->
            customerDetails.phoneNumber = text.toString()
        }
        return layout
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