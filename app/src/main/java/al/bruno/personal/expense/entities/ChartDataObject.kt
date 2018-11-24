package al.bruno.personal.expense.entities

class ChartDataObject<T, L>(id: T, pieData: L) {
    var id: T = id
        get() {
            return field
        }
    var pieData: L = pieData
        get() {
            return field
        }

}