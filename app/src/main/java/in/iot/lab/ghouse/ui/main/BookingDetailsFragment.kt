package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_booking_details.*


class BookingDetailsFragment : Fragment() {

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
    }

}