package `in`.iot.lab.ghouse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 12
    private val IS_LOGGED_IN = "is_logedin"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isLoggedIn = sharedPreferences.getBoolean(IS_LOGGED_IN, false)
        if (isLoggedIn){
           navigateToMainActivity(this)
        }
        setContentView(R.layout.activity_login)
    }


    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
    private val mGoogleSignInClient by lazy { GoogleSignIn.getClient(this, gso) }

    override fun onResume() {
        super.onResume()
        sign_in_button.setOnClickListener {
            sigin()
        }
    }

    private fun sigin() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            println("in.iot.lab.ghouse.ui.main>>LoginFragment>handleSignInResult  signInResult:failed code=${e.statusCode}")
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        val activity = this
        sharedPreferences.edit {
            putBoolean(IS_LOGGED_IN, true)
            navigateToMainActivity(activity)
        }
    }

    private fun navigateToMainActivity(activity: LoginActivity) {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val sharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            this::class.qualifiedName,
            0
        )
    }


}