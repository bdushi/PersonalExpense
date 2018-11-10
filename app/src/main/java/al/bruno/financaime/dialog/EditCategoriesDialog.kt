package al.bruno.financaime.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.R
import al.bruno.financaime.callback.*
import al.bruno.financaime.databinding.CategoriesEditDialogBinding
import al.bruno.financaime.model.Categories
import androidx.databinding.DataBindingUtil

class EditCategoriesDialog : DialogFragment() {
    private var categories = Categories()
    private var onEditListeners: OnEditListeners<Categories>? = null

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

    fun onCategoriesEditListener(onEditListeners: OnEditListeners<Categories>): EditCategoriesDialog {
        this.onEditListeners = onEditListeners
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val categoriesEditDialogBinding: CategoriesEditDialogBinding =
                DataBindingUtil.inflate(inflater, R.layout.categories_edit_dialog, container, false)
        categoriesEditDialogBinding.categories = arguments?.getParcelable("CATEGORY") ?: categories
        categoriesEditDialogBinding.hint = getString(arguments!!.getInt("HINT"))
        categoriesEditDialogBinding.title = getString(arguments!!.getInt("TITLE"))
        categoriesEditDialogBinding.onEditListeners = object : OnEditListeners<Categories> {
            override fun onEdit(t: Categories) {
                onEditListeners!!.onEdit(t)
                dismiss()
            }
            override fun onDismiss(t: Categories) {
                onEditListeners!!.onDismiss(t)
                dismiss()
            }


        }
        categoriesEditDialogBinding.onTextChangedListener = object : OnTextChangedListener {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.contains("bruno")) {
                    categoriesEditDialogBinding.editCategoriesInputLayout.error = "Error"
                } else {
                    categoriesEditDialogBinding.editCategoriesInputLayout.isErrorEnabled = false
                }
            }
        }
        return categoriesEditDialogBinding.root
    }
}
