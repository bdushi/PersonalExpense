package al.edu.feut.financaime;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.Set;

import al.edu.feut.financaime.model.Database;
import al.edu.feut.financaime.model.Settings;

public class SettingsFragment extends Fragment {
    private Settings mSettings;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSettings = new Database(getContext()).settings();
        SwitchCompat switchCompat = view.findViewById(R.id.auto_expense);

        AppCompatEditText editDefaultIncomes = view.findViewById(R.id.edit_default_incomes);
        AppCompatEditText editDefaultBudget = view.findViewById(R.id.edit_default_budget);

        AppCompatTextView autoExpenseLabel = view.findViewById(R.id.auto_expense_label);

        TextInputLayout incomesInputLayout = view.findViewById(R.id.incomes_input_layout);
        TextInputLayout budgetInputLayout = view.findViewById(R.id.budget_input_layout);
        switchCompat.setChecked(mSettings != null && mSettings.isAuto());
        if(mSettings != null) {
            editDefaultIncomes.setEnabled(mSettings.isAuto());
            editDefaultBudget.setEnabled(mSettings.isAuto());
            if(mSettings.isAuto())
                autoExpenseLabel.setText(R.string.automatic_expense);
            else
                autoExpenseLabel.setText(R.string.manual_expense);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editDefaultIncomes.setEnabled(b);
                editDefaultBudget.setEnabled(b);
                if(b)
                    autoExpenseLabel.setText(R.string.automatic_expense);
                else
                    autoExpenseLabel.setText(R.string.manual_expense);
                if(mSettings == null) {
                    Settings settings = new Settings();
                    settings.setAuto(b);
                    new Handler().postDelayed(() -> {
                        if(new Database(getContext()).insertSettings(settings) != -1)
                            mSettings = settings;
                    }, 1000);
                } else {
                    new Handler().postDelayed(() -> {
                        new Database(getContext()).updateAutoSettings(mSettings.setAuto(b));
                    }, 1000);
                }
            }
        });
        editDefaultIncomes.setText(mSettings != null ? mSettings.getIncomesStr() : "0");
        editDefaultBudget.setText(mSettings != null ? mSettings.getBudgetStr(): "0");

        editDefaultIncomes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mSettings == null) {
                    Settings settings = new Settings();
                    settings.setIncomes(Float.parseFloat(charSequence.toString()));
                    new Handler().postDelayed(() -> {
                        if(new Database(getContext()).insertSettings(settings) != 0)
                            mSettings = settings;
                    }, 1000);
                    incomesInputLayout.setErrorEnabled(false);
                    incomesInputLayout.setError(null);
                } else {
                    if (!charSequence.toString().isEmpty()) {
                        if (Float.parseFloat(charSequence.toString()) < mSettings.getBudget()) {
                            incomesInputLayout.setErrorEnabled(true);
                            incomesInputLayout.setError(getString(R.string.budget_grater_than_incomes));
                        } else {
                            mSettings.setIncomes(Float.parseFloat(charSequence.toString()));
                            new Handler().postDelayed(() -> new Database(getContext()).updateIncomesSettings(mSettings), 1000);
                            incomesInputLayout.setErrorEnabled(false);
                            incomesInputLayout.setError(null);
                        }
                    } else {
                        mSettings.setIncomes(0);
                        new Handler().postDelayed(() -> new Database(getContext()).updateIncomesSettings(mSettings), 1000);
                        editDefaultIncomes.setText(String.valueOf(0));
                    }
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
                if(mSettings == null) {
                    Settings settings = new Settings();
                    settings.setBudget(Float.parseFloat(charSequence.toString()));
                    new Handler().postDelayed(() -> {
                        if(new Database(getContext()).insertSettings(settings) != 0)
                            mSettings = settings;
                        }, 1000);
                    budgetInputLayout.setErrorEnabled(false);
                    budgetInputLayout.setError(null);
                } else {
                    if (!charSequence.toString().isEmpty()) {
                        if (Float.parseFloat(charSequence.toString()) > mSettings.getIncomes()) {
                            budgetInputLayout.setErrorEnabled(true);
                            budgetInputLayout.setError(getString(R.string.budget_grater_than_incomes));
                        } else {
                            mSettings.setBudget(Float.parseFloat(charSequence.toString()));
                            new Handler().postDelayed(() -> new Database(getContext()).updateBudgetSettings(mSettings), 1000);
                            budgetInputLayout.setErrorEnabled(false);
                            budgetInputLayout.setError(null);
                        }
                    } else {
                        mSettings.setBudget(0);
                        new Handler().postDelayed(() -> new Database(getContext()).updateBudgetSettings(mSettings), 1000);
                        editDefaultBudget.setText(String.valueOf(0));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
