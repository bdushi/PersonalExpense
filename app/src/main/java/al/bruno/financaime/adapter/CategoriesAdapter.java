package al.bruno.financaime.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import al.bruno.financaime.callback.RecyclerViewOnClickListener;
import al.bruno.financaime.holder.CategoriesHolder;
import al.bruno.financaime.model.Category;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesHolder> {

    private List<Category> categories;
    private int resources;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    public CategoriesAdapter(List<Category> categories, int resources, RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.categories = categories;
        this.resources = resources;
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }
    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesHolder(LayoutInflater.from(parent.getContext()).inflate(resources, parent, false), recyclerViewOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        Category category = categories.get(position);
        holder.category.setText(category.getCategory());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public Category getItem(int position) {
        return categories.get(position);
    }
}
