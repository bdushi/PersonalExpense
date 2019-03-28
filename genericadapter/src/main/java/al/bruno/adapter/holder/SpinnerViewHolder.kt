package al.bruno.adapter.holder

import al.bruno.adapter.BindingData
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class SpinnerViewHolder<T, VM: ViewDataBinding>(view: View, bindingData: BindingData<T, VM>) {
    private val binding:VM;
    private val bindingData: BindingData<T, VM>;

    init {
        binding = DataBindingUtil.bind(view)!!
        this.bindingData = bindingData;
    }
    fun bind(t:T) {
        bindingData.bindData(t, binding);
        binding.executePendingBindings()
    }
}