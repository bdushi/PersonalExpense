package al.edu.feut.financaime;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import al.edu.feut.financaime.model.Database;
import al.edu.feut.financaime.model.Settings;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Settings mSettings = new Database(getContext()).settings();

        AppCompatEditText editDefaultIncomes = view.findViewById(R.id.edit_default_incomes);
        AppCompatEditText editDefaultBudget = view.findViewById(R.id.edit_default_budget);

        TextInputLayout incomesInputLayout = view.findViewById(R.id.incomes_input_layout);
        TextInputLayout budgetInputLayout = view.findViewById(R.id.budget_input_layout);

        editDefaultIncomes.setText( mSettings.getIncomesStr());
        editDefaultBudget.setText(mSettings.getBudgetStr());
        editDefaultIncomes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty()) {
                    if (Float.parseFloat(charSequence.toString()) < mSettings.getBudget()) {
                        incomesInputLayout.setErrorEnabled(true);
                        incomesInputLayout.setError(getString(R.string.budget_grater_than_incomes));
                    } else {
                        mSettings.setIncomes(Float.parseFloat(charSequence.toString()));
                        new Handler().postDelayed(() ->new Database(getContext()).insertOrReplaceSettings(mSettings), 1000);
                        incomesInputLayout.setErrorEnabled(false);
                        incomesInputLayout.setError(null);
                    }
                } else {
                    mSettings.setIncomes(0);
                    new Handler().postDelayed(() -> new Database(getContext()).insertOrReplaceSettings(mSettings), 1000);
                    editDefaultIncomes.setText(String.valueOf(0));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editDefaultBudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty()) {
                    if (Float.parseFloat(charSequence.toString()) > mSettings.getIncomes()) {
                        budgetInputLayout.setErrorEnabled(true);
                        budgetInputLayout.setError(getString(R.string.budget_grater_than_incomes));
                    } else {
                        mSettings.setBudget(Float.parseFloat(charSequence.toString()));
                        new Handler().postDelayed(() ->new Database(getContext()).insertOrReplaceSettings(mSettings), 1000);
                        budgetInputLayout.setErrorEnabled(false);
                        budgetInputLayout.setError(null);
                    }
                } else {
                    mSettings.setBudget(0);
                    new Handler().postDelayed(() ->new Database(getContext()).insertOrReplaceSettings(mSettings), 1000);
                    editDefaultBudget.setText(String.valueOf(0));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
