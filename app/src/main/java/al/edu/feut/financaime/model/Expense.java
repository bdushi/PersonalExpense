package al.edu.feut.financaime.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import java.util.Date;

import al.edu.feut.financaime.util.Utilities;

public class Expense implements Parcelable
{
    private long id;
    private String expenseName;
    private double expense;
    private Date date;

    public Expense() {
    }

    public Expense(String expenseName, double expense, Date date) {
        this.expenseName = expenseName;
        this.expense = expense;
        this.date = date;
    }

    protected Expense(Parcel in) {
        id = in.readLong();
        expenseName = in.readString();
        expense = in.readDouble();
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(expenseName);
        dest.writeDouble(expense);
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public String getExpenseName()
    {
        return expenseName;
    }

    public void setExpenseName(String expenseName)
    {
        this.expenseName = expenseName;
    }

    public double getExpense()
    {
        return expense;
    }

    public String getExpensesStr()
    {
        return Utilities.format(expense);
    }

    public void setExpense(double expense)
    {
        this.expense = expense;
    }

    public long getId()
    {
        return id;
    }

    public String getIdStr()
    {
        return String.valueOf(id);
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
    public Date getDate()
    {
        return date;
    }

    public String getDateStr()
    {
        return Utilities.dateFormat(date);
    }


    public static abstract class ExpenseTable implements BaseColumns {

        //emri i tabeles
        public static final String EXPENSE_TABLE = "expense";

        //emrat e kolonave te tabeles
        public static final String EXPENSE_NAME = "_expense_name";
        public static final String EXPENSE = "_expense";
        public static final String DATE= "_date";


        public static final String CREATE_EXPENSE_TABLE = "CREATE TABLE " + EXPENSE_TABLE + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + EXPENSE_NAME + " TEXT, "
                + EXPENSE + " REAL, "
                + DATE + " REAL "
                + ")";

        public static ContentValues contentExpense(Expense expense) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(EXPENSE_NAME, expense.getExpenseName());
            contentValues.put(EXPENSE, expense.getExpense());
            contentValues.put(DATE, Utilities.dateToTimestamp(expense.getDate()));
            return contentValues;
        }
        public static Expense expenseCursor(Cursor cursor) {
            Expense expense = new Expense();
            expense.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            expense.setExpenseName(cursor.getString(cursor.getColumnIndex(EXPENSE_NAME)));
            expense.setExpense(cursor.getDouble(cursor.getColumnIndex(EXPENSE)));
            expense.setDate(Utilities.fromTimestamp(cursor.getLong(cursor.getColumnIndex(DATE))));
            return expense;
        }

    }

}
