package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.models.CustomerDetails
import `in`.iot.lab.ghouse.ui.main.booking.steps.CustomerDetailsStep
import `in`.iot.lab.ghouse.ui.main.booking.steps.DurationStep
import `in`.iot.lab.ghouse.ui.main.booking.steps.PaymentDetailsStep
import `in`.iot.lab.ghouse.ui.main.booking.steps.RoomStep
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ernestoyaquello.com.verticalstepperform.Step
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener
import kotlinx.android.synthetic.main.fragment_new_booking.*
import java.util.*


class NewBooking : Fragment(), StepperFormListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_booking, container, false)
    }

    override fun onResume() {
        super.onResume()
        val customerDetailsStep = CustomerDetailsStep()
        val durationStep = DurationStep()
        durationStep.onDurationDone = ::loadRooms
        val paymentDetailsStep = PaymentDetailsStep()
        val roomStep = RoomStep()
        stepper_form.setup(this, durationStep, roomStep, customerDetailsStep, paymentDetailsStep)

    }

    private fun loadRooms(duration: Pair<Date, Date>) {


    }

    override fun onCompletedForm() {

    }

    override fun onCancelledForm() {

    }


}