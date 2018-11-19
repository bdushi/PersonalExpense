package al.bruno.financaime.holder

import al.bruno.financaime.callback.BindingData
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class CustomViewHolder<T, VM : ViewDataBinding>(itemView: View, bindingData:BindingData<T, VM>) : RecyclerView.ViewHolder(itemView) {
    private val binding: VM
    private val bindingData:BindingData<T, VM>

    init {
        this.bindingData = bindingData;
        binding = DataBindingUtil.bind(itemView)!!
    }

    fun bind(t:T) {
        bindingData.bindData(t, binding);
        binding.executePendingBindings()
    }
}