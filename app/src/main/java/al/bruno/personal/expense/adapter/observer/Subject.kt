package al.bruno.personal.expense.adapter.observer

interface Subject<T> {
    fun registerObserver(o: Observer<T>)
    fun removeObserver(o: Observer<T>)
    fun notifyObserverChanged(t: T)
    fun notifyObserverRemove(t: T);
    fun notifyObserverAdd(t: T)
}