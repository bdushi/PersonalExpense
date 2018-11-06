package al.bruno.financaime

import android.os.Bundle

import al.bruno.financaime.adapter.CustomAdapter
import al.bruno.financaime.callback.BindingData
import al.bruno.financaime.callback.OnItemClickListener
import al.bruno.financaime.callback.OnItemSelectedListener
import al.bruno.financaime.databinding.CategoriesSpinnerItemBinding
import al.bruno.financaime.databinding.CategorySingleItemBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.model.Categories
import al.bruno.financaime.view.model.CategoriesViewModel
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class CategoriesFragment : Fragment() {

    private val disposable : CompositeDisposable  = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryRecyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        categoryRecyclerView.layoutManager = LinearLayoutManager(activity)
        categoryRecyclerView.itemAnimator = DefaultItemAnimator()
        categoryRecyclerView.addItemDecoration(DividerItemDecoration(activity!!, LinearLayoutManager.VERTICAL))
        disposable
                .add(ViewModelProviders
                        .of(this)
                        .get(CategoriesViewModel::class.java)
                        .categories()
                        .subscribeOn(Schedulers.io())
                        .subscribe(Consumer {
                            val customAdapter = CustomAdapter(it, R.layout.category_single_item, object : BindingData<Categories, CategorySingleItemBinding> {
                                override fun bindData(t: Categories, vm: CategorySingleItemBinding) {
                                    vm.categories = t
                                }
                            })
                            categoryRecyclerView.setAdapter(customAdapter);
                        }))

        /*CustomAdapter<Categories, CategorySingleItemBinding> categoriesAdapter = new CustomAdapter<>(new ArrayList<>(), R.layout.category_single_item, (category, categorySingleItemBinding) -> {
            categorySingleItemBinding.setCategories(category);
            categorySingleItemBinding.setOnItemClickListener(new OnItemClickListener<Categories>() {
                @Override
                public void onItemClick(Categories category) {
                    new EditCategories
                            .Builder()
                            .setCategories(category)
                            .setHint(getString(R.string.categories))
                            .setTitle(getString(R.string.add_category))
                            .build()
                            .OnEditCategoryListener(new EditCategories.EditCategoryListener() {
                                @Override
                                public void onSave(Categories category) {
                                    *//*categories.set(position, category);
                                    categoriesAdapter.notifyItemChanged(position, categories);*//*
                                }
                            }).show(getFragmentManager(), "EDIT_CATEGORY");
                }

                @Override
                public boolean onLongItemClick(Categories category) {
                    return false;
                }
            });
        });*/

        view.findViewById<View>(R.id.category_add).setOnClickListener {
            val categories: Categories = Categories()
            categories.category = "Shopping"
            disposable.add(ViewModelProviders.of(this).get(CategoriesViewModel::class.java).insert(categories).subscribeOn(Schedulers.io()).subscribe(Consumer {

            }))
           /* EditCategories.Builder()
                    .setHint(getString(R.string.categories))
                    .setTitle(getString(R.string.add_category))
                    .build()
                    .OnEditCategoryListener {
                        //categoriesList.add(categories);
                        //categoriesAdapter.notifyDataSetChanged();
                    }.show(fragmentManager!!, "EDIT_CATEGORY")*/
        }
    }
    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
