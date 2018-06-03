package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import al.edu.feut.financaime.dialog.EditBudget;
import al.edu.feut.financaime.dialog.EditIncomes;
import al.edu.feut.financaime.model.Budget;

public class BudgetFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatTextView editIncomes = view.findViewById(R.id.edit_incomes);
        AppCompatTextView editBudget = view.findViewById(R.id.edit_budget);
        editIncomes.setOnClickListener(this);
        editBudget.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_incomes:
                new EditIncomes.Builder().setBudget(new Budget()).build().show(getFragmentManager(), "INCOMES");
                break;
            case R.id.edit_budget:
                new EditBudget.Builder().setBudget(new Budget()).build().show(getFragmentManager(), "BUDGET");
                break;
        }
    }
}
