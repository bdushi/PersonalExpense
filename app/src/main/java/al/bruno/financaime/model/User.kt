package al.bruno.financaime.model

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "user", indices = arrayOf(Index(value = arrayOf("username") , unique = true)))
class User {
    @PrimaryKey
    var id: Long = 0
    var username: String? = null
    var password: String? = null
    @ColumnInfo(name = "first_name")
    var firstName: String? = null
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
    @ColumnInfo(name = "api_key")
    var apiKey: String? = null
    @ColumnInfo(name = "date")
    val date: Date? = null;
}
