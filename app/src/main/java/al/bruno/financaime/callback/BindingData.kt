package al.bruno.financaime.callback

import androidx.databinding.ViewDataBinding

interface BindingData<T, VM : ViewDataBinding> {
    fun bindData(t:T, vm:VM);
}