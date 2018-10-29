package al.bruno.financaime.database.dao

import al.bruno.financaime.model.Categories
import androidx.room.*
import io.reactivex.Single

@Dao
interface CategoryDao {
    @Insert
    fun insert(categories: Categories) : Single<Long>
    @Update
    fun update(categories: Categories) : Single<Long>
    @Delete
    fun delete(categories: Categories) : Single<Long>
    @Query("SELECT * FROM categories")
    fun categories() : Single<List<Categories>>
}