package al.bruno.adapter.holder

import al.bruno.adapter.BindingData
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class CustomViewHolder<T, VM : ViewDataBinding>(itemView: View, private val bindingData: BindingData<T, VM>) : RecyclerView.ViewHolder(itemView) {
    private val binding: VM = DataBindingUtil.bind(itemView)!!
    fun bind(t:T) {
        bindingData.bindData(t, binding);
        binding.executePendingBindings()
    }
}