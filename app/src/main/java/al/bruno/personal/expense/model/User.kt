package al.bruno.personal.expense.model

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "user", indices = arrayOf(Index(value = arrayOf("_username") , unique = true)))
class User() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_username")
    var username: String? = null
    @ColumnInfo(name = "_password")
    var password: String? = null
    @ColumnInfo(name = "_first_name")
    var firstName: String? = null
    @ColumnInfo(name = "_last_name")
    var lastName: String? = null
    @ColumnInfo(name = "_api_key")
    var apiKey: String? = null
    @ColumnInfo(name = "_date")
    var date: Date? = null;

    /*constructor() : this(0, "", "", "", "", "", Date())
    constructor(id: Long, username: String, password: String, firstName: String, lastName: String, apiKey: String, date: Date) {
        this.id = id
        this.username = username
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
        this.apiKey = apiKey
        this.date = date
    }*/
}
