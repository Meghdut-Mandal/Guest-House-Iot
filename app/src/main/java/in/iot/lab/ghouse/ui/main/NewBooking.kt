package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.db.LocalDb
import `in`.iot.lab.ghouse.db.Resource
import `in`.iot.lab.ghouse.models.Booking
import `in`.iot.lab.ghouse.models.Request
import `in`.iot.lab.ghouse.ui.main.booking.steps.CustomerDetailsStep
import `in`.iot.lab.ghouse.ui.main.booking.steps.DurationStep
import `in`.iot.lab.ghouse.ui.main.booking.steps.PaymentDetailsStep
import `in`.iot.lab.ghouse.ui.main.booking.steps.RoomStep
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener
import kotlinx.android.synthetic.main.fragment_new_booking.*
import kotlinx.android.synthetic.main.fragment_new_booking.view.*
import java.lang.Exception
import java.util.*


class NewBooking : Fragment(), StepperFormListener {
    private val mainViewModel by lazy {
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
        progressBar.isIndeterminate = true

        progressBar.isVisible = false
    }

    private fun loadRooms(duration: Pair<Date, Date>) {
        mainViewModel.loadFreeRooms(duration).observe(this) {
            when (it) {
                Resource.Loading -> {
                    roomStep.setUpRooms(listOf())
                    progressBar.isVisible = true
                }
                is Resource.Success<*> -> {
                    progressBar.isVisible = false
                    val room = it as Resource.Success<List<String>>
                    roomStep.setUpRooms(room.value)
                }
                is Resource.Faliure -> {
                    progressBar.isVisible = false

                    error("Failed to Load rooms")
                }
            }
        }
    }

    override fun onCompletedForm() {
        val duration = durationStep.dates
        val roomName = roomStep.stepData
        val payment = paymentDetailsStep.stepData
        val customerDetails = customerDetailsStep.stepData
        val id = System.currentTimeMillis().toString()
        val booking = Booking(
            id,
            duration.first.time,
            duration.second.time,
            customerDetails,
            LocalDb.email,
            roomName,
            payment
        )
        mainViewModel.addBooking(booking).observe(this) {

            when (it) {
                is Resource.Loading -> {
                    progressBar.isVisible = true
                }
                is Resource.Success<*> -> {
                    mainViewModel.requestLiveData.postValue(Request.ReloadViewModel)
                    findNavController().navigate(R.id.action_newBooking_to_bookingsFragment)
                }
                is Resource.Faliure -> {
                    progressBar.isVisible = false
                    error("Failed to add Booking!")
                }
            }
        }
    }

    private fun error(msg: String) {
        Snackbar.make(stepper_form, msg, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onCancelledForm() {
        findNavController().navigate(R.id.action_newBooking_to_bookingsFragment)
    }


}