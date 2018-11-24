package al.bruno.personal.expense

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

class HostActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        //google analitics
        //FirebaseAnalytics.getInstance(this)
        //inflater home fragment
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.host, HomeFragment())
                .commit()
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)

        //Lambda Expression
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportActionBar != null)
                supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
        }
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                if (supportFragmentManager.findFragmentById(R.id.host) is GoogleMapFragment) {
                    navigation.visibility = View.GONE
                } else if (supportFragmentManager.findFragmentById(R.id.host) is StatisticsFragment) {
                    navigation.visibility = View.GONE
                } else if (supportFragmentManager.findFragmentById(R.id.host) is SettingsFragment) {
                    navigation.visibility = View.GONE
                } else if (supportFragmentManager.findFragmentById(R.id.host) is CategoriesFragment) {
                    navigation.visibility = View.GONE
                }
            } else {
                navigation.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.map -> supportFragmentManager.beginTransaction()
                    .replace(R.id.host, GoogleMapFragment())
                    .addToBackStack("GOOGLE_MAP__FRAGMENT")
                    .commit()
            R.id.statistics -> supportFragmentManager.beginTransaction()
                    .replace(R.id.host, StatisticsFragment())
                    .addToBackStack("STATISTICS_FRAGMENT")
                    .commit()
            R.id.settings -> supportFragmentManager.beginTransaction()
                    .replace(R.id.host, SettingsFragment())
                    .addToBackStack("SETTINGS_FRAGMENT")
                    .commit()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.host, HomeFragment())
                        .commit()
                return true
            }
            R.id.budget -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.host, BudgetFragment())
                        .commit()
                return true
            }
            R.id.expense -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.host, ExpenseFragment())
                        .commit()
                return true
            }
            R.id.expense_log -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.host, DetailsFragment())
                        .commit()
                return true
            }
            else -> return false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
