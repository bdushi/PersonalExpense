package al.bruno.financaime;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import al.bruno.financaime.model.Budget;
import al.bruno.financaime.model.Categories;
import al.bruno.financaime.database.Database;
import al.bruno.financaime.model.Expense;
import al.bruno.financaime.util.Utilities;

public class ExpenseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        AppCompatTextView budgetHint = view.findViewById(R.id.budget_hint);
        AppCompatEditText inputExpValue = view.findViewById(R.id.input_exp_value);

        TextInputLayout inputExpTextInputLayout = view.findViewById(R.id.input_exp_text_input_layout);

        //Get Data from local db
        Budget budget = new Database(getContext()).budget(Utilities.INSTANCE.month(Utilities.INSTANCE.month()));
        List<Categories> categories = new Database(getContext()).categories();

        //Spinner adapter
        ArrayAdapter<Categories> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(adapter);

        budgetHint.setText(Utilities.INSTANCE.format(budget != null ? budget.getBudget() : 0));

        inputExpValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputExpTextInputLayout.setError(null);
            }
        });

        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (budget == null) {
                    Toast.makeText(getActivity(), R.string.out_of_budget, Toast.LENGTH_SHORT).show();
                } else if (spinner.getSelectedItem().toString().isEmpty() || inputExpValue.getText().toString().isEmpty()) {
                    inputExpTextInputLayout.setError(getString(R.string.alert));
                } else if (budget.getBudget() >= Double.parseDouble(inputExpValue.getText().toString())) {
                    Expense expense = new Expense();
                    expense.setExpenseName(spinner.getSelectedItem().toString());
                    expense.setExpense(Double.parseDouble(inputExpValue.getText().toString()));
                    expense.setDate(Utilities.INSTANCE.date());
                    expense.setIdBudget(budget.getId());
                    if (new Database(getContext()).insertExpense(expense) != -1) {
                        budget.setBudget(budget.getBudget() - expense.getExpense());
                        if (new Database(getContext()).updateBudgetValue(budget) != -1) {
                            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                            spinner.setSelection(0);
                            budgetHint.setText(Utilities.INSTANCE.format(budget.getBudget()));
                            inputExpValue.setText("");
                            inputExpValue.clearFocus();
                        } else
                            Toast.makeText(getActivity(), R.string.fail, Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getActivity(), R.string.out_of_budget, Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.edit_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.host, new CategoriesFragment())
                        .addToBackStack("EXPENSE_CATEGORIES_FRAGMENT")
                        .commit();
            }
        });
    }
}
