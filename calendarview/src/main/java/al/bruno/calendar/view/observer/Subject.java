package al.bruno.calendar.view.observer;

/**
 * Created by 1sd on 3/15/18.
 */

public interface Subject<T> {
    void registerObserver(Observer<T> o);
    void removeObserver(Observer<T> o);
    void notifyObserver(T t);
}
