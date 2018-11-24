package al.bruno.personal.expense.adapter

import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.holder.SpinnerViewHolder
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.databinding.ViewDataBinding

class CustomSpinnerAdapter<T, VM:ViewDataBinding>(context: Context, r: Int, t : List<T>, bindingInterface: BindingData<T, VM>)
    : ArrayAdapter<T>(context, r, t) {
    private val t: List<T>
    private val r: Int
    private val bindingInterface: BindingData<T, VM>
    private val themedSpinnerAdapter: ThemedSpinnerAdapter.Helper

    init {
        this.t = t
        this.r = r
        this.bindingInterface = bindingInterface
        themedSpinnerAdapter = ThemedSpinnerAdapter.Helper(context)
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var contentView = view;
        if(contentView == null) {
            contentView = themedSpinnerAdapter.dropDownViewInflater.inflate(r, parent, false)
            val spinnerHolder = SpinnerViewHolder<T, VM>(contentView, bindingInterface)
            spinnerHolder.bind(t[position])
            contentView.tag = spinnerHolder
        } else {
            val spinnerHolder = contentView.tag as SpinnerViewHolder<T, VM>
            spinnerHolder.bind(t[position])
        }
        return contentView!!
    }

    override fun getDropDownView(position: Int, view: View?, parent: ViewGroup): View {
        return getView(position, view, parent)
    }

    override fun isEmpty(): Boolean {
        return t.isEmpty()
    }
}