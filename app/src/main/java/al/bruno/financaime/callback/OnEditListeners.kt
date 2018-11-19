package al.bruno.financaime.callback

interface OnEditListeners<T> {
    fun onEdit(t: T)
    fun onDismiss(t: T)
}