package al.bruno.personal.expense

import al.bruno.personal.expense.callback.OnEditListener
import al.bruno.personal.expense.util.Month
import al.bruno.personal.expense.util.Utilities
import android.os.Bundle
import android.view.LayoutInflater

import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import java.util.*

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
                if(supportFragmentManager.findFragmentById(R.id.host) is MonthNavigationFragment)
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                            .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthNavigationFragment)
                            .commit()
                else
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                            .add(R.id.host, MonthNavigationFragment().setOnEditListener(onEditListener = object : OnEditListener<Calendar> {
                                override fun onEdit(t: Calendar) {
                                    Toast.makeText(this@HostActivity, Utilities.month(calendar = t), Toast.LENGTH_SHORT).show()
                                    supportFragmentManager.beginTransaction()
                                            .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                                            .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthNavigationFragment)
                                            .commit()
                                }
                            }))
                            .commit()
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
