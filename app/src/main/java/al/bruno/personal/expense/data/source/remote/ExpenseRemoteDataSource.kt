package al.bruno.personal.expense.data.source.remote

import al.bruno.personal.expense.data.source.local.dao.ExpenseDao
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class ExpenseRemoteDataSource@Inject constructor(private val expenseDao: DatabaseReference)  {
}