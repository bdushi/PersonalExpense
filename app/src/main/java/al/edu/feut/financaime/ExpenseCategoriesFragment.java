package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import al.edu.feut.financaime.adapter.CategoriesAdapter;
import al.edu.feut.financaime.callback.RecyclerViewOnClickListener;
import al.edu.feut.financaime.dialog.EditCategory;
import al.edu.feut.financaime.model.Category;
import al.edu.feut.financaime.model.Database;

public class ExpenseCategoriesFragment extends Fragment
{
    private CategoriesAdapter categoriesAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.expense_categories);
        return inflater.inflate(R.layout.fragment_categories_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);RecyclerView categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        List<Category> categories = new Database(getContext()).categories();

        categoriesAdapter = new CategoriesAdapter(categories, R.layout.category_single_item, new RecyclerViewOnClickListener() {
            @Override
            public void OnClickListener(View view, int position) {
                new EditCategory
                        .Builder()
                        .setCategory(categoriesAdapter.getItem(position))
                        .setHint(getString(R.string.category))
                        .setTitle(getString(R.string.add_category))
                        .build()
                        .OnEditCategoryListener(new EditCategory.EditCategoryListener() {
                            @Override
                            public void onSave(Category category) {
                                categories.set(position, category);
                                categoriesAdapter.notifyItemChanged(position, categories);
                            }
                        }).show(getFragmentManager(), "EDIT_CATEGORY");
            }
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
                                categoriesAdapter.notifyDataSetChanged();
                            }
                        }).show(getFragmentManager(), "EDIT_CATEGORY");
            }
        });
    }
}
