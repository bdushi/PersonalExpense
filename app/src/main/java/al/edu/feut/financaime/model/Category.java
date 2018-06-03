package al.edu.feut.financaime.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Category implements Parcelable {
    private long id;
    private String category;
    //ignore
    private String operation;
    //ignore
    private boolean loadingLeft;
    //ignore
    private boolean loadingRight;

    public Category() {
    }

    protected Category(Parcel in)
    {
        id = in.readLong();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
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

    public void setCategory(String category)
    {
        this.category = category;
    }

    @Override
    public String toString()
    {
        return category;
    }

    public boolean isLoadingLeft()
    {
        return loadingLeft;
    }

    public void setLoadingLeft(boolean loadingLeft) {
        this.loadingLeft = loadingLeft;
    }

    public boolean isLoadingRight()
    {
        return loadingRight;
    }

    public void setLoadingRight(boolean loadingRight) {
        this.loadingRight = loadingRight;
    }

    public boolean isLoading()
    {
        return loadingRight || loadingLeft;
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    //DDL

    public static abstract class CategoryTable implements BaseColumns{
        public static final String CATEGORY_TABLE = "category";

        public static final String CATEGORY= "_category";

        public static String CREATE_CATEGORY_TABLE= "CREATE TABLE " + CATEGORY_TABLE + "(" + _ID + " INTEGER PRIMARY KEY AUTO INCREMENT NOT NULL, "
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
         category.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));

         return category;
        }
    }
}
