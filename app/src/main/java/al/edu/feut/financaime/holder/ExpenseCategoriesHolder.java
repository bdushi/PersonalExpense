package al.edu.feut.financaime.holder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import al.edu.feut.financaime.R;
import al.edu.feut.financaime.callback.RecyclerViewOnClickListener;

public class ExpenseCategoriesHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView category;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    public ExpenseCategoriesHolder(View itemView, RecyclerViewOnClickListener recyclerViewOnClickListener) {
        super(itemView);
        category = itemView.findViewById(R.id.category);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewOnClickListener.OnClickListener(v, getAdapterPosition());
            }
        });
    }
}
