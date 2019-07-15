package al.bruno.personal.expense.ui

import al.bruno.month.view.Month
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MyRxBus {
    private var subject:PublishSubject<Month> = PublishSubject.create<Month>()

    fun setMonth(month: Month) {
        subject.onNext(month)
    }

    fun getEvents(): Observable<Month> {
        return subject
    }
}