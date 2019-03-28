package al.bruno.adapter

import al.bruno.adapter.holder.CustomViewHolder
import al.bruno.adapter.observer.Observer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter<T, VM: ViewDataBinding>(t:List<T>, private val r: Int, private val bindingData: BindingData<T, VM>): RecyclerView.Adapter<CustomViewHolder<T, VM>>(), Observer<T> {
    private val t:ArrayList<T> = ArrayList(t);
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<T, VM> {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(r, parent, false), bindingData)
    }

    override fun getItemCount(): Int {
        return t.size;
    }

    override fun onBindViewHolder(viewHolder: CustomViewHolder<T, VM>, position: Int) {
        viewHolder.bind(t[position])
    }

    fun item(position: Int) : T {
        return t[position]
    }

    public val items : List<T>

    get() {
        return t;
    }

    override fun update(l: T) {
        notifyItemChanged(t.indexOf(l))
    }

    override fun remove(l: T) {
        // because item was removed and mismatch the index
        // val position:Int = t.indexOf(l)
        notifyItemRemoved(t.indexOf(l))
        t.remove(l)
    }

    override fun add(l: T) {
        val position:Int = t.indexOf(l)
        when (position) {
            -1 -> {
                t.add(l)
                notifyDataSetChanged()
            }
            else -> {
                t[position] = l
                notifyItemChanged(position)
            }
        }
    }
}