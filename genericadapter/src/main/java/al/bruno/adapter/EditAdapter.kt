package al.bruno.adapter

import al.bruno.adapter.holder.CustomViewHolder
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import android.view.LayoutInflater

class EditAdapter<T, VM: ViewDataBinding, VN: ViewDataBinding>(
        t: List<T>,
        private val item: Int,
        private val bindingData: BindingData<T, VM>,
        private val edit: Int,
        private val bindingEdit: BindingData<List<T>, VN>)
    : CustomEditAdapter<T, CustomViewHolder<T, VM>, CustomViewHolder<List<T>, VN>>(t) {

    override fun onItemViewHolder(itemViewGroup: ViewGroup, viewType: Int): CustomViewHolder<T, VM> {
        return CustomViewHolder(LayoutInflater.from(itemViewGroup.context).inflate(item, itemViewGroup, false), bindingData)
    }

    override fun onBindItemViewHolder(itemViewHolder: CustomViewHolder<T, VM>, t: T) {
        itemViewHolder.bind(t);
    }

    override fun onEditViewHolder(editViewGroup: ViewGroup, viewType: Int): CustomViewHolder<List<T>, VN> {
        return CustomViewHolder(LayoutInflater.from(editViewGroup.context).inflate(edit, editViewGroup, false), bindingEdit)
    }

    override fun onBindEditViewHolder(editViewHolder: CustomViewHolder<List<T>, VN>, t: List<T>) {
        editViewHolder.bind(t)
    }
}