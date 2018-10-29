package al.bruno.financaime;

import android.os.Bundle;

import al.bruno.financaime.adapter.CustomAdapter;
import al.bruno.financaime.callback.BindingData;
import al.bruno.financaime.callback.OnItemClickListener;
import al.bruno.financaime.databinding.CategorySingleItemBinding;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import al.bruno.financaime.callback.RecyclerViewOnClickListener;
import al.bruno.financaime.dialog.EditCategory;
import al.bruno.financaime.model.Category;
import al.bruno.financaime.model.Database;

public class ExpenseCategoriesFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);RecyclerView categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        List<Category> categories = new Database(getContext()).categories();

        CustomAdapter<Category, CategorySingleItemBinding> categoriesAdapter = new CustomAdapter<>(categories, R.layout.category_single_item, (category, categorySingleItemBinding) -> {
            categorySingleItemBinding.setCategory(category);
            categorySingleItemBinding.setOnItemClickListener(new OnItemClickListener<Category>() {
                @Override
                public void onItemClick(Category category) {
                    new EditCategory
                            .Builder()
                            .setCategory(category)
                            .setHint(getString(R.string.category))
                            .setTitle(getString(R.string.add_category))
                            .build()
                            .OnEditCategoryListener(new EditCategory.EditCategoryListener() {
                                @Override
                                public void onSave(Category category) {
                                    /*categories.set(position, category);
                                    categoriesAdapter.notifyItemChanged(position, categories);*/
                                }
                            }).show(getFragmentManager(), "EDIT_CATEGORY");
                }

                @Override
                public boolean onLongItemClick(Category category) {
                    return false;
                }
            });
        });

        categoryRecyclerView.setAdapter(categoriesAdapter);

        view.findViewById(R.id.category_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditCategory
                        .Builder()
                        .setHint(getString(R.string.category))
                        .setTitle(getString(R.string.add_category))
                        .build()
                        .OnEditCategoryListener(new EditCategory.EditCategoryListener() {
                            @Override
                            public void onSave(Category category) {
                                categories.add(category);
                                //categoriesAdapter.notifyDataSetChanged();
                            }
                        }).show(getFragmentManager(), "EDIT_CATEGORY");
            }
        });
    }
}
