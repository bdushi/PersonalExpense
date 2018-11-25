package al.bruno.personal.expense.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded

class BudgetMaster() : Parcelable {
    @Embedded
    var budget: Budget? = null
    //@Relation(entity = Expense::class, parentColumn = "_id", entityColumn = "_id_budget")
    //@Embedded(prefix = "_expense")
    //var expense: List<Expense>? = null
    @ColumnInfo(name = "_amount")
    var amount:Double = 0.0

    constructor(parcel: Parcel) : this() {
        budget = parcel.readParcelable(Budget::class.java.classLoader)
        amount = parcel.readDouble()
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(budget, flags)
        parcel.writeDouble(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BudgetMaster> {
        override fun createFromParcel(parcel: Parcel): BudgetMaster {
            return BudgetMaster(parcel)
        }

        override fun newArray(size: Int): Array<BudgetMaster?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$budget.toString()$amount"
    }
}