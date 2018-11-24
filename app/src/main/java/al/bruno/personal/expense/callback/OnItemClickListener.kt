package al.bruno.personal.expense.callback

interface OnItemClickListener<T> {
    fun onItemClick(t: T)
    fun onLongItemClick(t: T): Boolean
}