package al.bruno.personal.expense.callback

import androidx.databinding.ViewDataBinding

interface BindingData<T, VM : ViewDataBinding> {
    fun bindData(t:T, vm:VM);
}