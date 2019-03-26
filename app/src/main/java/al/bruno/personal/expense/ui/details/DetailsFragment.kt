package al.bruno.personal.expense.ui.details

import al.bruno.calendar.view.listener.OnDateClickListener
import al.bruno.personal.expense.R
import al.bruno.personal.expense.adapter.CustomAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.databinding.ExpenseSingleItemBinding
import al.bruno.personal.expense.databinding.FragmentDetailsBinding
import al.bruno.personal.expense.model.Expense
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import javax.inject.Inject

class DetailsFragment : Fragment() {
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private val disposable : CompositeDisposable = CompositeDisposable()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentDetailsBinding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        fragmentDetailsBinding.onDateClickListener = OnDateClickListener { _, localDateTime ->
            disposable.addAll(
                    ViewModelProviders
                            .of(this@DetailsFragment, mViewModelFactory)[DetailsViewModel::class.java]
                            .expenses(localDateTime!!.dateTime)
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
                    ViewModelProviders
                            .of(this@DetailsFragment, mViewModelFactory)[DetailsViewModel::class.java]
                            .total(localDateTime.dateTime)
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                fragmentDetailsBinding.total = it
                            },{
                                fragmentDetailsBinding.total = null
                                Log.i(DetailsFragment::class.java.name, it.message)
                            })
            )
        }

        disposable.addAll(
                ViewModelProviders
                        .of(this, mViewModelFactory)[DetailsViewModel::class.java]
                        .expenses(DateTime.now().withTime(7, 0, 0, 0))
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
                ViewModelProviders
                        .of(this, mViewModelFactory)[DetailsViewModel::class.java]
                        .total(DateTime.now().withTime(7, 0, 0, 0))
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentDetailsBinding.total = it
                        },{
                            fragmentDetailsBinding.total = null
                            Log.i(DetailsFragment::class.java.name, it.message)
                        }),
                ViewModelProviders.of(this, mViewModelFactory)[DetailsViewModel::class.java]
                        .date()
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentDetailsBinding.event = it
                        }, {
                            Log.i(DetailsFragment::class.java.name, it.message)
                        })
        )
        return fragmentDetailsBinding.root
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
