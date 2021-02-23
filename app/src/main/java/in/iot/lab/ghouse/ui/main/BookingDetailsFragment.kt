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
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.checkbox.isCheckPromptChecked
import kotlinx.android.synthetic.main.fragment_booking_details.*


class BookingDetailsFragment : Fragment() {


    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            MainViewModel::class.java
        )
    }
    var bookingId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_details, container, false)
    }

    override fun onResume() {
        super.onResume()
        booking_deatils_toolbar.inflateMenu(R.menu.booking_details_menu)
        booking_deatils_toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.delete_item) {
                tryDeleting()
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }
        booking_deatils_toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        mainViewModel.selectedBooking.observe(viewLifecycleOwner) {
            if (it != null) {
                loadData(it)
            }
        }
    }

    private fun tryDeleting() {
        MaterialDialog(requireActivity()).show {
            val materialDialog = this
            title(text = "Delete this Booking")
            message(text = "This operation can't reverted back in anyway!")

            checkBoxPrompt(text = "I understand risk involved.") {}
            positiveButton(text="Ok") { dialog ->
                val isChecked = dialog.isCheckPromptChecked()
                // do something
                if (isChecked) {
                    mainViewModel.deleteBooking(bookingId)
                    materialDialog.dismiss()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun loadData(booking: Booking) {
        room_name.text = "Room ${booking.room}"
        bookingId = booking.id
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
        payment_type.text =
            "${booking.payment?.paymentMethod}: Advance- ₹${booking.payment?.paymentAdvance} \n ₹${booking.payment?.paymentRemaining} due"

    }

    private fun callNumber(booking: Booking) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + booking.customer?.phoneNumber)
        startActivity(intent)
    }


}