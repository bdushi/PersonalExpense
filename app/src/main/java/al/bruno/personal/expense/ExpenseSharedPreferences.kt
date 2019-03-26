package al.bruno.personal.expense

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseSharedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun testEdit(test: String) {
        sharedPreferences.edit().putString("TEST", test).apply()
    }

    fun readTest() : String? {
        return sharedPreferences.getString("TEST", "")
    }


}