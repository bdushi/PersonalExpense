package al.bruno.calendar.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class CustomArrayAdapter <T, VM extends ViewDataBinding> extends RecyclerView.Adapter<CustomViewHolder<T, VM>> {
    private T[] t;
    private int resources;
    private BindingInterface<T, VM> bindingInterface;

    public CustomArrayAdapter(T[] t, int resources, BindingInterface<T, VM> bindingInterface) {
        this.t = t;
        this.resources = resources;
        this.bindingInterface = bindingInterface;
    }
    @NonNull
    @Override
    public CustomViewHolder<T, VM> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder<>(LayoutInflater.from(parent.getContext()).inflate(resources, parent, false), bindingInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder<T, VM> holder, int position) {
        holder.bindData(t[position]);
    }

    @Override
    public int getItemCount() {
        return t == null ? 0 : t.length;
    }
}
