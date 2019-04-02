package al.bruno.personal.expense.ui.sign.`in`

import al.bruno.personal.expense.R
import al.bruno.personal.expense.callback.OnClickListener
import al.bruno.personal.expense.databinding.ActivitySignInBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {
    private val TAG = SignInActivity::class.java.name
    //Google Login Request Code
    private val RC_SIGN_IN = 9001
    //Firebase Auth
    private lateinit var auth: FirebaseAuth
    private lateinit var activitySignInBinding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        //Google Sign In Client
        val googleSignInClient = GoogleSignIn.getClient(this@SignInActivity, googleSignInOptions)

        activitySignInBinding.onClick = object : OnClickListener<View> {
            override fun onClick(t: View) {
                when (t.id) {
                    R.id.sign_in -> startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
                    R.id.sign_in_cancel -> finish()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val signInAccount = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = signInAccount.getResult(ApiException::class.java)
                auth.signInWithCredential(GoogleAuthProvider.getCredential(account!!.idToken, null))
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success")
                                //val user = auth.currentUser
                                activitySignInBinding.user = auth.currentUser
                            } else {
                                // If sign in fails, display a message to the user.
                                activitySignInBinding.user = null
                                Log.w(TAG, "signInWithCredential:failure", task.exception)
                                Snackbar.make(findViewById(android.R.id.content), "Authentication Failed." + task.exception, Snackbar.LENGTH_SHORT).show()
                            }
                        }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                Snackbar.make(findViewById(android.R.id.content), "Authentication Failed" + e.message, Snackbar.LENGTH_SHORT).show()
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Authentication Failed", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        activitySignInBinding.user = auth.currentUser
    }
    override fun onBackPressed() {
        finish()
    }
}
