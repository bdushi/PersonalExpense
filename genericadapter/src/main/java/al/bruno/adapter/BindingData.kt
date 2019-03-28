package al.bruno.adapter

import androidx.databinding.ViewDataBinding

interface BindingData<T, VM : ViewDataBinding> {
    fun bindData(t:T, vm:VM);
}