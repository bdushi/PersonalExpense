package al.bruno.financaime

import al.bruno.financaime.adapter.CustomAdapter
import al.bruno.financaime.callback.BindingData
import al.bruno.financaime.callback.OnClickListener
import al.bruno.financaime.callback.OnEditListeners
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.databinding.FragmentBudgetBinding
import al.bruno.financaime.databinding.LogSingleItemBinding
import al.bruno.financaime.dialog.EditBudgetDialog
import al.bruno.financaime.dialog.EditIncomesDialog
import al.bruno.financaime.model.Budget
import al.bruno.financaime.model.BudgetMaster
import al.bruno.financaime.model.Expense
import al.bruno.financaime.util.Utilities.month
import al.bruno.financaime.view.model.BudgetMasterViewModel
import al.bruno.financaime.view.model.BudgetViewModel
import al.bruno.financaime.view.model.ExpenseViewModel
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
        disposable.add(ViewModelProviders.of(this).get(BudgetMasterViewModel::class.java).budget(month(month())).subscribeOn(Schedulers.io()).subscribe({
            fragmentBudgetBinding.budget = it
        }, {

        }))
        fragmentBudgetBinding.onClickListenerEditBudget = object: OnClickListener<BudgetMaster> {
            override fun onClick(t: BudgetMaster) {
                EditBudgetDialog
                        .Builder()
                        .setBudget(budget = t)
                        .build()
                        .onDialogEditListeners(object : OnEditListeners<Budget> {
                            override fun onEdit(t: Budget) {
                                disposable.add(ViewModelProviders
                                        .of(this@BudgetFragment)
                                        .get(BudgetViewModel::class.java)
                                        .insert(t)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe({

                                        }, {

                                        }))
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
                                disposable.add(ViewModelProviders
                                        .of(this@BudgetFragment)
                                        .get(BudgetViewModel::class.java)
                                        .insert(t)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe({

                                        }, {

                                        }))
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
