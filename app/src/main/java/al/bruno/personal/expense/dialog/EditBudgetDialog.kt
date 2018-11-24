package al.bruno.personal.expense.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.personal.expense.R
import al.bruno.personal.expense.callback.OnClickListener
import al.bruno.personal.expense.callback.OnEditListeners
import al.bruno.personal.expense.databinding.DialogEditBudgetBinding
import al.bruno.personal.expense.model.Budget
import al.bruno.personal.expense.model.BudgetMaster
import al.bruno.personal.expense.util.Utilities.date
import androidx.databinding.DataBindingUtil

class EditBudgetDialog : DialogFragment() {
    private var onEditListeners: OnEditListeners<Budget>? = null

    fun onDialogEditListeners(onEditListeners: OnEditListeners<Budget>): EditBudgetDialog {
        this.onEditListeners = onEditListeners
        return this
    }

    class Builder {
        private var budget: BudgetMaster? = null
        fun setBudget(budget: BudgetMaster): EditBudgetDialog.Builder {
            this.budget = budget
            return this
        }

        fun build(): EditBudgetDialog {
            return newInstance(budget)
        }
    }
    companion object {
        private fun newInstance(budget: BudgetMaster?): EditBudgetDialog {
            val args = Bundle()
            args.putParcelable("BUDGET", budget);
            val fragment = EditBudgetDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dialogEditBudgetBinding: DialogEditBudgetBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit_budget, container, false)
        val budget:BudgetMaster = arguments!!.getParcelable("BUDGET") ?: BudgetMaster()
        dialogEditBudgetBinding.budget = budget.budget ?: Budget()
        dialogEditBudgetBinding.local = budget.amount
        dialogEditBudgetBinding.onCancelClickListener = object : OnClickListener<Budget> {
            override fun onClick(t: Budget) {
                onEditListeners!!.onDismiss(t)
                dismiss()
            }
        }
        dialogEditBudgetBinding.onEditClickListener = object : OnClickListener<Budget> {
            override fun onClick(t: Budget) {
                if(t.id == 0.toLong()) {
                    val b = Budget()
                    b.incomes = t.budget
                    b.budget = t.budget
                    b.date = date()
                    onEditListeners!!.onEdit(b)
                } else {
                    val b = Budget()
                    b.id = t.id
                    b.incomes = t.incomes
                    b.budget = t.budget
                    b.date = t.date
                    onEditListeners!!.onEdit(b)
                }
                dismiss()
            }
        }
        return dialogEditBudgetBinding.root
    }
}
