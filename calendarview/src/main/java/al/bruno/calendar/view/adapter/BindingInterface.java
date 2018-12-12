package al.bruno.calendar.view.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

/**
 * Created by bruno on 6/23/2017.
 */

public interface BindingInterface<T, VM extends ViewDataBinding> {
    void bindData(@NonNull T t, @NonNull VM vm);
}