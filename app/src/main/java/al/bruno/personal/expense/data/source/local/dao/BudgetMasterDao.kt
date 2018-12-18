package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.BudgetMaster
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface BudgetMasterDao {
    //(b._budget - (SELECT TOTAL(e._amount) FROM expense AS e WHERE e._id_budget = b._id))
    //b._id AS _id, b._budget AS _budget, b._incomes AS _incomes, b._date AS _date, b._budget = e._amount AS _amount, e._expense, e._id_budget AS _id_budget
    //@Query("SELECT * FROM incomes AS b LEFT JOIN expense AS e ON b._id = e._id_budget WHERE strftime('%m', datetime(b._date/1000, 'unixepoch')) = :month GROUP BY e._id_budget")
    //@Query("SELECT b._id AS _id, b._budget AS _budget, b._incomes AS _incomes, b._date AS _date, TOTAL(e._amount) AS _amount, e._expense AS _expense, e._id_budget AS _id_budget, e._id AS _id, e._date AS _date FROM incomes AS b JOIN expense AS e ON b._id = e._id_budget WHERE strftime('%m', datetime(b._date/1000, 'unixepoch')) = :month GROUP BY e._id_budget")
    //@Query("SELECT b.* FROM incomes AS b JOIN expense AS e ON b._id = e._id_budget WHERE strftime('%m', datetime(b._date/1000, 'unixepoch')) = :month")
    @Query("SELECT i._id AS _id, i._incomes -  TOTAL(e._amount) AS _budget, i._incomes AS _incomes, i._date AS _date, TOTAL(e._amount) AS _amount FROM incomes AS i LEFT JOIN expense AS e ON i._id = e._id_budget WHERE strftime('%m', datetime(i._date/1000, 'unixepoch')) = :month GROUP BY e._id_budget")
    fun budget(month: String) : LiveData<BudgetMaster>
}