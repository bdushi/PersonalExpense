package al.bruno.financaime

import al.bruno.financaime.adapter.CustomSpinnerAdapter
import al.bruno.financaime.callback.*
import al.bruno.financaime.databinding.FragmentExpenseBinding
import android.os.Bundle

import al.bruno.financaime.model.Budget
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.util.Utilities.month
import al.bruno.financaime.view.model.BudgetViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import al.bruno.financaime.databinding.CategoriesSpinnerItemBinding
import al.bruno.financaime.model.Categories
import al.bruno.financaime.model.Expense
import al.bruno.financaime.util.Utilities
import al.bruno.financaime.view.model.CategoriesViewModel
import al.bruno.financaime.view.model.ExpenseViewModel
import android.util.Log
import android.widget.Toast
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer

class ExpenseFragment : Fragment() {
    private val disposable : CompositeDisposable  = CompositeDisposable()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentExpenseBinding : FragmentExpenseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expense, container, false)
        ViewModelProviders.of(this).get(BudgetViewModel::class.java).budget(month(month())).observe(this, Observer {
            run {
                fragmentExpenseBinding.budget = it
            }
        })

        ViewModelProviders.of(this)
                .get(CategoriesViewModel::class.java)
                .categories()
                .observe(this, Observer {
                    fragmentExpenseBinding.spinnerAdapter =
                            CustomSpinnerAdapter(activity!!, R.layout.categories_spinner_item, it, object : BindingData<Categories, CategoriesSpinnerItemBinding> {
                                override fun bindData(t: Categories, vm: CategoriesSpinnerItemBinding) {
                                    vm.categories = t
                                }
                            })
                })

        fragmentExpenseBinding.onClick = object : OnClick {
            override fun onClick(){
                fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.host, CategoriesFragment())
                        .addToBackStack("EXPENSE_CATEGORIES_FRAGMENT")
                        .commit()

            }
        }

        fragmentExpenseBinding.onClickListener = object : OnClickListener<Budget> {
            override fun onClick(t: Budget) {
                val expense = Expense()
                expense.expense = t.expense
                expense.amount = t.amount
                expense.date = Utilities.date()
                expense.idBudget = t.id
                disposable.add(ViewModelProviders.of(activity!!)
                        .get(ExpenseViewModel::class.java)
                        .insert(expense)
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if(it != -1.toLong()) {
                                t.amount = 0.0
                                Log.i(ExpenseFragment::class.java.name, "Success")
                                //Toast.makeText(activity, R.string.success, Toast.LENGTH_SHORT).show()
                            } else
                                Log.i(ExpenseFragment::class.java.name, "Fail")
                                //Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show()
                        }, {
                            Log.i(ExpenseFragment::class.java.name, it.message)
                            //Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        }))
                Toast.makeText(activity, expense.toString(), Toast.LENGTH_SHORT).show()
            }

        }
        return fragmentExpenseBinding.root
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
