package al.bruno.financaime.callback

interface OnItemClickListener<T> {
    fun onItemClick(t: T)
    fun onLongItemClick(t: T): Boolean
}