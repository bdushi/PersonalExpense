package al.bruno.personal.expense.dialog

import al.bruno.personal.expense.R
import al.bruno.personal.expense.callback.OnClickListener
import al.bruno.personal.expense.databinding.BottomSheetIncomesBinding
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

class IncomesBottomSheet: BottomSheetDialogFragment() {
    private val disposable : CompositeDisposable = CompositeDisposable()
    companion object {
        class Builder {
            var expense: Expense? = null
            fun setExpense(expense: Expense): Builder {
                this.expense = expense
                return this
            }
            fun build() : IncomesBottomSheet {
                return newInstance(expense!!)
            }
        }

        private fun newInstance(expense: Expense): IncomesBottomSheet {
            val incomesBottomSheet = IncomesBottomSheet()
            val bundle = Bundle()
            bundle.putParcelable("EXPENSE", expense)
            incomesBottomSheet.arguments = bundle
            return incomesBottomSheet
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bottomSheetIncomesBinding = DataBindingUtil.inflate<BottomSheetIncomesBinding>(inflater, R.layout.bottom_sheet_incomes, container, false)
        bottomSheetIncomesBinding.expense = arguments!!.getParcelable("EXPENSE")
        bottomSheetIncomesBinding.onClickListener = object : OnClickListener<Expense> {
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
        return bottomSheetIncomesBinding.root
    }
}