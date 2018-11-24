package al.bruno.personal.expense.callback

interface OnItemSwipeSelectListener<T> {
    fun onItemSwipedLeft(t: T)
    fun onItemSwipedRight(t: T)
}