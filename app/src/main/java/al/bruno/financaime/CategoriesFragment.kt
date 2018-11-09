package al.bruno.financaime

import al.bruno.financaime.adapter.CustomAdapter
import al.bruno.financaime.callback.BindingData
import al.bruno.financaime.callback.OnClick
import android.os.Bundle

import al.bruno.financaime.callback.OnEditListener
import al.bruno.financaime.callback.OnSwipeItemListener
import al.bruno.financaime.databinding.CategoriesSingleItemBinding
import al.bruno.financaime.databinding.FragmentCategoriesExpenseBinding
import al.bruno.financaime.dialog.EditCategoriesDialog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.model.Categories
import al.bruno.financaime.view.model.CategoriesViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class CategoriesFragment : Fragment(), OnEditListener<Categories>, OnClick {

    //https://medium.com/fueled-engineering/swipe-drag-bind-recyclerview-817408125530
    private val disposable : CompositeDisposable  = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentCategoriesExpenseBinding = DataBindingUtil.inflate<FragmentCategoriesExpenseBinding>(inflater, R.layout.fragment_categories_expense, container, false);
        ViewModelProviders
                .of(this)
                .get(CategoriesViewModel::class.java)
                .categories()
                .observe(this, Observer {
                    fragmentCategoriesExpenseBinding.customAdapter = CustomAdapter(it, R.layout.categories_single_item, object : BindingData<Categories, CategoriesSingleItemBinding> {
                        override fun bindData(t: Categories, vm: CategoriesSingleItemBinding) {
                            vm.categories = t
                        }
                    })
                })
        fragmentCategoriesExpenseBinding.onClick = this
        fragmentCategoriesExpenseBinding.onSwipeItemListener = object : OnSwipeItemListener {
            override fun onItemSwipedLeft(position: Int) {
            }

            override fun onItemSwipedRight(position: Int) {
            }

        }
        return fragmentCategoriesExpenseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val categoriesExpense = view.findViewById<RecyclerView>(R.id.categories_expense)
        categoriesExpense.layoutManager = LinearLayoutManager(activity)
        categoriesExpense.itemAnimator = DefaultItemAnimator()
        categoriesExpense.addItemDecoration(DividerItemDecoration(activity!!, LinearLayoutManager.VERTICAL))
        ViewModelProviders
                .of(this)
                .get(CategoriesViewModel::class.java)
                .categories()
                .observe(this, Observer {
                    categoriesExpense.adapter = CustomAdapter(it, R.layout.categories_single_item, object : BindingData<Categories, CategoriesSingleItemBinding> {
                        override fun bindData(t: Categories, vm: CategoriesSingleItemBinding) {
                            vm.categories = t
                        }
                    })
                })*/
    }
    override fun onEdit(t: Categories) {
        disposable.add(ViewModelProviders
                .of(this)
                .get(CategoriesViewModel::class.java)
                .insert(t)
                .subscribeOn(Schedulers.io())
                .subscribe(Consumer {

                }))
    }
    override fun onClick() {
        EditCategoriesDialog
                .Builder()
                .setHint(R.string.categories)
                .setTitle(R.string.add_categories)
                .build()
                .OnCategoriesEditListener(this)
                .show(fragmentManager, CategoriesFragment::class.java.name)
    }
    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
