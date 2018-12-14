package al.bruno.personal.expense

import al.bruno.calendar.view.listener.OnDateClickListener
import al.bruno.calendar.view.model.LocalDateTime
import al.bruno.personal.expense.adapter.CustomAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.databinding.ExpenseSingleItemBinding
import al.bruno.personal.expense.databinding.FragmentDetailsBinding
import al.bruno.personal.expense.model.Expense
import al.bruno.personal.expense.util.Utilities
import al.bruno.personal.expense.view.model.ExpenseViewModel
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
import org.joda.time.DateTime

class DetailsFragment : Fragment() {
    private val disposable : CompositeDisposable = CompositeDisposable()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentDetailsBinding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        fragmentDetailsBinding.onDateClickListener = object : OnDateClickListener {
            override fun setOnDateClickListener(view: View?, localDateTime: LocalDateTime?) {
                disposable.add(ViewModelProviders.of(this@DetailsFragment)[ExpenseViewModel::class.java]
                        .expenses(DateTime.now().withTimeAtStartOfDay())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentDetailsBinding.adapter = CustomAdapter(it, R.layout.expense_single_item, object : BindingData<Expense, ExpenseSingleItemBinding> {
                                override fun bindData(t: Expense, vm: ExpenseSingleItemBinding) {
                                    vm.expense = t
                                }
                            })
                        },{
                            Log.i(DetailsFragment::class.java.name, it.message)
                        }))
            }
        }

        disposable.addAll(
                ViewModelProviders.of(this)[ExpenseViewModel::class.java]
                .expenses(DateTime.now().withTimeAtStartOfDay())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    fragmentDetailsBinding.adapter = CustomAdapter(it, R.layout.expense_single_item, object : BindingData<Expense, ExpenseSingleItemBinding> {
                        override fun bindData(t: Expense, vm: ExpenseSingleItemBinding) {
                            vm.expense = t
                        }
                    })
                },{
                    Log.i(DetailsFragment::class.java.name, it.message)
                }),
                ViewModelProviders.of(this)[ExpenseViewModel::class.java]
                .total(DateTime.now().withTimeAtStartOfDay())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    fragmentDetailsBinding.total = it
                },{
                    fragmentDetailsBinding.total = null
                    Log.i(DetailsFragment::class.java.name, it.message)
                }),
                ViewModelProviders.of(this)[ExpenseViewModel::class.java]
                        .date()
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentDetailsBinding.event = it
                        }, {
                            Log.i(DetailsFragment::class.java.name, it.message)
                        }))
        return fragmentDetailsBinding.root
        //val factory = ViewModelProviderFactory(ExpenseDetailsViewModel(providerExpenseDetailsInjection(context!!)!!))
        /*disposable.add(ViewModelProviders
                .of(this, factory)[ExpenseDetailsViewModel::class.java]
                .expense().subscribeOn(Schedulers.io())
                .subscribe({
                    fragmentDetailsBinding.details = it
                    Log.i(DetailsFragment::class.java.name, it.toString())
                },{
                    Log.i(DetailsFragment::class.java.name, it.message)
                }))*/
        /*disposable.addAll(ViewModelProviders.of(this)[ExpenseViewModel::class.java]
                .expenses(DateTimeUtils.toSqlDate(date.date))
                .subscribeOn(Schedulers.io())
                .subscribe({
                    fragmentDetailsBinding.adapter = CustomAdapter(it, R.layout.expense_single_item, object : BindingData<Expense, ExpenseSingleItemBinding> {
                        override fun bindData(t: Expense, vm: ExpenseSingleItemBinding) {
                            vm.expense = t
                        }
                    })
                },{
                    Log.i(DetailsFragment::class.java.name, it.message)
                }),
                ViewModelProviders.of(this)[ExpenseViewModel::class.java]
                        .total(DateTimeUtils.toSqlDate(date.date))
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentDetailsBinding.total = it
                        },{
                            fragmentDetailsBinding.total = null
                            Log.i(DetailsFragment::class.java.name, it.message)
                        }))*/
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
