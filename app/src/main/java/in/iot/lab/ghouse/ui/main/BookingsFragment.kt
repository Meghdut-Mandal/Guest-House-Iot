package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.Util.day
import `in`.iot.lab.ghouse.models.Request
import `in`.iot.lab.ghouse.models.RvItem
import `in`.iot.lab.ghouse.ui.main.adapters.BookingItemPagingAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_bookings.*
import java.util.*

class BookingsFragment : Fragment() {
    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    private val bookingItemAdapter by lazy {
        BookingItemPagingAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_bookings, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookingsList.layoutManager = GridLayoutManager(context, 1)
        bookingsList.adapter = bookingItemAdapter
        bookingItemAdapter.onClickListener = ::previewItem
        newBookingButton.setOnClickListener {
            findNavController().navigate(R.id.action_bookingsFragment_to_newBooking)
        }
        mainViewModel.requestLiveData.observe(viewLifecycleOwner){
            if (it is Request.ReloadViewModel){
                reload()
            }
        }
    }

    private fun reload() {
        mainViewModel.loadBooking(this, Date().time - 5 * day).observe(viewLifecycleOwner) {
            bookingItemAdapter.submitList(it)
        }
    }

    private fun previewItem(rvItem: RvItem) {
        if (rvItem is RvItem.BookingItem) {
            mainViewModel.selectedBooking.postValue(rvItem.booking)
            findNavController().navigate(R.id.action_bookingsFragment_to_bookingDetailsFragment)
        }
    }


    override fun onResume() {
        super.onResume()
        println("in.iot.lab.ghouse.ui.main>BookingsFragment>onResume   ")


    }


}