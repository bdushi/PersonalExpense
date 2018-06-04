package al.edu.feut.financaime.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import al.edu.feut.financaime.callback.RecyclerViewOnClickListener;
import al.edu.feut.financaime.holder.ExpenseCategoriesHolder;
import al.edu.feut.financaime.model.Category;

public class ExpenseCategoriesAdapter extends RecyclerView.Adapter<ExpenseCategoriesHolder> {

    private List<Category> categories;
    private int resources;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    public ExpenseCategoriesAdapter(List<Category> categories, int resources, RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.categories = categories;
        this.resources = resources;
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }
    @NonNull
    @Override
    public ExpenseCategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseCategoriesHolder(LayoutInflater.from(parent.getContext()).inflate(resources, parent, false), recyclerViewOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseCategoriesHolder holder, int position) {
        Category category = categories.get(position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
