package al.bruno.personal.expense.callback

interface OnEditListener<T> {
    fun onEdit(t: T);
}