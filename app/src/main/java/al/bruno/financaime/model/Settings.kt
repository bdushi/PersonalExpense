package al.bruno.financaime.model

import androidx.room.ColumnInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
class Settings() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_budget")
    var budget: Double = 0.toDouble()
    @ColumnInfo(name = "_incomes")
    var incomes: Double = 0.toDouble()
    @ColumnInfo(name = "_auto")
    var auto: Boolean = false

    /*constructor() : this(0, 0.0, 0.0, false)
    constructor(id: Long, budget: Double, incomes: Double, auto: Boolean) {
        this.id = id;
        this.budget = budget;
        this.incomes = incomes;
        this.auto = auto;
    }*/
}
