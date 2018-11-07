package al.bruno.financaime

import android.os.Bundle

import al.bruno.financaime.adapter.CustomAdapter
import al.bruno.financaime.callback.BindingData
import al.bruno.financaime.databinding.CategoriesSingleItemBinding
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
import androidx.lifecycle.Observer
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

        val categoriesExpense = view.findViewById<RecyclerView>(R.id.categories_expense)
        categoriesExpense.layoutManager = LinearLayoutManager(activity)
        categoriesExpense.itemAnimator = DefaultItemAnimator()
        categoriesExpense.addItemDecoration(DividerItemDecoration(activity!!, LinearLayoutManager.VERTICAL))
        ViewModelProviders
                .of(this)
                .get(CategoriesViewModel::class.java)
                .categories()
                .observe(this, Observer {
                    val customAdapter = CustomAdapter(it, R.layout.categories_single_item, object : BindingData<Categories, CategoriesSingleItemBinding> {
                        override fun bindData(t: Categories, vm: CategoriesSingleItemBinding) {
                            vm.categories = t
                        }
                    })
                    categoriesExpense.setAdapter(customAdapter);
                })



        view.findViewById<View>(R.id.categories_expense_add).setOnClickListener {
            val categories = Categories()
            categories.category = "Shopping"
            disposable.add(ViewModelProviders
                    .of(this)
                    .get(CategoriesViewModel::class.java)
                    .insert(categories)
                    .subscribeOn(Schedulers.io())
                    .subscribe(Consumer {

                    }))
        }
    }
    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
