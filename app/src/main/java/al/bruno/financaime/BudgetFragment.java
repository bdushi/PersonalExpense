package al.bruno.financaime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import al.bruno.financaime.callback.DialogCallBack;
import al.bruno.financaime.dialog.EditBudget;
import al.bruno.financaime.dialog.EditIncomes;
import al.bruno.financaime.model.Budget;
import al.bruno.financaime.util.Utilities;

public class BudgetFragment extends Fragment implements View.OnClickListener{

    private Budget mBudget;
    private AppCompatTextView budgetTv;
    private AppCompatTextView incomesTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mBudget = new Database(getContext()).budget(Utilities.INSTANCE.month(Utilities.INSTANCE.month()));
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

        budgetTv.setText(Utilities.INSTANCE.format(mBudget != null ? mBudget.getBudget() : 0));
        incomesTv.setText(Utilities.INSTANCE.format(mBudget != null ? mBudget.getIncomes() : 0));

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
        //log.setAdapter(new CustomAdapter<Expense, LogSingleItemBinding>(new Database(getContext()).expense(Utilities.INSTANCE.month(Utilities.INSTANCE.month())), R.layout.log_single_item, (expense, logSingleItemBinding) -> logSingleItemBinding.setExpense(expense)));
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
                                /*if(new Database(getActivity()).insertBudget(budget) != -1) {
                                    budgetTv.setText(Utilities.INSTANCE.format(budget.getBudget()));
                                    incomesTv.setText(Utilities.INSTANCE.format(budget.getIncomes()));
                                    mBudget = budget;
                                }*/
                            }

                            @Override
                            public void onClickUpdate(Budget budget) {
                                /*if(new Database(getActivity()).updateIncomesValue(budget) != -1) {
                                    budgetTv.setText(Utilities.INSTANCE.format(budget.getBudget()));
                                    incomesTv.setText(Utilities.INSTANCE.format(budget.getIncomes()));
                                    mBudget = budget;
                                }*/
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
                                /*if((new Database(getActivity()).insertBudget(budget)) != -1) {
                                    budgetTv.setText(Utilities.INSTANCE.format(budget.getBudget()));
                                    incomesTv.setText(Utilities.INSTANCE.format(budget.getIncomes()));
                                    mBudget = budget;
                                }*/
                            }

                            @Override
                            public void onClickUpdate(Budget budget) {
                                /*if(new Database(getActivity()).updateBudgetValue(budget) != 0) {
                                    budgetTv.setText(Utilities.INSTANCE.format(budget.getBudget()));
                                    incomesTv.setText(Utilities.INSTANCE.format(budget.getIncomes()));
                                    mBudget = budget;
                                }*/
                            }
                        })
                        .show(getFragmentManager(), "BUDGET");
                break;
        }
    }
}
