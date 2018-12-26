package al.bruno.personal.expense.dialog

import al.bruno.personal.expense.R
import al.bruno.personal.expense.callback.OnClickListener
import al.bruno.personal.expense.databinding.BottomSheetExpenseBinding
import al.bruno.personal.expense.model.Expense
import al.bruno.personal.expense.view.model.ExpenseViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime

class ExpenseBottomSheet : BottomSheetDialogFragment() {
    private val disposable : CompositeDisposable = CompositeDisposable()
    companion object {
        class Builder {
            var expense: Expense? = null
            fun setExpense(expense: Expense): Builder {
                this.expense = expense
                return this
            }
            fun build() : ExpenseBottomSheet {
                return newInstance(expense!!)
            }
        }

        private fun newInstance(expense: Expense): ExpenseBottomSheet {
            val expenseBottomSheet = ExpenseBottomSheet()
            val bundle = Bundle()
            bundle.putParcelable("EXPENSE", expense)
            expenseBottomSheet.arguments = bundle
            return expenseBottomSheet
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentExpenseBinding : BottomSheetExpenseBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_expense, container, false)
        fragmentExpenseBinding.expense = arguments!!.getParcelable("EXPENSE")
        fragmentExpenseBinding.onClickListener = object : OnClickListener<Expense> {
            override fun onClick(t: Expense) {
                t.date = DateTime.now().withTimeAtStartOfDay()
                disposable.add(ViewModelProviders.of(activity!!)
                        .get(ExpenseViewModel::class.java)
                        .insert(t)
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            if (it != -1.toLong()) {
                                Log.i(ExpenseBottomSheet::class.java.name, "Success")
                                dismiss()
                                //Toast.makeText(activity, R.string.success, Toast.LENGTH_SHORT).show()
                            } else
                                Log.i(ExpenseBottomSheet::class.java.name, "Fail")
                            //Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show()
                        }, {
                            Log.i(ExpenseBottomSheet::class.java.name, it.message)
                            //Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        }))
            }
        }
        return fragmentExpenseBinding.root
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
