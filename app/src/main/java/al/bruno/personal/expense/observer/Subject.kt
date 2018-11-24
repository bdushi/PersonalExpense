package al.bruno.personal.expense.observer

interface Subject<T> {
    fun registerObserver(o: Observer<T>)
    fun removeObserver(o: Observer<T>)
    fun notifyObserver(t: T)
    fun notifyObserverRemove(t: T);
    fun notifyObserverAdd(t: T)
}