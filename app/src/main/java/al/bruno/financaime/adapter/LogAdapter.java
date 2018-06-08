package al.bruno.financaime.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import al.bruno.financaime.holder.LogHolder;
import al.bruno.financaime.model.Expense;

public class LogAdapter extends RecyclerView.Adapter<LogHolder> {
    private List<Expense> expenses;
    private int resources;

    public LogAdapter(List<Expense> expenses, int resources) {
        this.expenses = expenses;
        this.resources = resources;
    }
    @NonNull
    @Override
    public LogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LogHolder(LayoutInflater.from(parent.getContext()).inflate(resources, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull LogHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.expense.setText(expense.getExpensesStr());
        holder.name.setText(expense.getExpenseName());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }
}
