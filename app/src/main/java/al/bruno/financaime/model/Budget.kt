package al.bruno.financaime.model

import androidx.room.*
import java.util.Date

@Entity(tableName = "budget")
class Budget() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_budget")
    var budget: Double = 0.toDouble()
    @ColumnInfo(name = "_incomes")
    var incomes: Double = 0.toDouble()
    @ColumnInfo(name = "_date")
    var date: Date? = null

    /*constructor() : this(0, 0.0, 0.0, Date(), 0.0)
    constructor(id: Long, budget: Double, incomes: Double, date: Date, value: Double) {
        this.id = id;
        this.budget = budget;
        this.incomes = incomes;
        this.date = date;
        this.value = value
    }*/
}
