package al.bruno.personal.expense.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.personal.expense.R
import al.bruno.personal.expense.callback.*
import al.bruno.personal.expense.databinding.CategoriesEditDialogBinding
import al.bruno.personal.expense.model.Categories
import android.os.Parcelable
import android.text.TextUtils
import androidx.databinding.DataBindingUtil

class EditCategoriesDialog : DialogFragment() {
    private var categories = Categories()
    private var categoriesList: List<Categories> = ArrayList()
    private var onEditListeners: OnEditListeners<Categories>? = null

    class Builder {
        private var hint: Int = 0
        private var title: Int = 0
        private var categories: Categories? = null
        private var categoriesList: List<Categories> = ArrayList()

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

        fun setCategoriesList(categoriesList: List<Categories>): EditCategoriesDialog.Builder {
            this.categoriesList = categoriesList;
            return this
        }

        fun build(): EditCategoriesDialog {
            return newInstance(hint, title, categories, categoriesList)
        }
    }

    private companion object {
        private fun newInstance(hint: Int, title: Int, categories: Categories?, categoriesList: List<Categories>?): EditCategoriesDialog {
            val args = Bundle()
            args.putInt("HINT", hint)
            args.putInt("TITLE", title)
            args.putParcelable("CATEGORY", categories);
            args.putParcelableArrayList("CATEGORIES", categoriesList as (ArrayList<out Parcelable>));
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
        categoriesList = arguments?.getParcelableArrayList("CATEGORIES")!!
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
        /*categories.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                for (c: Categories  in categoriesList) {
                    if (TextUtils.equals(categories.category?.toLowerCase(), c.category.toString())) {
                        categoriesEditDialogBinding.editCategoriesInputLayout.error = "Error"
                        categoriesEditDialogBinding.editCategoriesSave.isEnabled = false
                    } else {
                        categoriesEditDialogBinding.editCategoriesInputLayout.isErrorEnabled = false
                        categoriesEditDialogBinding.editCategoriesSave.isEnabled = true
                    }
                }
            }
        })*/
        categoriesEditDialogBinding.onTextChangedListener = object : OnTextChangedListener {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                for (c: Categories  in categoriesList) {
                    if ((TextUtils.equals(s.toString().toLowerCase(), c.category!!.toLowerCase()))) {
                        categoriesEditDialogBinding.editCategoriesInputLayout.error = "This Categories Exist"
                        categoriesEditDialogBinding.enable = true
                        break
                    } else {
                        categoriesEditDialogBinding.editCategoriesInputLayout.isErrorEnabled = false
                        categoriesEditDialogBinding.enable = false
                    }
                }
            }
        }
        return categoriesEditDialogBinding.root
    }
}
