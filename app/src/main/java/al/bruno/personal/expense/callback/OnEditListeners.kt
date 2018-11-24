package al.bruno.personal.expense.callback

interface OnEditListeners<T> {
    fun onEdit(t: T)
    fun onDismiss(t: T)
}