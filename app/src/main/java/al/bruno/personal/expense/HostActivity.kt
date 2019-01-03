package al.bruno.personal.expense

import android.os.Bundle
import android.view.LayoutInflater

import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class HostActivity : AppCompatActivity() {
    private var itemRoot: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.host, HomeFragment())
                .commit()
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayShowCustomEnabled(true);
            supportActionBar!!.customView = LayoutInflater.from(this).inflate(R.layout.action_bar_custom_layout, null, false)
            supportActionBar!!.customView.setOnClickListener {
                Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
            }
        }
        //Lambda Expression
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportActionBar != null)
                supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
            itemRoot?.isVisible = supportFragmentManager.findFragmentById(R.id.host) !is PersonalExpensesFragment
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
        itemRoot?.isVisible = supportFragmentManager.findFragmentById(R.id.host) !is PersonalExpensesFragment
        return super.onPrepareOptionsMenu(menu)
    }
}
