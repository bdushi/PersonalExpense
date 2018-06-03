package al.edu.feut.financaime.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import al.edu.feut.financaime.R;
import al.edu.feut.financaime.model.Category;
import al.edu.feut.financaime.model.Database;

public class EditCategory extends DialogFragment implements View.OnClickListener, TextWatcher
{
    private Category category;
    private AppCompatEditText categoryEdit;
    private Database appDatabase;
    private TextInputLayout categoryTextInputLayout;

    private OnDismissListener onDismissListener;

    public EditCategory onDismiss(OnDismissListener onDismissListener)
    {
        this.onDismissListener = onDismissListener;
        return this;
    }

    private DialogInterface.OnCancelListener onCancelListener;

    public EditCategory OnCancel(DialogInterface.OnCancelListener onCancelListener)
    {
        this.onCancelListener = onCancelListener;
        return this;
    }

    /*private DialogInterface.OnDismissListener onDismissListener;
    public EditCategory onDismiss(DialogInterface.OnDismissListener onDismissListener)
    {
        this.onDismissListener = onDismissListener;
        return this;
    }*/

    public static class Builder
    {
        private CharSequence hint;
        private CharSequence title;
        private Category category;

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

        public EditCategory.Builder setCategory(Category category)
        {
            this.category = category;
            return this;
        }

        public EditCategory build()
        {
            return newInstance(hint, title, category);
        }
    }

    public static EditCategory newInstance(CharSequence hint, CharSequence title, Category category)
    {
        Bundle args = new Bundle();
        args.putCharSequence("HINT", hint);
        args.putCharSequence("TITLE", title);
        args.putParcelable("CATEGORY", category);
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.category_edit_dialog, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        AppCompatTextView categoryTitle = (AppCompatTextView) view.findViewById(R.id.category_title);
        categoryTextInputLayout = (TextInputLayout) view.findViewById(R.id.category_text_input_layout);
        categoryEdit = (AppCompatEditText) view.findViewById(R.id.category_edit);
        categoryEdit.addTextChangedListener(this);
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            categoryTextInputLayout.setHint(bundle.getCharSequence("HINT"));
            categoryTitle.setText(bundle.getCharSequence("TITLE"));
            category = bundle.getParcelable("CATEGORY");
            if(category != null)
                categoryEdit.setText(category.getCategory());
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
                if(category == null)
                {
                    if(categoryEdit.getText().toString().isEmpty())
                    {
                        categoryTextInputLayout.setError(getString(R.string.add_value));
                    }
                    else
                    {
                        Category category = new Category();
                        category.setCategory(categoryEdit.getText().toString());
                        /*new InsertCategories(aLong ->
                        {
                            if(aLong != -1)
                            {
                                dismiss();
                            }
                            else
                                categoryTextInputLayout.setError(getString(R.string.fail));
                        }, appDatabase).execute(category);*/
                    }
                }
                else
                {
                    if(categoryEdit.getText().toString().isEmpty())
                    {
                        categoryTextInputLayout.setError(getString(R.string.add_value));
                    }
                    else
                    {
                        category.setCategory(categoryEdit.getText().toString());
                        /*new EditCategories(aInteger ->
                        {
                            if(aInteger != -1)
                            {
                                dismiss();
                            }
                            else
                                categoryTextInputLayout.setError(getString(R.string.fail));
                        }, appDatabase).execute(category);*/
                    }
                }
                dismiss();
                break;
            case R.id.category_cancel:
                if(onDismissListener != null)
                    onDismissListener.OnDismiss();
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


    /*@Override
    public void onDismiss(DialogInterface my.finances.dialog)
    {
        super.onDismiss(my.finances.dialog);
        if (onDismissListener != null)
            onDismissListener.onDismiss(my.finances.dialog);
    }*/

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        if (onDismissListener != null)
            onCancelListener.onCancel(dialog);
    }

    public interface OnDismissListener
    {
        void OnDismiss();
    }
}
