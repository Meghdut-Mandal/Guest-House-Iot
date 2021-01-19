package `in`.iot.lab.ghouse.ui.main.booking.steps

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.models.Customer
import `in`.iot.lab.ghouse.models.CustomerDetails
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doOnTextChanged
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.step_customer_layout.view.*

class CustomerDetailsStep : Step<Customer>("Customer Details") {

    val customerDetails = Customer()

    override fun getStepData() = customerDetails

    override fun getStepDataAsHumanReadableString() = customerDetails.name

    override fun restoreStepData(data: Customer) {
        customerDetails.name = data.name
        customerDetails.phoneNumber = data.phoneNumber
    }

    override fun isStepDataValid(stepData: Customer): IsDataValid {
        if (stepData.name.isBlank()) {
            return IsDataValid(false, "Customer Name required")
        } else if (stepData.phoneNumber.isBlank()) {
            return IsDataValid(false, "PhoneNumber Required")
        }

        return IsDataValid(true)
    }

    override fun createStepContentLayout(): View {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.step_customer_layout, null, false)
        layout.customerNameField.doOnTextChanged { text, start, before, count ->
            customerDetails.name = text.toString()
            markAsCompletedOrUncompleted(true)
        }
        layout.customerPhoneField.doOnTextChanged { text, start, before, count ->
            customerDetails.phoneNumber = text.toString()
            markAsCompletedOrUncompleted(true)
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