package al.bruno.financaime.holder;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import al.bruno.financaime.R;
import al.bruno.financaime.callback.RecyclerViewOnClickListener;

public class CategoriesHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView category;

    public CategoriesHolder(View itemView, RecyclerViewOnClickListener recyclerViewOnClickListener) {
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
