package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.Categories
import androidx.room.*
import io.reactivex.Single

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: Categories) : Single<Long>
    @Update
    fun update(categories: Categories) : Single<Int>
    @Delete
    fun delete(categories: Categories) : Single<Int>
    @Query("SELECT * FROM categories WHERE _type = :type")
    fun categories(type: String) : Single<List<Categories>>
}