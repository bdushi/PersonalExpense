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

public class BudgetFragment extends Fragment {
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
        //log.setAdapter(new CustomAdapter<Expense, LogSingleItemBinding>(new Database(getContext()).expense(Utilities.INSTANCE.month(Utilities.INSTANCE.month())), R.layout.log_single_item, (value, logSingleItemBinding) -> logSingleItemBinding.setValue(value)));
    }
}
