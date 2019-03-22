package al.bruno.personal.expense.ui

import al.bruno.personal.expense.*
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
import al.bruno.personal.expense.observer.ExpenseObserver
import al.bruno.personal.expense.observer.ExpenseSubject
import al.bruno.personal.expense.ui.expense.PersonalExpensesFragment
import al.bruno.personal.expense.ui.home.HomeFragment
import al.bruno.personal.expense.ui.map.GoogleMapFragment
import al.bruno.personal.expense.ui.settings.SettingsFragment
import al.bruno.personal.expense.ui.statistic.StatisticsFragment
import al.bruno.personal.expense.util.EXPENSES
import al.bruno.personal.expense.util.INCOMES
import al.bruno.personal.expense.util.Utilities.monthFormat
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.Calendar
import javax.inject.Inject

class HostActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private var itemRoot: MenuItem? = null
    private val registry = ArrayList<ExpenseObserver<List<Categories>, String>>()
    private val monthRegistry = ArrayList<Observer<Month>>()
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        AndroidInjection.inject(this)
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
                if(supportFragmentManager.findFragmentById(R.id.host) is HomeFragment) {
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
                } else if(supportFragmentManager.findFragmentById(R.id.host) is StatisticsFragment) {
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
                }
                else if(supportFragmentManager.findFragmentById(R.id.host) is PersonalExpensesFragment) {
                    expenseSubject.registerObserver((supportFragmentManager.findFragmentById(R.id.host) as PersonalExpensesFragment))
                    val actionBarExpenseNavigationLayoutBinding: ActionBarExpenseNavigationLayoutBinding =
                            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.action_bar_expense_navigation_layout, null, false)
                    val customSpinnerAdapter = CustomSpinnerAdapter(
                            this,
                            R.layout.expense_spinner_single_item,
                            R.layout.simple_spinner_dropdown_item,
                            arrayOf(ExpenseType(EXPENSES, getString(R.string.expenses), false), ExpenseType(INCOMES, getString(R.string.incomes), false)),
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
                            val expenseType = (p0?.getItemAtPosition(p2) as ExpenseType)
                            disposable
                                    .add(ViewModelProviders
                                            .of(this@HostActivity, mViewModelFactory)
                                            [HostViewModel::class.java]
                                            .categories(expenseType.key)
                                            .subscribeOn(Schedulers.io())
                                            .subscribe({
                                                expenseSubject.notifyObserver(t = it, l = expenseType.key)
                                            }, {
                                                Log.i(PersonalExpensesFragment::class.java.name, it.message)
                                                expenseSubject.notifyObserver(t = ArrayList(), l = expenseType.key)
                                            })
                                    )
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

    private val expenseSubject = object : ExpenseSubject<List<Categories>, String> {
        override fun registerObserver(o: ExpenseObserver<List<Categories>, String>) {
            registry.add(o)
        }

        override fun removeObserver(o: ExpenseObserver<List<Categories>, String>) {
            if (registry.indexOf(o) >= 0)
                registry.remove(o)
        }

        override fun notifyObserver(t: List<Categories>, l: String) {
            for (observer in registry) {
                observer.update(t, l)
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
