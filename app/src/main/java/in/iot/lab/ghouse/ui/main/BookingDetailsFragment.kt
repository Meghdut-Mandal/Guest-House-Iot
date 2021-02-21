package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.Util.formatDayMonth
import `in`.iot.lab.ghouse.Util.toDate
import `in`.iot.lab.ghouse.models.Booking
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_booking_details.*


class BookingDetailsFragment : Fragment() {



    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
                MainViewModel::class.java
        )
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_details, container, false)
    }

    override fun onResume() {
        super.onResume()
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        mainViewModel.selectedBooking.observe(viewLifecycleOwner) {
            if (it != null) {
                loadData(it)
            }
        }
    }

    private fun loadData(booking: Booking) {
        room_name.text = "Room ${booking.room}"
        startDate.text = booking.startTime.toDate().formatDayMonth()
        endDate.text = booking.endTime.toDate().formatDayMonth()
        customerName.text = booking.customer?.name
        phoneNumber.text = booking.customer?.phoneNumber
        phoneNumber.setOnClickListener {
            callNumber(booking)
        }
        phoneIcon.setOnClickListener {
            callNumber(booking)
        }
        payment_type.text="${booking.payment?.paymentMethod}: Advance- ₹${booking.payment?.paymentAdvance} \n ₹${booking.payment?.paymentRemaining} due"

    }

    private fun callNumber(booking: Booking) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + booking.customer?.phoneNumber)
        startActivity(intent)
    }


}