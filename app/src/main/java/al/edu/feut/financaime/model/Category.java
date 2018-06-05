package al.edu.feut.financaime.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Category implements Parcelable {
    private long id;
    private String category;
    public Category() {
    }

    public Category(String category) {
        this.category = category;
    }


    protected Category(Parcel in) {
        id = in.readLong();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(category);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>()
    {
        @Override
        public Category createFromParcel(Parcel in)
        {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size)
        {
            return new Category[size];
        }
    };

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCategory()
    {
        return category;
    }

    public Category setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public String toString()
    {
        return category;
    }

    //DDL

    public static abstract class CategoryTable implements BaseColumns{
        public static final String CATEGORY_TABLE = "category";

        public static final String CATEGORY= "_category";

        public static final String CREATE_CATEGORY_TABLE= "CREATE TABLE " + CATEGORY_TABLE + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + CATEGORY + " TEXT" +")";

        public static ContentValues contentCategory(Category category)
        {
            ContentValues contentValues= new ContentValues();
            contentValues.put(CATEGORY, category.getCategory());

            return contentValues;
        }

        public static Category categoryCursor (Cursor cursor)
        {
         Category category= new Category();
         category.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
         category.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));

         return category;
        }
    }
}
