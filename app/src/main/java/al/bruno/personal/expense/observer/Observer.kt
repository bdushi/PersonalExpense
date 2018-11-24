package al.bruno.personal.expense.observer

interface Observer<T> {
    fun update(l: T);
    fun remove(l: T);
    fun add(l: T)
}