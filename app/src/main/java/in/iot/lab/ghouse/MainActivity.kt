package `in`.iot.lab.ghouse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.fragment_booking_details.*
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)



    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView
        val navHostFragment = navHostFragment as NavHostFragment
        NavigationUI.setupWithNavController(
                bottomNavigationView,
                navHostFragment.navController
        )
    }

    fun callCustomer(view: View) {
        val customerNumber= phoneNumber.text.toString()
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$customerNumber")
        startActivity(intent)
    }

}
