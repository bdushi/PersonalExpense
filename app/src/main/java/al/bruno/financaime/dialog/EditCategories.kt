package al.bruno.financaime.dialog

import android.os.Bundle
import com.google.android.material.textfield.TextInputLayout
import androidx.fragment.app.DialogFragment
import androidx.appcompat.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.R
import al.bruno.financaime.callback.OnEditListener
import al.bruno.financaime.model.Categories

class EditCategories : DialogFragment(), TextWatcher {
    private var categories: Categories? = null
    private var categoryTextInputLayout: TextInputLayout? = null
    private var onEditListener: OnEditListener<Categories>? = null

    class Builder {
        private var hint: CharSequence? = null
        private var title: CharSequence? = null
        private var categories: Categories? = null

        fun setHint(hint: CharSequence): EditCategories.Builder {
            this.hint = hint
            return this
        }

        fun setTitle(title: CharSequence): EditCategories.Builder {
            this.title = title
            return this
        }

        fun setCategories(categories: Categories): EditCategories.Builder {
            this.categories = categories
            return this
        }

        fun build(): EditCategories {
            return newInstance(hint, title, categories)
        }
    }

    fun OnCategoriesEditListener(onEditListener: OnEditListener<Categories>): EditCategories {
        this.onEditListener = onEditListener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.categories_edit_dialog, container, false)
        val categoryTitle = view.findViewById<AppCompatTextView>(R.id.category_title)
        categoryTextInputLayout = view.findViewById(R.id.category_text_input_layout)
        val bundle = arguments
        if (bundle != null) {
            categoryTextInputLayout!!.hint = bundle.getCharSequence("HINT")
            categoryTitle.text = bundle.getCharSequence("TITLE")
            //categories = bundle.getParcelable<Parcelable>("CATEGORY")
        }
        return view
    }
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun afterTextChanged(editable: Editable) {
        categoryTextInputLayout!!.error = null
    }

    companion object {
        fun newInstance(hint: CharSequence?, title: CharSequence?, categories: Categories?): EditCategories {
            val args = Bundle()
            args.putCharSequence("HINT", hint)
            args.putCharSequence("TITLE", title)
            //args.putParcelable("CATEGORY", categories);
            val fragment = EditCategories()
            fragment.arguments = args
            return fragment
        }
    }
}
