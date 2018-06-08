package al.bruno.financaime.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import al.bruno.financaime.holder.ExpenseHolder;
import al.bruno.financaime.model.Expense;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseHolder> {

    private List<Expense> expenses;
    private int resources;

    public ExpenseAdapter(List<Expense> expenses, int resources) {
        this.expenses = expenses;
        this.resources = resources;
    }

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseHolder(LayoutInflater.from(parent.getContext()).inflate(resources, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.id.setText(expense.getIdStr());
        holder.expense.setText(expense.getExpensesStr());
        holder.name.setText(expense.getExpenseName());
        holder.date.setText(expense.getDateStr());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }
}
