package al.bruno.personal.expense

import al.bruno.personal.expense.databinding.FragmentDetailsBinding
import al.bruno.personal.expense.dependency.injection.InjectionProvider.providerExpenseDetailsInjection
import al.bruno.personal.expense.util.ViewModelProviderFactory
import al.bruno.personal.expense.view.model.ExpenseDetailsViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsFragment : Fragment() {
    private val disposable : CompositeDisposable = CompositeDisposable()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentDetailsBinding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        val factory = ViewModelProviderFactory(ExpenseDetailsViewModel(providerExpenseDetailsInjection(context!!)!!))
        disposable.add(ViewModelProviders
                .of(this, factory)[ExpenseDetailsViewModel::class.java]
                .expense().subscribeOn(Schedulers.io())
                .subscribe({
                    fragmentDetailsBinding.details = it
                    Log.i(DetailsFragment::class.java.name, it.toString())
                },{
                    Log.i(DetailsFragment::class.java.name, it.message)
                }))
        fragmentDetailsBinding.expenseLogCalendarView.setOnDateChangedListener { widget, date, selected ->

        }
        return fragmentDetailsBinding.root;
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
