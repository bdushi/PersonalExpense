package al.bruno.personal.expense

import al.bruno.personal.expense.callback.OnClick
import al.bruno.personal.expense.callback.OnClickListener
import al.bruno.personal.expense.databinding.BottomSheetExpenseBinding
import al.bruno.personal.expense.model.Expense
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable
import org.joda.time.DateTime

class ExpenseBottomSheet : BottomSheetDialogFragment() {
    private val disposable : CompositeDisposable = CompositeDisposable()
    companion object {
        class Builder {
            var expense: Expense? = null
            fun setExpense(expense: Expense): ExpenseBottomSheet.Companion.Builder  {
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
            bundle.putParcelable("CATEGORIES", expense)
            return expenseBottomSheet
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentExpenseBinding : BottomSheetExpenseBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_expense, container, false)
        fragmentExpenseBinding.expense = arguments!!.getParcelable("CATEGORIES")
        fragmentExpenseBinding.onClickListener = object : OnClickListener<Expense> {
            override fun onClick(t: Expense) {
                t.date = DateTime.now().withTimeAtStartOfDay()
                /*disposable.add(ViewModelProviders.of(activity!!)
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
                        }))*/
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        return fragmentExpenseBinding.root
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
