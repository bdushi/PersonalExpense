package al.bruno.financaime.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.R
import al.bruno.financaime.callback.OnClickListener
import al.bruno.financaime.callback.OnEditListeners
import al.bruno.financaime.databinding.DialogEditBudgetBinding
import al.bruno.financaime.model.Budget
import al.bruno.financaime.model.BudgetMaster
import al.bruno.financaime.util.Utilities.date
import androidx.databinding.DataBindingUtil
import java.util.*

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
                if(t.incomes == 0.0 && t.budget == 0.0) {
                    val b = Budget()
                    b.incomes = t.budget
                    b.budget = t.budget
                    b.date = date()
                    onEditListeners!!.onEdit(b)
                } else {
                    val b = Budget()
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
