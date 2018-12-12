package al.bruno.calendar.view.adapter;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
/**
 * Created by bruno on 6/23/2017.
 */

public class CustomViewHolder<T, VM extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private VM binding;
    private BindingInterface<T, VM> bindingInterface;

    public CustomViewHolder(View view, BindingInterface<T, VM> bindingInterface) {
        super(view);
        binding = DataBindingUtil.bind(view);
        this.bindingInterface = bindingInterface;
    }
    public void bindData(T model) {
        bindingInterface.bindData(model, binding);
        binding.executePendingBindings();
    }
}
