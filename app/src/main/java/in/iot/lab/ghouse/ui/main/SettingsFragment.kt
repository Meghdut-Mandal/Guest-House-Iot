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
import com.chibatching.kotpref.bulk
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

    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onResume() {
        super.onResume()

    }

}