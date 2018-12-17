package al.bruno.personal.expense

import al.bruno.personal.expense.adapter.CustomAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.callback.OnClickListener
import al.bruno.personal.expense.callback.OnEditListeners
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.personal.expense.databinding.FragmentBudgetBinding
import al.bruno.personal.expense.databinding.LogSingleItemBinding
import al.bruno.personal.expense.dialog.EditBudgetDialog
import al.bruno.personal.expense.dialog.EditIncomesDialog
import al.bruno.personal.expense.model.Budget
import al.bruno.personal.expense.model.BudgetMaster
import al.bruno.personal.expense.model.Expense
import al.bruno.personal.expense.util.Utilities.month
import al.bruno.personal.expense.view.model.BudgetMasterViewModel
import al.bruno.personal.expense.view.model.BudgetViewModel
import al.bruno.personal.expense.view.model.ExpenseViewModel
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BudgetFragment : Fragment() {
    private val disposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBudgetBinding: FragmentBudgetBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget, container, false)
        ViewModelProviders.of(this)
                .get(BudgetMasterViewModel::class.java)
                .budget(month(month()))
                .observe(this, Observer{
                    run {
                        fragmentBudgetBinding.budget = it ?: BudgetMaster()
                    }
                }
        )
        fragmentBudgetBinding.onClickListenerEditBudget = object: OnClickListener<BudgetMaster> {
            override fun onClick(t: BudgetMaster) {
                EditBudgetDialog
                        .Builder()
                        .setBudget(budget = t)
                        .build()
                        .onDialogEditListeners(object : OnEditListeners<Budget> {
                            override fun onEdit(t: Budget) {
                                if(t.id == 0.toLong()) {
                                    disposable.add(ViewModelProviders
                                            .of(this@BudgetFragment)
                                            .get(BudgetViewModel::class.java)
                                            .insert(t)
                                            .subscribeOn(Schedulers.io())
                                            .subscribe({
                                                Log.i(BudgetFragment::class.java.name, it.toString())
                                            }, {
                                                Log.i(BudgetFragment::class.java.name, it.message)
                                            }))
                                } else {
                                    Thread(Runnable {
                                        ViewModelProviders
                                                .of(this@BudgetFragment)[BudgetViewModel::class.java]
                                                .updateBudget(t.budget, t.id)
                                    }).start()
                                }
                            }
                            override fun onDismiss(t: Budget) {
                            }
                        })
                        .show(fragmentManager, BudgetFragment::class.java.name)
            }
        }
        fragmentBudgetBinding.onClickListenerEditIncomes = object: OnClickListener<BudgetMaster> {
            override fun onClick(t: BudgetMaster) {
                EditIncomesDialog
                        .Builder()
                        .setBudget(budget = t)
                        .build()
                        .onDialogEditListeners(object : OnEditListeners<Budget>{
                            override fun onEdit(t: Budget) {
                                if(t.id == 0.toLong()) {
                                    disposable.add(ViewModelProviders
                                            .of(this@BudgetFragment)
                                            .get(BudgetViewModel::class.java)
                                            .insert(t)
                                            .subscribeOn(Schedulers.io())
                                            .subscribe({
                                                Log.i(BudgetFragment::class.java.name, it.toString())
                                            }, {
                                                Log.i(BudgetFragment::class.java.name, it.message)
                                            }))
                                } else {
                                    Thread(Runnable {
                                        ViewModelProviders
                                                .of(this@BudgetFragment)[BudgetViewModel::class.java]
                                                .updateIncomes(t.incomes, t.id)
                                    }).start()
                                }
                            }
                            override fun onDismiss(t: Budget) {
                            }

                        }).show(fragmentManager, BudgetFragment::class.java.name)
            }
        }
        ViewModelProviders.of(this)[ExpenseViewModel::class.java].expenses(month(month())).observe(this, Observer {
            run {
                fragmentBudgetBinding.logAdapter = CustomAdapter(it, R.layout.log_single_item, object : BindingData<Expense, LogSingleItemBinding> {
                    override fun bindData(t: Expense, vm: LogSingleItemBinding) {
                        vm.setExpense(t)
                    }
                })

            }
        })
        return fragmentBudgetBinding.root
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
