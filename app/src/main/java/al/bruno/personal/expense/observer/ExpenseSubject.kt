package al.bruno.personal.expense.observer

interface ExpenseSubject<T, L> {
    fun registerObserver(o: ExpenseObserver<T, L>)
    fun removeObserver(o: ExpenseObserver<T, L>)
    fun notifyObserver(t: T, l:L)
}
