package al.bruno.personal.expense.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.personal.expense.R
import al.bruno.personal.expense.callback.OnClickListener
import al.bruno.personal.expense.callback.OnEditListeners
import al.bruno.personal.expense.databinding.DialogEditIncomesBinding
import al.bruno.personal.expense.model.Budget
import al.bruno.personal.expense.model.BudgetMaster
import al.bruno.personal.expense.util.Utilities.date
import androidx.databinding.DataBindingUtil

class EditIncomesDialog : DialogFragment() {
    private var onEditListeners: OnEditListeners<Budget>? = null
    class Builder {
        private var budget: BudgetMaster? = null

        fun setBudget(budget: BudgetMaster): EditIncomesDialog.Builder {
            this.budget = budget
            return this
        }

        fun build(): EditIncomesDialog {
            return newInstance(budget)
        }
    }
    companion object {
        private fun newInstance(budget: BudgetMaster?): EditIncomesDialog {
            val args = Bundle()
            val fragment = EditIncomesDialog()
            args.putParcelable("BUDGET", budget)
            fragment.arguments = args
            return fragment
        }
    }

    fun onDialogEditListeners(onEditListeners: OnEditListeners<Budget>): EditIncomesDialog {
        this.onEditListeners = onEditListeners
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dialogEditIncomesDialog: DialogEditIncomesBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit_incomes, container, false)
        val budgetMaster: BudgetMaster = arguments!!.getParcelable("BUDGET") ?: BudgetMaster()
        dialogEditIncomesDialog.budget = budgetMaster.budget ?: Budget()
        dialogEditIncomesDialog.onCancelClickListener = object : OnClickListener<Budget> {
            override fun onClick(t: Budget) {
                onEditListeners!!.onDismiss(t)
                dismiss()
            }
        }
        dialogEditIncomesDialog.onEditClickListener = object : OnClickListener<Budget> {
            override fun onClick(t: Budget) {
                val b = Budget()
                b.id = t.id
                b.incomes = t.incomes
                b.budget = t.budget
                b.date = date()
                onEditListeners!!.onEdit(b)
                dismiss()
            }
        }
        return dialogEditIncomesDialog.root
    }
}
