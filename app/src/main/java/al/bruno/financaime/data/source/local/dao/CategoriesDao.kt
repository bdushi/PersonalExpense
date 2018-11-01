package al.bruno.financaime.data.source.local.dao

import al.bruno.financaime.model.Categories
import androidx.room.*
import io.reactivex.Single

@Dao
interface CategoriesDao {
    @Insert
    fun insert(categories: Categories) : Single<Long>
    @Update
    fun update(categories: Categories) : Single<Int>
    @Delete
    fun delete(categories: Categories) : Single<Int>
    @Query("SELECT * FROM categories")
    fun categories() : Single<List<Categories>>
}