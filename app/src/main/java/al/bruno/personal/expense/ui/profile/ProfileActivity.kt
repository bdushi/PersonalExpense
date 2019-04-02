package al.bruno.personal.expense.ui.profile

import al.bruno.personal.expense.R
import al.bruno.personal.expense.databinding.ActivityProfileBinding
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.android.AndroidInjection
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val activityProfileBinding = DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile)
        val userInfo: FirebaseUser = auth.currentUser!!
        activityProfileBinding.user = userInfo
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = userInfo.displayName
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.sign_out -> {
                auth.signOut()
                googleSignInClient
                        .signOut()
                        .addOnCompleteListener {
                            finish()
                        }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    override fun onBackPressed() {
        finish()
    }
}
