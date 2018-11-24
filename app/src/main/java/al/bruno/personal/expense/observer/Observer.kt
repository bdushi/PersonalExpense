package al.bruno.personal.expense.observer
interface Observer<T> {
    fun update(t: T)
}