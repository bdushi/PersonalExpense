package al.bruno.financaime.dialog;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.Calendar;

import al.bruno.financaime.R;
import al.bruno.financaime.callback.DialogCallBack;
import al.bruno.financaime.model.Budget;

public class EditBudget extends DialogFragment implements View.OnClickListener, TextWatcher
{
    private Budget budget;
    private AppCompatEditText edit;
    private DialogCallBack dialogCallBack;
    private TextInputLayout textInputLayout;

    public EditBudget onDialogCallBack(DialogCallBack dialogCallBack)
    {
        this.dialogCallBack = dialogCallBack;
        return this;
    }

    public static class Builder {
        private Budget budget;
        public EditBudget.Builder setBudget(Budget budget)
        {
            this.budget = budget;
            return this;
        }

        public EditBudget build()
        {
            return newInstance(budget);
        }
    }

    public static EditBudget newInstance(Budget budget) {
        Bundle args = new Bundle();
        //args.putParcelable("BUDGET", budget);
        EditBudget fragment = new EditBudget();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = getArguments();
        if(bundle != null)
            budget = bundle.getParcelable("BUDGET");
        edit = view.findViewById(R.id.edit);
        edit.addTextChangedListener(this);

        textInputLayout = view.findViewById(R.id.text_input_layout);
        AppCompatTextView title = view.findViewById(R.id.title);
        title.setText(R.string.add_your_budget);
        textInputLayout.setHint(getString(R.string.budget));

        view.findViewById(R.id.save).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.save:
                if (!edit.getText().toString().isEmpty()) {
                    if (budget == null) {
                        double data = Double.parseDouble(edit.getText().toString());
                        Calendar calendar = Calendar.getInstance();
                        Budget budget = new Budget();
                        budget.setIncomes(data);
                        budget.setBudget(data);
                        budget.setDate(calendar.getTime());
                        dialogCallBack.onClickInsert(budget);
                        dismiss();
                    } else {
                        double budgetVal = Double.parseDouble(edit.getText().toString());
                        if (budgetVal <= (budget.getIncomes() - budget.getExpense())) {
                            budget.setBudget(budgetVal);
                            dialogCallBack.onClickUpdate(budget);
                            dismiss();
                        } else {
                            textInputLayout.setError(getString(R.string.out_of_incomes));
                        }
                    }
                } else {
                    textInputLayout.setError(getString(R.string.add_value));
                }
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        textInputLayout.setError(null);
    }
}
