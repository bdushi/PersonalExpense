package al.bruno.adapter

import al.bruno.adapter.holder.CustomViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class CustomArrayAdapter<T, VM : ViewDataBinding>(private var t: Array<T>?, private val resources: Int, private val bindingInterface: BindingData<T, VM>) : RecyclerView.Adapter<CustomViewHolder<T, VM>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<T, VM> {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(resources, parent, false), bindingInterface)

    }

    override fun onBindViewHolder(holder: CustomViewHolder<T, VM>, position: Int) {
        holder.bind(t!![position])
    }

    override fun getItemCount(): Int {
        return if (t == null) 0 else t!!.size
    }


    fun setT(t: Array<T>) {
        this.t = t
        notifyDataSetChanged()
    }
}
