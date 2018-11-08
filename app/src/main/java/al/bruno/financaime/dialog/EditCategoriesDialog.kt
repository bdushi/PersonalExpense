package al.bruno.financaime.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.R
import al.bruno.financaime.callback.OnEditListener
import al.bruno.financaime.databinding.CategoriesEditDialogBinding
import al.bruno.financaime.model.Categories
import androidx.databinding.DataBindingUtil

class EditCategoriesDialog : DialogFragment(), TextWatcher {
    private var categories: Categories? = null
    private var onEditListener: OnEditListener<Categories>? = null

    class Builder {
        private var hint: CharSequence? = null
        private var title: CharSequence? = null
        private var categories: Categories? = null

        fun setHint(hint: CharSequence): EditCategoriesDialog.Builder {
            this.hint = hint
            return this
        }

        fun setTitle(title: CharSequence): EditCategoriesDialog.Builder {
            this.title = title
            return this
        }

        fun setCategories(categories: Categories): EditCategoriesDialog.Builder {
            this.categories = categories
            return this
        }

        fun build(): EditCategoriesDialog {
            return newInstance(hint, title, categories)
        }
    }

    private companion object {
        private fun newInstance(hint: CharSequence?, title: CharSequence?, categories: Categories?): EditCategoriesDialog {
            val args = Bundle()
            args.putCharSequence("HINT", hint)
            args.putCharSequence("TITLE", title)
            //args.putParcelable("CATEGORY", categories);
            val fragment = EditCategoriesDialog()
            fragment.arguments = args
            return fragment
        }
    }

    fun OnCategoriesEditListener(onEditListener: OnEditListener<Categories>): EditCategoriesDialog {
        this.onEditListener = onEditListener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // android:text="@={categories.category}"
        val categoriesEditDialogBinding: CategoriesEditDialogBinding =
                DataBindingUtil.inflate(inflater, R.layout.categories_edit_dialog, container, false)
        return categoriesEditDialogBinding.root
    }
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun afterTextChanged(editable: Editable) {
    }
}
