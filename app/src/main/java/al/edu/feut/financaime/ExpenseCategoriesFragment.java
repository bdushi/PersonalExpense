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

import al.edu.feut.financaime.dialog.EditCategory;


/**
 * Created by bruno on 24-Jul-17.
 */

public class ExpenseCategoriesFragment extends Fragment
{
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
        super.onViewCreated(view, savedInstanceState);
        RecyclerView categoryRecyclerView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        view.findViewById(R.id.category_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditCategory.Builder().build().show(getFragmentManager(), "EDIT_CATEGORY");
            }
        });
    }
}
