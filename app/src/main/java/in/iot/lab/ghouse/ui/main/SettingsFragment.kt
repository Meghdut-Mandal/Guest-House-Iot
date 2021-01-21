package `in`.iot.lab.ghouse.ui.main

import `in`.iot.lab.ghouse.LoginActivity
import `in`.iot.lab.ghouse.R
import `in`.iot.lab.ghouse.db.LocalDb
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.chibatching.kotpref.bulk
import kotlinx.android.synthetic.main.edit_profile_layout.view.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authorName.text = LocalDb.name
        authorEmail.text = LocalDb.email
        logOutButton.setOnClickListener {
            LocalDb.bulk {
                name = ""
                email = ""
                isLoggedIn = false
                navigateToLogin()
            }
        }
        edit_profile.setOnClickListener {
            openEditDialog()
        }
        edit_image.setOnClickListener {
            openEditDialog()
        }
        loadData()

    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun openEditDialog() {
        MaterialDialog(requireActivity(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            customView(R.layout.edit_profile_layout, scrollable = true, horizontalPadding = true)
            title(text = "Edit Profile")
            getCustomView().apply {
                phone_number_edittext.setText(LocalDb.phoneNumber)
                email_edit_text.setText(LocalDb.email)
                address_edit_text.setText(LocalDb.address)
                name_eddit_text.setText(LocalDb.name)
            }

            positiveButton(text = "Save") {
                it.getCustomView().apply {
                    LocalDb.bulk {
                        phoneNumber = phone_number_edittext.text.toString()
                        email = email_edit_text.text.toString()
                        address = address_edit_text.text.toString()
                        name = name_eddit_text.text.toString()
                        loadData()
                    }
                }
            }
            negativeButton(text = "Cancel") { }
        }
    }

    fun loadData() {
        name_letter.text = LocalDb.name.toUpperCase()[0] + ""
        authorName.text = LocalDb.name
        authorAddress.text = LocalDb.address
        authorNumber.text = LocalDb.phoneNumber
        authorEmail.text = LocalDb.email
    }

}