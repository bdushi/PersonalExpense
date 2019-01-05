package al.bruno.personal.expense

import al.bruno.personal.expense.adapter.CustomSpinnerAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.callback.OnEditListener
import al.bruno.personal.expense.databinding.ActionBarExpenseNavigationLayoutBinding
import al.bruno.personal.expense.databinding.ExpenseSpinnerSingleItemBinding
import al.bruno.personal.expense.databinding.SimpleSpinnerDropdownItemBinding
import al.bruno.personal.expense.model.ExpenseType
import al.bruno.personal.expense.util.Utilities
import android.os.Bundle
import android.view.LayoutInflater

import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import java.util.*

class HostActivity : AppCompatActivity() {
    private var itemRoot: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.host, HomeFragment())
                .addToBackStack("HOME_FRAGMENT")
                .commit()
        /*if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayShowCustomEnabled(true);
            supportActionBar!!.customView = LayoutInflater.from(this).inflate(R.layout.action_bar_month_navigation_layout, null, false)
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
        }*/
        //Lambda Expression
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportActionBar != null) {
                if(supportFragmentManager.findFragmentById(R.id.host) is HomeFragment)
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                else
                    supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
                //
                if(supportFragmentManager.findFragmentById(R.id.host) is HomeFragment || supportFragmentManager.findFragmentById(R.id.host) is StatisticsFragment) {
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    supportActionBar!!.setDisplayShowCustomEnabled(true)
                    supportActionBar!!.customView = LayoutInflater.from(this).inflate(R.layout.action_bar_month_navigation_layout, null, false)
                    supportActionBar!!.customView.setOnClickListener {
                        if (supportFragmentManager.findFragmentById(R.id.host) is MonthNavigationFragment)
                            supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                                    .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthNavigationFragment)
                                    .commit()
                        else
                            supportFragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                                    .add(R.id.host,
                                            MonthNavigationFragment()
                                            .setOnEditListener(onEditListener = object : OnEditListener<Calendar> {
                                                override fun onEdit(t: Calendar) {
                                                    Toast.makeText(this@HostActivity, Utilities.monthFormat(t.timeInMillis), Toast.LENGTH_SHORT).show()
                                                    supportFragmentManager
                                                            .beginTransaction()
                                                            .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                                                            .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthNavigationFragment)
                                                            .commit()
                                        }
                                    }))
                                    .commit()
                    }
                    //Array(2) {ExpenseType("Expenses", true); ExpenseType("Incomes", true)}
                } else if(supportFragmentManager.findFragmentById(R.id.host) is PersonalExpensesFragment) {
                    val actionBarExpenseNavigationLayoutBinding: ActionBarExpenseNavigationLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.action_bar_expense_navigation_layout, null, false)
                    actionBarExpenseNavigationLayoutBinding.adapter =
                            CustomSpinnerAdapter(
                                    this,
                                    R.layout.expense_spinner_single_item,
                                    R.layout.simple_spinner_dropdown_item,
                                    arrayOf(ExpenseType("Expenses", true), ExpenseType("Incomes", false)),
                                    object : BindingData<ExpenseType, ExpenseSpinnerSingleItemBinding> {
                                        override fun bindData(t: ExpenseType, vm: ExpenseSpinnerSingleItemBinding) {
                                            vm.type = t
                                        }
                                    },
                                    object : BindingData<ExpenseType, SimpleSpinnerDropdownItemBinding> {
                                        override fun bindData(t: ExpenseType, vm: SimpleSpinnerDropdownItemBinding) {
                                            vm.type = t
                                        }
                                    })
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    supportActionBar!!.setDisplayShowCustomEnabled(true)
                    supportActionBar!!.customView = actionBarExpenseNavigationLayoutBinding.root
                } else {
                    supportActionBar!!.setDisplayShowTitleEnabled(true)
                    supportActionBar!!.setDisplayShowCustomEnabled(false)
                    supportActionBar!!.setTitle(R.string.app_name)
                }
            }
            itemRoot?.isVisible = supportFragmentManager.findFragmentById(R.id.host) is HomeFragment
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

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.host) is MonthNavigationFragment)
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                    .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthNavigationFragment)
                    .commit()
        else if (supportFragmentManager.findFragmentById(R.id.host) is HomeFragment) {
            AlertDialog
                    .Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.app_name)
                    .setIcon(R.drawable.alert_outline)
                    .setPositiveButton(R.string.yes) { dialog, _ ->
                        System.exit(0)
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.no) { dialog, _ ->  dialog.dismiss()}
                    .show()
        } else {
            super.onBackPressed()
        }
    }
}
