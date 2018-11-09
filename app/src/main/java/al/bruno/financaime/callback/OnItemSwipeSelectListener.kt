package al.bruno.financaime.callback

interface OnItemSwipeSelectListener<T> {
    fun onItemSwipedLeft(t: T)
    fun onItemSwipedRight(t: T)
}