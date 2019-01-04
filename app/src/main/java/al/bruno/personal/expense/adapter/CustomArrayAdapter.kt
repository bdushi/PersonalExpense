package al.bruno.personal.expense.adapter

import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.holder.CustomViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class CustomArrayAdapter<T, VM: ViewDataBinding>(private val t:Array<T>, private val r: Int, private val bindingData: BindingData<T, VM>): RecyclerView.Adapter<CustomViewHolder<T, VM>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<T, VM> {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(r, parent, false), bindingData)
    }

    override fun getItemCount(): Int {
        return t.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder<T, VM>, position: Int) {
        holder.bind(t[position])
    }

}
