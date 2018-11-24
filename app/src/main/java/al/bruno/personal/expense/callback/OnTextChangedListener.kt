package al.bruno.personal.expense.callback

interface OnTextChangedListener {
    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
}
