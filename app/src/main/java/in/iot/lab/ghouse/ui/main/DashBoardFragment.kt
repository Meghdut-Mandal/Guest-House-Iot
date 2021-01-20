package `in`.iot.lab.ghouse.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.db.Resource
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashBoardFragment : Fragment() {


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
        view.activeBookings.layoutManager = GridLayoutManager(context, 1)
        view.activeBookings.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        dashboard_progress.isIndeterminate = true
        dashboard_progress.isVisible = false
        mainViewModel.loadActiveRooms().observe(viewLifecycleOwner) {
            when (it) {
                Resource.Loading -> {
                    dashboard_progress.isVisible = true
                }
                is Resource.Success -> {
                    dashboard_progress.isVisible = false
                    adapter.submitList(it.value)
                }
                is Resource.Faliure -> {
                    dashboard_progress.isVisible = false
                    Snackbar.make(dashboard_progress, "Error In loading !!", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
        mainViewModel.loadPayments()


    }

}