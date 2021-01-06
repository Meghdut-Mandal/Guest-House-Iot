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
import androidx.lifecycle.ViewModelProvider
import ernestoyaquello.com.verticalstepperform.Step
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener
import kotlinx.android.synthetic.main.fragment_new_booking.*
import kotlinx.android.synthetic.main.fragment_new_booking.view.*
import java.util.*


class NewBooking : Fragment(), StepperFormListener {
    val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_booking, container, false)
        view.stepper_form.setup(
            this,
            durationStep,
            roomStep,
            customerDetailsStep,
            paymentDetailsStep
        )
            .init()
        return view
    }

    private val customerDetailsStep by lazy { CustomerDetailsStep() }
    private val durationStep by lazy {
        DurationStep().apply {
            onDurationDone = ::loadRooms
        }
    }
    private val paymentDetailsStep by lazy { PaymentDetailsStep() }
    private val roomStep by lazy { RoomStep() }

    override fun onResume() {
        super.onResume()
        mainViewModel.freeRoomLiveData.observe(viewLifecycleOwner) {
            roomStep.setUpRooms(it)
        }
    }

    private fun loadRooms(duration: Pair<Date, Date>) {
        mainViewModel.loadFreeRooms(duration)
    }

    override fun onCompletedForm() {

    }

    override fun onCancelledForm() {

    }


}