package al.bruno.personal.expense

import al.bruno.personal.expense.observer.Observer
import al.bruno.personal.expense.observer.Subject
import al.bruno.personal.expense.adapter.CustomSpinnerAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.callback.OnEditListener
import al.bruno.personal.expense.callback.OnItemSelectedListener
import al.bruno.personal.expense.databinding.ActionBarExpenseNavigationLayoutBinding
import al.bruno.personal.expense.databinding.ActionBarMonthNavigationLayoutBinding
import al.bruno.personal.expense.databinding.ExpenseSpinnerSingleItemBinding
import al.bruno.personal.expense.databinding.SimpleSpinnerDropdownItemBinding
import al.bruno.personal.expense.entities.ExpenseType
import al.bruno.personal.expense.entities.Month
import al.bruno.personal.expense.model.Categories
import al.bruno.personal.expense.util.EXPENSES
import al.bruno.personal.expense.util.EXPENSES_KEY
import al.bruno.personal.expense.util.INCOMES
import al.bruno.personal.expense.util.INCOMES_KEY
import al.bruno.personal.expense.util.Utilities.monthFormat
import al.bruno.personal.expense.view.model.CategoriesViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater

import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.Calendar

class HostActivity : AppCompatActivity() {
    private var itemRoot: MenuItem? = null
    private val registry = ArrayList<Observer<List<Categories>>>()
    private val monthRegistry = ArrayList<Observer<Month>>()
    private val disposable: CompositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        val homeFragment = HomeFragment()
        monthSubject.registerObserver(homeFragment)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.host, homeFragment)
                .addToBackStack("HOME_FRAGMENT")
                .commit()
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
                    val actionBarMonthNavigationLayoutBinding: ActionBarMonthNavigationLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.action_bar_month_navigation_layout, null, false)
                    actionBarMonthNavigationLayoutBinding.date = monthFormat(Calendar.getInstance().timeInMillis)
                    supportActionBar!!.customView = actionBarMonthNavigationLayoutBinding.root
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
                                                    .setOnEditListener(onEditListener = object : OnEditListener<Month> {
                                                        override fun onEdit(t: Month) {
                                                            monthSubject.notifyObserver(t)
                                                            actionBarMonthNavigationLayoutBinding.date = t.monthFormat()
                                                            supportFragmentManager
                                                                    .beginTransaction()
                                                                    .setCustomAnimations(R.anim.slide_down, R.anim.slide_up)
                                                                    .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthNavigationFragment)
                                                                    .commit()
                                        }
                                    }))
                                    .commit()
                    }
                } else if(supportFragmentManager.findFragmentById(R.id.host) is PersonalExpensesFragment) {
                    expenseSubject.registerObserver((supportFragmentManager.findFragmentById(R.id.host) as PersonalExpensesFragment))
                    val actionBarExpenseNavigationLayoutBinding: ActionBarExpenseNavigationLayoutBinding =
                            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.action_bar_expense_navigation_layout, null, false)
                    val customSpinnerAdapter = CustomSpinnerAdapter(
                            this,
                            R.layout.expense_spinner_single_item,
                            R.layout.simple_spinner_dropdown_item,
                            arrayOf(ExpenseType(getString(R.string.expenses), false), ExpenseType(getString(R.string.incomes), false)),
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
                    actionBarExpenseNavigationLayoutBinding.itemSelectedListener = object : OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            customSpinnerAdapter.setSelection(p2)
                            when ((p0?.getItemAtPosition(p2) as ExpenseType).type) {
                                EXPENSES_KEY -> {
                                    disposable.add(ViewModelProviders
                                            .of(this@HostActivity)
                                            .get(CategoriesViewModel::class.java)
                                            .categories(EXPENSES)
                                            .subscribeOn(Schedulers.io())
                                            .subscribe({
                                                expenseSubject.notifyObserver(t = it)
                                            }, {
                                                Log.i(PersonalExpensesFragment::class.java.name, it.message)
                                                expenseSubject.notifyObserver(t = ArrayList())
                                            }))
                                }
                                INCOMES_KEY -> {
                                    disposable.add(ViewModelProviders
                                            .of(this@HostActivity)
                                            .get(CategoriesViewModel::class.java)
                                            .categories(INCOMES)
                                            .subscribeOn(Schedulers.io())
                                            .subscribe({
                                                expenseSubject.notifyObserver(t = it)
                                            }, {
                                                Log.i(PersonalExpensesFragment::class.java.name, it.message)
                                                expenseSubject.notifyObserver(t = ArrayList())
                                            }))
                                }
                            }
                        }
                    }
                    actionBarExpenseNavigationLayoutBinding.adapter = customSpinnerAdapter

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
            R.id.statistics -> {
                val statisticsFragment = StatisticsFragment()
                monthSubject.registerObserver(statisticsFragment)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.host, statisticsFragment)
                        .addToBackStack("STATISTICS_FRAGMENT")
                        .commit()
            }
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

    override fun onStop() {
        super.onStop()
        disposable.clear()
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
            supportFragmentManager.popBackStack()
            supportFragmentManager.executePendingTransactions()
        }
    }

    private val expenseSubject = object : Subject<List<Categories>> {
        override fun registerObserver(o: Observer<List<Categories>>) {
            registry.add(o)
        }

        override fun removeObserver(o: Observer<List<Categories>>) {
            if (registry.indexOf(o) >= 0)
                registry.remove(o)
        }

        override fun notifyObserver(t: List<Categories>) {
            for (observer in registry) {
                observer.update(t)
            }
        }
    }

    private val monthSubject = object : Subject<Month> {
        override fun registerObserver(o: Observer<Month>) {
            monthRegistry.add(o)
        }

        override fun removeObserver(o: Observer<Month>) {
            if (monthRegistry.indexOf(o) >= 0)
                monthRegistry.remove(o)
        }

        override fun notifyObserver(t: Month) {
            for (observer in monthRegistry) {
                observer.update(t)
            }
        }
    }
}
