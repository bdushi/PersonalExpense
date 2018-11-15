package al.bruno.financaime.entities

class PieDataObject<T, L>(id: T, pieData: L) {
    var id: T = id
        get() {
            return field
        }
    var pieData: L = pieData
        get() {
            return field
        }

}