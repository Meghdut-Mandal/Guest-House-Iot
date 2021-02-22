package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.db.Resource
import `in`.iot.lab.ghouse.models.RvItem
import `in`.iot.lab.ghouse.ui.main.adapters.BookingItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private val adapter by lazy {
        BookingItemAdapter()
    }

    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(
            MainViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_items.layoutManager = GridLayoutManager(context, 1)
        search_items.adapter = adapter
        adapter.onClickListener=::previewItem
        search_toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun previewItem(rvItem: RvItem) {
        if (rvItem is RvItem.BookingItem) {
            mainViewModel.selectedBooking.postValue(rvItem.booking)
            findNavController().navigate(R.id.action_searchFragment_to_bookingDetailsFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        search_progress.isIndeterminate = true
        search_progress.isVisible = true
        mainViewModel.loadSearchBookings().observe(this) { res ->
            when (res) {
                is Resource.Success -> {
                    adapter.submitList(res.value)
                    search_progress.isVisible = false
                }
                is Resource.Failure -> {
                    Snackbar.make(search_items, "Error In loading Data", Snackbar.LENGTH_LONG)
                        .show()
                    search_progress.isVisible = false
                }
                is Resource.Loading -> {
                    search_progress.isVisible = true
                }
            }
        }
    }

}