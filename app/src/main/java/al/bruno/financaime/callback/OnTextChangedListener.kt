package al.bruno.financaime.callback

interface OnTextChangedListener {
    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
}
