package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import al.edu.feut.financaime.callback.DialogCallBack;
import al.edu.feut.financaime.dialog.EditBudget;
import al.edu.feut.financaime.dialog.EditIncomes;
import al.edu.feut.financaime.model.Budget;
import al.edu.feut.financaime.model.Database;
import al.edu.feut.financaime.util.Utilities;

public class BudgetFragment extends Fragment implements View.OnClickListener{

    private Budget mBudget;
    private AppCompatTextView budgetTv;
    private AppCompatTextView incomesTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBudget = new Budget();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatTextView editIncomes = view.findViewById(R.id.edit_incomes);
        AppCompatTextView editBudget = view.findViewById(R.id.edit_budget);

        budgetTv = view.findViewById(R.id.budget);
        incomesTv = view.findViewById(R.id.incomes);

        budgetTv.setText(Utilities.format(mBudget.getBudget()));
        incomesTv.setText(Utilities.format(mBudget.getIncomes()));

        editIncomes.setOnClickListener(this);
        editBudget.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_incomes:
                new EditIncomes
                        .Builder()
                        .setBudget(mBudget)
                        .build()
                        .onDialogCallBack(new DialogCallBack() {
                            @Override
                            public void onClickSave(Budget budget) {
                                if(new Database(getActivity()).insertBudget(budget) != -1) {
                                    budgetTv.setText(Utilities.format(mBudget.getBudget()));
                                    incomesTv.setText(Utilities.format(mBudget.getIncomes()));
                                    mBudget = budget;
                                }
                            }
                        })
                        .show(getFragmentManager(), "INCOMES");
                break;
            case R.id.edit_budget:
                new EditBudget
                        .Builder()
                        .setBudget(mBudget)
                        .build()
                        .onDialogCallBack(new DialogCallBack() {
                            @Override
                            public void onClickSave(Budget budget) {
                                if(new Database(getActivity()).insertBudget(budget) != -1) {
                                    budgetTv.setText(Utilities.format(mBudget.getBudget()));
                                    incomesTv.setText(Utilities.format(mBudget.getIncomes()));
                                    mBudget = budget;
                                }
                            }
                        })
                        .show(getFragmentManager(), "BUDGET");
                break;
        }
    }
}
