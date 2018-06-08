package al.bruno.financaime.holder;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import al.bruno.financaime.R;

public class ExpenseHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView id;
    public AppCompatTextView name;
    public AppCompatTextView expense;
    public AppCompatTextView date;
    public ExpenseHolder(View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.id);
        name = itemView.findViewById(R.id.expense_name);
        expense = itemView.findViewById(R.id.expense_value);
        date = itemView.findViewById(R.id.date);
    }
}
