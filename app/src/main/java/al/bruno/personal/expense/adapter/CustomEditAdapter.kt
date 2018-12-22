package al.bruno.personal.expense.adapter

import al.bruno.personal.expense.adapter.observer.Observer
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



abstract class CustomEditAdapter<T, VM: RecyclerView.ViewHolder, VN: RecyclerView.ViewHolder>(t:List<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Observer<T> {
    private var ITEM_VIEW_TYPE_ITEM = 0
    private var ITEM_VIEW_TYPE_ADD = 1
    private val t:ArrayList<T> = ArrayList(t);
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == t.size) {
            onBindEditViewHolder(holder as VN, t)
            return
        } else {
            onBindItemViewHolder(holder as VM, t[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ITEM_VIEW_TYPE_ADD) onEditViewHolder(parent, viewType) else onItemViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return t.size + 1;
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == t.size) ITEM_VIEW_TYPE_ADD else ITEM_VIEW_TYPE_ITEM;
    }

    abstract fun onItemViewHolder(itemViewGroup: ViewGroup, viewType: Int): VM
    abstract fun onEditViewHolder(editViewGroup: ViewGroup, viewType: Int): VN

    abstract fun onBindItemViewHolder(itemViewHolder: VM, t: T)
    abstract fun onBindEditViewHolder(editViewHolder: VN, t:List<T>)

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
