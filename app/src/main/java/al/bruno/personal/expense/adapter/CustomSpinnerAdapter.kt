package al.bruno.personal.expense.adapter

import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.holder.SpinnerViewHolder
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.databinding.ViewDataBinding

class CustomSpinnerAdapter<T, VM:ViewDataBinding, VD: ViewDataBinding>(context: Context, private val r: Int, private val d: Int, private val t: Array<T>, private val bindingView: BindingData<T, VM>, private val bindingDropDownView: BindingData<T, VD>) : ArrayAdapter<T>(context, r, t) {
    private val themedSpinnerAdapter: ThemedSpinnerAdapter.Helper = ThemedSpinnerAdapter.Helper(context)
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var contentView = view;
        if(contentView == null) {
            contentView = themedSpinnerAdapter.dropDownViewInflater.inflate(r, parent, false)
            val spinnerHolder = SpinnerViewHolder(contentView, bindingView)
            spinnerHolder.bind(t[position])
            contentView.tag = spinnerHolder
        } else {
            val spinnerHolder = contentView.tag as SpinnerViewHolder<T, VM>
            spinnerHolder.bind(t[position])
        }
        return contentView!!
    }

    override fun getDropDownView(position: Int, view: View?, parent: ViewGroup): View {
        var contentView = view;
        if(contentView == null) {
            contentView = themedSpinnerAdapter.dropDownViewInflater.inflate(d, parent, false)
            val spinnerHolder = SpinnerViewHolder(contentView, bindingDropDownView)
            spinnerHolder.bind(t[position])
            contentView.tag = spinnerHolder
        } else {
            val spinnerHolder = contentView.tag as SpinnerViewHolder<T, VD>
            spinnerHolder.bind(t[position])
        }
        return contentView!!
    }

    override fun isEmpty(): Boolean {
        return t.isEmpty()
    }
}