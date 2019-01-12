package al.bruno.personal.expense.observer

interface ExpenseObserver<T, L> {
    fun update(t: T, l: L)
}