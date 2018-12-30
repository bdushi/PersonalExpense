package al.bruno.personal.expense

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

class HostActivity : AppCompatActivity() {
    private var itemRoot: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.host, HomeFragment())
                .commit()
        //Lambda Expression
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportActionBar != null)
                supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
            itemRoot?.isVisible = supportFragmentManager.findFragmentById(R.id.home) is PersonalExpensesFragment
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
                    .addToBackStack("GOOGLE_MAP_FRAGMENT")
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        itemRoot = menu!!.findItem(R.id.root)
        itemRoot?.isVisible = supportFragmentManager.findFragmentById(R.id.home) is PersonalExpensesFragment
        return super.onPrepareOptionsMenu(menu)
    }
}
