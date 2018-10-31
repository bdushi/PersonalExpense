package al.bruno.financaime.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import al.bruno.financaime.R;
import al.bruno.financaime.model.Categories;
import al.bruno.financaime.data.source.local.Database;

public class EditCategory extends DialogFragment implements View.OnClickListener, TextWatcher
{
    private Categories categories;
    private AppCompatEditText categoryEdit;
    private TextInputLayout categoryTextInputLayout;
    private EditCategoryListener editCategoryListener;

    public static class Builder {
        private CharSequence hint;
        private CharSequence title;
        private Categories categories;

        public EditCategory.Builder setHint(CharSequence hint)
        {
            this.hint = hint;
            return this;
        }
        public EditCategory.Builder setTitle(CharSequence title)
        {
            this.title = title;
            return this;
        }

        public EditCategory.Builder setCategories(Categories categories)
        {
            this.categories = categories;
            return this;
        }

        public EditCategory build()
        {
            return newInstance(hint, title, categories);
        }
    }

    public EditCategory OnEditCategoryListener (EditCategoryListener editCategoryListener) {
        this.editCategoryListener = editCategoryListener;
        return this;
    }
    public static EditCategory newInstance(CharSequence hint, CharSequence title, Categories categories)
    {
        Bundle args = new Bundle();
        args.putCharSequence("HINT", hint);
        args.putCharSequence("TITLE", title);
        args.putParcelable("CATEGORY", categories);
        EditCategory fragment = new EditCategory();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.category_edit_dialog, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        AppCompatTextView categoryTitle = view.findViewById(R.id.category_title);
        categoryTextInputLayout = view.findViewById(R.id.category_text_input_layout);
        categoryEdit = view.findViewById(R.id.category_edit);
        categoryEdit.addTextChangedListener(this);
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            categoryTextInputLayout.setHint(bundle.getCharSequence("HINT"));
            categoryTitle.setText(bundle.getCharSequence("TITLE"));
            categories = bundle.getParcelable("CATEGORY");
            if(categories != null)
                categoryEdit.setText(categories.getCategory());
        }

        view.findViewById(R.id.category_save).setOnClickListener(this);
        view.findViewById(R.id.category_cancel).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.category_save:
                if(categories == null) {
                    if(categoryEdit.getText().toString().isEmpty()) {
                        categoryTextInputLayout.setError(getString(R.string.add_value));
                    } else {
                        Categories categories = new Categories();
                        categories.setCategory(categoryEdit.getText().toString());
                        if(new Database(getContext()).insertCategory(categories) != -1) {
                            editCategoryListener.onSave(categories);
                        }
                    }
                } else {
                    if(categoryEdit.getText().toString().isEmpty()) {
                        categoryTextInputLayout.setError(getString(R.string.add_value));
                    } else {
                        categories.setCategory(categoryEdit.getText().toString());
                        if(new Database(getContext()).updateCategory(categories) != 0) {
                            editCategoryListener.onSave(categories);
                        }
                    }
                }
                dismiss();
                break;
            case R.id.category_cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        categoryTextInputLayout.setError(null);
    }

    public interface EditCategoryListener {
        void onSave(Categories categories);
    }
}
