package al.edu.feut.financaime.holder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import al.edu.feut.financaime.R;

public class LogHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView name;
    public AppCompatTextView expense;

    public LogHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        expense = itemView.findViewById(R.id.expense);
    }
}
