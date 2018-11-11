package al.bruno.financaime.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.R
import al.bruno.financaime.callback.OnClick
import al.bruno.financaime.callback.OnClickListener
import al.bruno.financaime.callback.OnEditListeners
import al.bruno.financaime.databinding.DialogEditBudgetBinding
import al.bruno.financaime.model.Budget
import al.bruno.financaime.util.Utilities
import al.bruno.financaime.util.Utilities.date
import androidx.databinding.DataBindingUtil

class EditIncomesDialog : DialogFragment() {
    private  var budget = Budget()
    private var onEditListeners: OnEditListeners<Budget>? = null

    class Builder {
        private var budget: Budget? = null

        fun setBudget(budget: Budget): EditIncomesDialog.Builder {
            this.budget = budget
            return this
        }

        fun build(): EditIncomesDialog {
            return newInstance(budget)
        }
    }
    companion object {
        private fun newInstance(budget: Budget?): EditIncomesDialog {
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
        val dialogEditBudgetBinding: DialogEditBudgetBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit_budget, container, false)
        dialogEditBudgetBinding.budget = arguments?.getParcelable("CATEGORY") ?: budget
        dialogEditBudgetBinding.onCancelClickListener = object : OnClickListener<Budget> {
            override fun onClick(t: Budget) {
                onEditListeners!!.onDismiss(t)
                dismiss()
            }
        }
        dialogEditBudgetBinding.onEditClickListener = object : OnClickListener<Budget> {
            override fun onClick(t: Budget) { val budget = Budget()
                budget.incomes = t.amount
                budget.date = date()
                onEditListeners!!.onEdit(budget)
                dismiss()
            }
        }
        return dialogEditBudgetBinding.root
    }
    /*if(!edit.getText().toString().isEmpty()) {
           if(budget != null) {
               if(Double.parseDouble(edit.getText().toString()) >= budget.getBudget()) {
                   budget.setIncomes(Double.parseDouble(edit.getText().toString()));
                   dialogCallBack.onClickUpdate(budget);
                   dismiss();
               } else {
                   textInputLayout.setError(getString(R.string.incomes_alert));
               }
           } else {
               Calendar calendar = Calendar.getInstance();
               Budget budget = new Budget();
               budget.setIncomes(Double.parseDouble(edit.getText().toString()));
               budget.setBudget(0);
               budget.setDate(calendar.getTime());
               dialogCallBack.onClickInsert(budget);
               dismiss();
           }
       } else {
           textInputLayout.setError(getString(R.string.add_value));
       }*/
}
