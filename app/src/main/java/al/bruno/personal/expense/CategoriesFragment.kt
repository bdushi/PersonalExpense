package al.bruno.personal.expense

import al.bruno.personal.expense.adapter.EditAdapter
import al.bruno.personal.expense.callback.*
import android.os.Bundle

import al.bruno.personal.expense.databinding.CategoriesSingleItemBinding
import al.bruno.personal.expense.databinding.FragmentCategoriesBinding
import al.bruno.personal.expense.dialog.EditCategoriesDialog
import androidx.fragment.app.Fragment

import al.bruno.personal.expense.model.Categories
import al.bruno.personal.expense.adapter.observer.Subject
import al.bruno.personal.expense.databinding.AddNewItemBinding
import al.bruno.personal.expense.databinding.IncomesSingleItemBinding
import al.bruno.personal.expense.model.Expense
import al.bruno.personal.expense.util.Utilities
import al.bruno.personal.expense.view.model.CategoriesViewModel
import al.bruno.personal.expense.view.model.ExpenseViewModel
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class CategoriesFragment : Fragment(), OnItemSwipeSelectListener<Categories>, Subject<Categories> {
    //https://medium.com/fueled-engineering/swipe-drag-bind-recyclerview-817408125530
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val registry = ArrayList<al.bruno.personal.expense.adapter.observer.Observer<Categories>>()
    private var calendar = Calendar.getInstance()
    private var fragmentCategoriesBinding: FragmentCategoriesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCategoriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        disposable.add(ViewModelProviders
                .of(this)
                .get(CategoriesViewModel::class.java)
                .categories()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.i(CategoriesFragment::class.java.name, it.toString())
                    val adapter = EditAdapter(
                            it,
                            R.layout.categories_single_item,
                            object : BindingData<Categories, CategoriesSingleItemBinding> {
                                override fun bindData(t: Categories, vm: CategoriesSingleItemBinding) {
                                    val expense = Expense();
                                    expense.category = t.category
                                    vm.categories = t
                                    vm.onItemClickListener = object : OnItemClickListener<Categories> {
                                        override fun onItemClick(t: Categories) {
                                            ExpenseBottomSheet
                                                    .Companion
                                                    .Builder()
                                                    .setExpense(expense)
                                                    .build()
                                                    .show(fragmentManager, "EXPENSE_BOTTON_SHEET")
                                        }

                                        override fun onLongItemClick(t: Categories): Boolean {
                                            return false
                                        }
                                    }
                                }
                            }, R.layout.add_new_item, object : BindingData<List<Categories>, AddNewItemBinding> {
                        override fun bindData(t: List<Categories>, vm: AddNewItemBinding) {
                            vm.onClick = object : OnClick {
                                override fun onClick() {
                                    EditCategoriesDialog
                                            .Builder()
                                            .setHint(R.string.expenses)
                                            .setTitle(R.string.expense)
                                            .setCategoriesList(t)
                                            .build()
                                            .onCategoriesEditListener(onEditListeners = object : OnEditListeners<Categories> {
                                                override fun onEdit(t: Categories) {
                                                    disposable.add(ViewModelProviders
                                                            .of(this@CategoriesFragment)
                                                            .get(CategoriesViewModel::class.java)
                                                            .insert(t)
                                                            .subscribeOn(Schedulers.io())
                                                            .doOnSubscribe {
                                                                notifyObserverAdd(t)
                                                            }.subscribe())
                                                }

                                                override fun onDismiss(t: Categories) {
                                                    notifyObserverChanged(t)
                                                }
                                            })
                                            .show(fragmentManager, CategoriesFragment::class.java.name)
                                }
                            }
                        }
                    })
                    registerObserver(adapter)
                    fragmentCategoriesBinding?.customAdapter = adapter
                }, {
                    Log.i(CategoriesFragment::class.java.name, it.message)
                }))
        fragmentCategoriesBinding?.onItemSwipeSelectListener = this
        return fragmentCategoriesBinding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fab, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.expenses -> {
                disposable.add(ViewModelProviders
                        .of(this)
                        .get(CategoriesViewModel::class.java)
                        .categories()
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            val adapter = EditAdapter(
                                    it,
                                    R.layout.categories_single_item,
                                    object : BindingData<Categories, CategoriesSingleItemBinding> {
                                        override fun bindData(t: Categories, vm: CategoriesSingleItemBinding) {
                                            val expense = Expense();
                                            expense.category = t.category
                                            vm.categories = t
                                            vm.onItemClickListener = object : OnItemClickListener<Categories> {
                                                override fun onItemClick(t: Categories) {
                                                    ExpenseBottomSheet
                                                            .Companion
                                                            .Builder()
                                                            .setExpense(expense)
                                                            .build()
                                                            .show(fragmentManager, "EXPENSE_BOTTON_SHEET")
                                                }

                                                override fun onLongItemClick(t: Categories): Boolean {
                                                    return false
                                                }
                                            }
                                        }
                                    },
                                    R.layout.add_new_item,
                                    object : BindingData<List<Categories>, AddNewItemBinding> {
                                        override fun bindData(t: List<Categories>, vm: AddNewItemBinding) {
                                            vm.onClick = object : OnClick {
                                                override fun onClick() {
                                                    EditCategoriesDialog
                                                            .Builder()
                                                            .setHint(R.string.expenses)
                                                            .setTitle(R.string.expense)
                                                            .setCategoriesList(t)
                                                            .build()
                                                            .onCategoriesEditListener(onEditListeners = object : OnEditListeners<Categories> {
                                                                override fun onEdit(t: Categories) {
                                                                    disposable.add(ViewModelProviders
                                                                            .of(this@CategoriesFragment)
                                                                            .get(CategoriesViewModel::class.java)
                                                                            .insert(t)
                                                                            .subscribeOn(Schedulers.io())
                                                                            .doOnSubscribe {
                                                                                notifyObserverAdd(t)
                                                                            }.subscribe())
                                                                }

                                                                override fun onDismiss(t: Categories) {
                                                                    notifyObserverChanged(t)
                                                                }
                                                            })
                                                            .show(fragmentManager, CategoriesFragment::class.java.name)
                                                }
                                            }
                                        }
                                    })
                            registerObserver(adapter)
                            fragmentCategoriesBinding?.customAdapter = adapter
                        }, {
                            Log.i(CategoriesFragment::class.java.name, it.message)
                        }))
                return false
            }
            R.id.incomes -> {
                disposable.add(ViewModelProviders
                        .of(this)
                        .get(ExpenseViewModel::class.java)
                        .incomes(Utilities.month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            val adapter = EditAdapter(
                                    it,
                                    R.layout.incomes_single_item,
                                    object : BindingData<Expense, IncomesSingleItemBinding> {
                                        override fun bindData(t: Expense, vm: IncomesSingleItemBinding) {
                                            vm.expense = t
                                            vm.onItemClickListener = object : OnItemClickListener<Expense> {
                                                override fun onItemClick(t: Expense) {
                                                    Log.i(CategoriesFragment::class.java.name, t.toString())
                                                }
                                                override fun onLongItemClick(t: Expense): Boolean {
                                                    return false
                                                }

                                            }
                                        }

                                    },
                                    R.layout.add_new_item,
                                    object : BindingData<List<Expense>, AddNewItemBinding> {
                                        override fun bindData(t: List<Expense>, vm: AddNewItemBinding) {
                                            vm.onClick = object : OnClick {
                                                override fun onClick() {

                                                }
                                            }
                                        }

                                    })
                            fragmentCategoriesBinding?.customAdapter = adapter
                        }, {
                            Log.i(CategoriesFragment::class.java.name, it.toString())
                        }))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onItemSwipedLeft(t: Categories) {
        val handler = Handler()
        val runnable = Runnable {
            ViewModelProviders
                    .of(this)
                    .get(CategoriesViewModel::class.java)
                    .delete(t)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
                    .dispose()
        }
        Snackbar.make(activity!!.findViewById(android.R.id.content), "DELETED", Snackbar.LENGTH_LONG).setAction("UNDO") {
            handler.removeCallbacks(runnable)
            notifyObserverAdd(t)
        }.show();
        notifyObserverRemove(t)
        handler.postDelayed(runnable, 3500)
    }

    override fun onItemSwipedRight(t: Categories) {
        EditCategoriesDialog
                .Builder()
                .setHint(R.string.expenses)
                .setTitle(R.string.expense)
                .setCategories(t)
                .build()
                .onCategoriesEditListener(onEditListeners = object : OnEditListeners<Categories> {
                    override fun onEdit(t: Categories) {
                        disposable.add(ViewModelProviders
                                .of(this@CategoriesFragment)
                                .get(CategoriesViewModel::class.java)
                                .insert(t)
                                .subscribeOn(Schedulers.io())
                                .doOnSubscribe {
                                    notifyObserverAdd(t)
                                }.subscribe())
                    }

                    override fun onDismiss(t: Categories) {
                        notifyObserverChanged(t)
                    }
                })
                .show(fragmentManager, CategoriesFragment::class.java.name)
    }

    override fun registerObserver(o: al.bruno.personal.expense.adapter.observer.Observer<Categories>) {
        registry.add(o)
    }

    override fun removeObserver(o: al.bruno.personal.expense.adapter.observer.Observer<Categories>) {
        if (registry.indexOf(o) >= 0)
            registry.remove(o);
    }

    override fun notifyObserverRemove(t: Categories) {
        for (observer in registry) {
            observer.remove(t)
        }
    }

    override fun notifyObserverAdd(t: Categories) {
        for (observer in registry) {
            observer.add(t)
        }
    }

    override fun notifyObserverChanged(t: Categories) {
        for (observer in registry) {
            observer.update(t)
        }
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
