package `in`.iot.lab.ghouse.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.iot.lab.ghouse.R
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashBoardFragment : Fragment() {

    companion object {
        fun newInstance() = DashBoardFragment()
    }

    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            MainViewModel::class.java
        )
    }
    private val adapter by lazy {
        BookingItemAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        view.activeBookings.layoutManager = LinearLayoutManager(context)
        view.activeBookings.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.loadActiveRooms()
        mainViewModel.loadPayments()
        mainViewModel.activeRoomsLiveData.observe(viewLifecycleOwner) {
            if (it!=null) {
                adapter.submitList(it)
            }
        }


    }

}