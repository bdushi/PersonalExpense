package al.bruno.financaime.adapter

import al.bruno.financaime.callback.BindingData
import al.bruno.financaime.holder.CustomViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter<T, VM: ViewDataBinding>(t:List<T>, r:Int, bindingData:BindingData<T, VM>): RecyclerView.Adapter<CustomViewHolder<T, VM>>() {
    private val t:List<T>;
    private val o:List<T>;
    private val r:Int
    private val bindingData:BindingData<T, VM>
    private var filter: Filter? = null;
    init {
        this.t = t;
        this.o = t;
        this.r = r;
        this.bindingData = bindingData;
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<T, VM> {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(r, parent, false), bindingData)
    }

    override fun getItemCount(): Int {
        return t.size;
    }

    override fun onBindViewHolder(viewHolder: CustomViewHolder<T, VM>, position: Int) {
        viewHolder.bind(t.get(position))
    }

}