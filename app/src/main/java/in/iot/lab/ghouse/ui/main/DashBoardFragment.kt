package `in`.iot.lab.ghouse.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.db.Resource
import `in`.iot.lab.ghouse.models.Room
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import kotlinx.android.synthetic.main.new_room.view.*

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
        add_room_cardview.setOnClickListener {
            showAddRoom()
        }


    }

    private fun showAddRoom() {
        MaterialDialog(requireActivity(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            customView(R.layout.new_room, scrollable = true, horizontalPadding = true)
            title(text = "Add New Room")
            positiveButton(text = "Save") {
                val view = it.getCustomView()
                val desc = view.room_descripton.text.toString()
                val name = view.room_name.text.toString()
                val room = Room(System.currentTimeMillis().toString(), name, desc)
                mainViewModel.saveRoom(room).observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            Snackbar.make(view, "Room Saved !", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                        is Resource.Faliure -> {
                            Snackbar.make(
                                view,
                                "Unable to Save room!",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
            negativeButton(text = "Cancel") { }
        }
    }

}
/*


 */