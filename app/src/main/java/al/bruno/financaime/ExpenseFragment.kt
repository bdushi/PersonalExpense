package al.bruno.financaime

import android.os.Bundle

import al.bruno.financaime.model.Budget
import com.google.android.material.textfield.TextInputLayout
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import al.bruno.financaime.model.Expense
import al.bruno.financaime.util.Utilities
import al.bruno.financaime.view.model.BudgetViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class ExpenseFragment : Fragment() {
    var budget: Budget? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner:AppCompatSpinner = view.findViewById(R.id.spinner)
        val budgetHint = view.findViewById<AppCompatTextView>(R.id.budget_hint)
        val inputExpValue = view.findViewById<AppCompatEditText>(R.id.input_exp_value)

        val inputExpTextInputLayout = view.findViewById<TextInputLayout>(R.id.input_exp_text_input_layout)
        //Get Data from local db
        //Budget budget = new Database(getContext()).budget(Utilities.INSTANCE.month(Utilities.INSTANCE.month()));

        /*ViewModelProviders.of(getActivity()).get(BudgetViewModel.class).findBudget(Utilities.month()).observe(this, budget ->
        {
            budgetTxt.setText(Utilities.format(budget != null ? budget.getBudget() : 0));
            expValue.setText(null);
            mBudget = budget;
        });*/

        ViewModelProviders.of(this).get(BudgetViewModel::class.java).budget(Utilities.month(Utilities.month())).observe(this, Observer {
            mBudget -> {
            budget = mBudget
            budgetHint.text = Utilities.format(mBudget.budget)
        }
        })

        //val categories = Database(context).categories()

        //Spinner adapter
        //val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, categories)
        ///spinner.adapter = adapter

        inputExpValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                inputExpTextInputLayout.error = null
            }
        })

        view.findViewById<View>(R.id.btn_add).setOnClickListener {
            if (budget == null) {
                Toast.makeText(activity, R.string.out_of_budget, Toast.LENGTH_SHORT).show()
            } else if (spinner.selectedItem.toString().isEmpty() || inputExpValue.text!!.toString().isEmpty()) {
                inputExpTextInputLayout.error = getString(R.string.alert)
            } else if (budget!!.budget >= java.lang.Double.parseDouble(inputExpValue.text!!.toString())) {
                val expense = Expense()
                expense.expenseName = spinner.selectedItem.toString()
                expense.expense = java.lang.Double.parseDouble(inputExpValue.text!!.toString())
                expense.date = Utilities.date()
                expense.idBudget = budget!!.id
                /*if (Database(context).insertExpense(expense) != -1) {
                    budget!!.budget = budget!!.budget - expense.expense
                    if (Database(context).updateBudgetValue(budget) != -1) {
                        Toast.makeText(activity, R.string.success, Toast.LENGTH_SHORT).show()
                        spinner.setSelection(0)
                        budgetHint.text = Utilities.format(budget!!.budget)
                        inputExpValue.setText("")
                        inputExpValue.clearFocus()
                    } else
                        Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show()
                }*/
            } else
                Toast.makeText(activity, R.string.out_of_budget, Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.edit_category).setOnClickListener {
            fragmentManager!!
                    .beginTransaction()
                    .replace(R.id.host, CategoriesFragment())
                    .addToBackStack("EXPENSE_CATEGORIES_FRAGMENT")
                    .commit()
        }
    }
}
