package al.bruno.personal.expense.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelProviderFactory<V>(v: V) : ViewModelProvider.Factory {
    //private val v:ExpenseDetailsDataSource = t
    private val v:V
    init {
        this.v = v
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return v as T;
    }
}