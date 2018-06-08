package al.bruno.financaime.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
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

public class EditIncomes extends DialogFragment implements View.OnClickListener, TextWatcher
{
    private AppCompatEditText edit;
    private TextInputLayout textInputLayout;
    private Budget budget;

    private DialogCallBack dialogCallBack;


    public EditIncomes onDialogCallBack(DialogCallBack dialogCallBack)
    {
        this.dialogCallBack = dialogCallBack;
        return this;
    }

    public static class Builder
    {
        private Budget budget;

        public EditIncomes.Builder setBudget(Budget budget)
        {
            this.budget = budget;
            return this;
        }

        public EditIncomes build()
        {
            return newInstance(budget);
        }
    }

    public static EditIncomes newInstance(Budget budget)
    {

        Bundle args = new Bundle();
        EditIncomes fragment = new EditIncomes();
        args.putParcelable("BUDGET", budget);
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

        textInputLayout.setHint(getString(R.string.incomes));
        title.setText(R.string.add_your_incomes);

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
                if(!edit.getText().toString().isEmpty()) {
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
