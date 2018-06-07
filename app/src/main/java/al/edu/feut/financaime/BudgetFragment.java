package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import al.edu.feut.financaime.adapter.LogAdapter;
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
        mBudget = new Database(getContext()).budget(Utilities.month(Utilities.month()));
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

        budgetTv.setText(Utilities.format(mBudget != null ? mBudget.getBudget() : 0));
        incomesTv.setText(Utilities.format(mBudget != null ? mBudget.getIncomes() : 0));

        if(mBudget == null) {
            editBudget.setOnClickListener(this);
            editBudget.setText(R.string.edit);
        } else if(mBudget.getIncomes() - mBudget.getExpense() == 0){
            editBudget.setOnClickListener(null);
            editBudget.setText(R.string.out_of_incomes_alert);
        } else {
            editBudget.setOnClickListener(this);
            editBudget.setText(R.string.edit);
        }
        editIncomes.setOnClickListener(mBudget == null ? this : null);

        RecyclerView log = view.findViewById(R.id.log);
        log.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        log.setItemAnimator(new DefaultItemAnimator());
        log.setAdapter(new LogAdapter(new Database(getContext()).expense(Utilities.month(Utilities.month())), R.layout.log_single_item));
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
                            public void onClickInsert(Budget budget) {
                                if(budget.setId(new Database(getActivity()).insertBudget(budget) )!= -1) {
                                    budgetTv.setText(Utilities.format(budget.getBudget()));
                                    incomesTv.setText(Utilities.format(budget.getIncomes()));
                                    mBudget = budget;
                                }
                            }

                            @Override
                            public void onClickUpdate(Budget budget) {
                                if(new Database(getActivity()).updateIncomesValue(budget) != -1) {
                                    budgetTv.setText(Utilities.format(budget.getBudget()));
                                    incomesTv.setText(Utilities.format(budget.getIncomes()));
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
                            public void onClickInsert(Budget budget) {
                                if(budget.setId(new Database(getActivity()).insertBudget(budget)) != -1) {
                                    budgetTv.setText(Utilities.format(budget.getBudget()));
                                    incomesTv.setText(Utilities.format(budget.getIncomes()));
                                    mBudget = budget;
                                }
                            }

                            @Override
                            public void onClickUpdate(Budget budget) {
                                if(new Database(getActivity()).updateBudgetValue(budget) != 0) {
                                    budgetTv.setText(Utilities.format(budget.getBudget()));
                                    incomesTv.setText(Utilities.format(budget.getIncomes()));
                                    mBudget = budget;
                                }
                            }
                        })
                        .show(getFragmentManager(), "BUDGET");
                break;
        }
    }
}
