package al.bruno.personal.expense.adapter

import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.holder.CustomViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MonthNavigationAdapter<T, VM: ViewDataBinding>(private var t: java.util.Calendar, private val r: Int, private val bindingData: BindingData<Calendar, VM>): RecyclerView.Adapter<CustomViewHolder<Calendar, VM>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<Calendar, VM> {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(r, parent, false), bindingData)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: CustomViewHolder<Calendar, VM>, position: Int) {
        t.set(Calendar.MONTH, position)
        holder.bind(t)
    }
}
