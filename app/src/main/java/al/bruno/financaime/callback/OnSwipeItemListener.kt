package al.bruno.financaime.callback

interface OnSwipeItemListener {
    fun onItemSwipedLeft(position: Int)
    fun onItemSwipedRight(position: Int)
}