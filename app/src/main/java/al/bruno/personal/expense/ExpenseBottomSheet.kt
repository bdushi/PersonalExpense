package al.bruno.personal.expense

import al.bruno.personal.expense.adapter.CustomSpinnerAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.callback.OnClick
import al.bruno.personal.expense.callback.OnClickListener
import al.bruno.personal.expense.databinding.BottomSheetExpenseBinding
import al.bruno.personal.expense.databinding.CategoriesSpinnerItemBinding
import al.bruno.personal.expense.model.Incomes
import al.bruno.personal.expense.model.Categories
import al.bruno.personal.expense.model.Expense
import al.bruno.personal.expense.util.Utilities
import al.bruno.personal.expense.view.model.BudgetViewModel
import al.bruno.personal.expense.view.model.CategoriesViewModel
import al.bruno.personal.expense.view.model.ExpenseViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime

class ExpenseBottomSheet : BottomSheetDialogFragment() {
    private val disposable : CompositeDisposable = CompositeDisposable()
    companion object {
        class Builder {
            var categories: Categories? = null
            fun setCategories(categories: Categories): ExpenseBottomSheet.Companion.Builder  {
                this.categories = categories
                return this
            }
            fun build() : ExpenseBottomSheet {
                return newInstance(categories!!)
            }
        }

        private fun newInstance(categories: Categories): ExpenseBottomSheet {
            val expenseBottomSheet = ExpenseBottomSheet()
            val bundle = Bundle()
            bundle.putParcelable("CATEGORIES", categories)
            return expenseBottomSheet
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentExpenseBinding : BottomSheetExpenseBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_expense, container, false)
        ViewModelProviders.of(this).get(BudgetViewModel::class.java).expense(Utilities.month(Utilities.month())).observe(this, Observer {
            run {
                fragmentExpenseBinding.incomes = it
            }
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

        fragmentExpenseBinding.onClickListener = object : OnClickListener<Incomes> {
            override fun onClick(t: Incomes) {
                val expense = Expense()
                expense.expense = t.expenseStr
                expense.amount = t.amount
                expense.date = DateTime.now().withTimeAtStartOfDay()
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
