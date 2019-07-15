package al.bruno.personal.expense.ui

import al.bruno.adapter.BindingData
import al.bruno.month.view.Month
import al.bruno.month.view.MonthView
import al.bruno.personal.expense.R
import al.bruno.personal.expense.callback.OnItemSelectedListener
import al.bruno.personal.expense.data.source.local.ExpenseSharedPreferences
import al.bruno.personal.expense.databinding.ActionBarExpenseNavigationLayoutBinding
import al.bruno.personal.expense.databinding.ActionBarMonthNavigationLayoutBinding
import al.bruno.personal.expense.databinding.ExpenseSpinnerSingleItemBinding
import al.bruno.personal.expense.databinding.SimpleSpinnerDropdownItemBinding
import al.bruno.personal.expense.entities.ExpenseType
import al.bruno.personal.expense.model.Categories
import al.bruno.personal.expense.observer.ExpenseObserver
import al.bruno.personal.expense.observer.ExpenseSubject
import al.bruno.personal.expense.ui.expense.ExpenseFragment
import al.bruno.personal.expense.ui.home.HomeFragment
import al.bruno.personal.expense.ui.map.GoogleMapFragment
import al.bruno.personal.expense.ui.profile.ProfileActivity
import al.bruno.personal.expense.ui.settings.SettingsFragment
import al.bruno.personal.expense.ui.sign.`in`.SignInActivity
import al.bruno.personal.expense.ui.statistic.StatisticsFragment
import al.bruno.personal.expense.util.EXPENSES
import al.bruno.personal.expense.util.INCOMES
import al.bruno.personal.expense.util.Utilities.monthFormat
import al.bruno.personal.expense.work.manager.PullExpenseWorkManager
import al.bruno.personal.expense.work.manager.PushExpenseWorkManager
import android.app.Activity
import android.content.Intent
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
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.Calendar.getInstance
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.system.exitProcess

class HostActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var sharedPreferences: ExpenseSharedPreferences

    @Inject
    lateinit var auth: FirebaseAuth


    @Inject
    lateinit var myRxBus: MyRxBus

    private var userInfo: FirebaseUser? = null

    private val RC_SIGN_IN_ACTIVITY = 7
    private val RC_PROFILE_ACTIVITY = 8

    private var itemRoot: MenuItem? = null
    private var signIn: MenuItem? = null
    private val registry = ArrayList<ExpenseObserver<List<Categories>, String>>()
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        AndroidInjection.inject(this)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.host, HomeFragment())
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
                    actionBarMonthNavigationLayoutBinding.date = monthFormat(getInstance().timeInMillis)
                    supportActionBar!!.customView = actionBarMonthNavigationLayoutBinding.root
                    supportActionBar!!.customView.setOnClickListener {
                        if (supportFragmentManager.findFragmentById(R.id.host) is MonthView)
                            supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                                    .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthView)
                                    .commit()
                        else
                            supportFragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                                    .add(R.id.host,
                                            MonthView()
                                                    .setOnEditListener(onEditListener = object : al.bruno.month.view.OnEditListener<Month> {
                                                        override fun onEdit(t: Month) {
                                                            myRxBus.setMonth(month = t)
                                                            actionBarMonthNavigationLayoutBinding.date = t.monthFormat()
                                                            supportFragmentManager
                                                                    .beginTransaction()
                                                                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                                                                    .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthView)
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
                        if (supportFragmentManager.findFragmentById(R.id.host) is MonthView)
                            supportFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                                    .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthView)
                                    .commit()
                        else
                            supportFragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                                    .add(R.id.host,
                                            MonthView()
                                                    .setOnEditListener(onEditListener = object : al.bruno.month.view.OnEditListener<Month> {
                                                        override fun onEdit(t: Month) {
                                                            myRxBus.setMonth(month = t)
                                                            actionBarMonthNavigationLayoutBinding.date = t.monthFormat()
                                                            supportFragmentManager
                                                                    .beginTransaction()
                                                                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                                                                    .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthView)
                                                                    .commit()
                                                        }
                                                    }))
                                    .commit()
                    }
                }
                else if(supportFragmentManager.findFragmentById(R.id.host) is ExpenseFragment) {
                    expenseSubject.registerObserver((supportFragmentManager.findFragmentById(R.id.host) as ExpenseFragment))
                    val actionBarExpenseNavigationLayoutBinding: ActionBarExpenseNavigationLayoutBinding =
                            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.action_bar_expense_navigation_layout, null, false)
                    val customSpinnerAdapter = al.bruno.adapter.CustomSpinnerAdapter(
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
                                                Log.i(ExpenseFragment::class.java.name, it.message)
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

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

     override fun onStart() {
         super.onStart()
         userInfo = auth.currentUser
         if(userInfo != null) {
             WorkManager
                     .getInstance(this)
                     .enqueueUniquePeriodicWork(
                             UUID.randomUUID().toString(),
                             ExistingPeriodicWorkPolicy.KEEP,
                             PeriodicWorkRequestBuilder<PushExpenseWorkManager>(15, TimeUnit.MINUTES)
                                     .addTag(UUID.randomUUID().toString())
                                     .build())
         } else {
             WorkManager.getInstance(this).cancelAllWork()
         }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.host) is MonthView)
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out)
                    .remove(supportFragmentManager.findFragmentById(R.id.host) as MonthView)
                    .commit()
        else if (supportFragmentManager.findFragmentById(R.id.host) is HomeFragment) {
            AlertDialog
                    .Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.app_name)
                    .setIcon(R.drawable.alert_outline)
                    .setPositiveButton(R.string.yes) { dialog, _ ->
                        dialog.dismiss()
                        exitProcess(0)
                    }
                    .setNegativeButton(R.string.no) { dialog, _ ->  dialog.dismiss()}
                    .show()
        } else {
            supportFragmentManager.popBackStack()
            supportFragmentManager.executePendingTransactions()
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
            R.id.map -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.host, GoogleMapFragment())
                        .addToBackStack("GOOGLE_MAP_FRAGMENT")
                        .commit()
                return true
            } R.id.statistics -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.host, StatisticsFragment())
                        .addToBackStack("STATISTICS_FRAGMENT")
                        .commit()
                return true
            }
            R.id.settings -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.host, SettingsFragment())
                        .addToBackStack("SETTINGS_FRAGMENT")
                        .commit()
                return true
            }

            R.id.sign_in -> {
                if (userInfo != null)
                    startActivityForResult(Intent(this, ProfileActivity::class.java), RC_PROFILE_ACTIVITY)
                else
                    startActivityForResult(Intent(this, SignInActivity::class.java), RC_SIGN_IN_ACTIVITY)
                return true
            } else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        itemRoot = menu!!.findItem(R.id.root)
        signIn = menu.findItem(R.id.sign_in)
        itemRoot!!.isVisible = supportFragmentManager.findFragmentById(R.id.host) !is ExpenseFragment
        if(userInfo != null) {
            signIn!!.title = userInfo!!.displayName
            /*Picasso
                    .get()
                    .load(userInfo!!.photoUrl)
                    .into(object : Target {
                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            signIn.icon = BitmapDrawable(resources, bitmap)
                        }
                    })*/
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == RC_SIGN_IN_ACTIVITY) {
                userInfo = auth.currentUser
                signIn!!.title = userInfo!!.displayName
                WorkManager
                        .getInstance(this)
                        .enqueue(OneTimeWorkRequestBuilder<PullExpenseWorkManager>()
                                .setInputData(Data.Builder().putString("UID", userInfo!!.uid).build())
                                .build())
            } else if(requestCode == RC_PROFILE_ACTIVITY) {
                signIn!!.setTitle(R.string.sign_in)
            }
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
}
