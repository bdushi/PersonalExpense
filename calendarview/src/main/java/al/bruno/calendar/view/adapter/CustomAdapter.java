package al.bruno.calendar.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter<T, VM extends ViewDataBinding> extends RecyclerView.Adapter<CustomViewHolder<T, VM>> {
    private List<T> t;
    private List<T> o;
    private int resources;
    private BindingInterface<T, VM> bindingInterface;

    public CustomAdapter(List<T> t, int resources, BindingInterface<T, VM> bindingInterface) {
        this.t = t;
        this.o = t;
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
        holder.bindData(t.get(position));
    }

    @Override
    public int getItemCount() {
        return t == null ? 0 : t.size();
    }

    public List<T> getItems() {
        return t;
    }

    public T getItem(int position) {
        return o.get(o.indexOf(t.get(position)));
    }

    public T getItem(T t) {
        return o.get(o.indexOf(t));
    }

    public int index(int position)
    {
        return o.indexOf(t.get(position));
    }
    public int index(T t1) {
        return o.indexOf(t1);
    }

    public void remove(T t1) {
        t.remove(t1);
        notifyItemRemoved(index(t1));
    }

    public void remove(int position) {
        t.remove(position);
        notifyItemRemoved(position);
    }

    public void notifyItemChanged(T t1) {
        notifyItemChanged(index(t1), t1);
    }

    public void update(T t1) {
        t.set(index(t1), t1);
        notifyItemChanged(index(t1), t1);
    }

    public void add(T t1){
        t.add(t1);
        //o.add(t1);
        notifyItemInserted(index(t1));
    }

    public void addAll(List<T> t1){
        t = t1;
        o = t1;
        notifyDataSetChanged();
    }
}
