package al.bruno.financaime.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.R
import al.bruno.financaime.callback.OnClick
import al.bruno.financaime.callback.OnClickListener
import al.bruno.financaime.callback.OnEditListener
import al.bruno.financaime.databinding.CategoriesEditDialogBinding
import al.bruno.financaime.model.Categories
import androidx.databinding.DataBindingUtil

class EditCategoriesDialog : DialogFragment(), TextWatcher {
    private var categories: Categories? = Categories()
    private var onEditListener: OnEditListener<Categories>? = null

    class Builder {
        private var hint: Int = 0
        private var title: Int = 0
        private var categories: Categories? = null

        fun setHint(hint: Int): EditCategoriesDialog.Builder {
            this.hint = hint
            return this
        }

        fun setTitle(title: Int): EditCategoriesDialog.Builder {
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
        private fun newInstance(hint: Int, title: Int, categories: Categories?): EditCategoriesDialog {
            val args = Bundle()
            args.putInt("HINT", hint)
            args.putInt("TITLE", title)
            args.putParcelable("CATEGORY", categories);
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
        val categoriesEditDialogBinding: CategoriesEditDialogBinding =
                DataBindingUtil.inflate(inflater, R.layout.categories_edit_dialog, container, false)
        categoriesEditDialogBinding.categories = categories
        categoriesEditDialogBinding.onClick = object : OnClick{
            override fun onClick() {
                dismiss()
            }
        }
        categoriesEditDialogBinding.onClickListener = object : OnClickListener<Categories> {
            override fun onClick(t: Categories) {
                onEditListener!!.onEdit(t)
                dismiss()
            }
        }
        return categoriesEditDialogBinding.root
    }
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun afterTextChanged(editable: Editable) {
    }
}
