package al.bruno.financaime.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import al.bruno.financaime.model.Budget;
import al.bruno.financaime.model.BudgetMaster;
import al.bruno.financaime.model.Category;
import al.bruno.financaime.model.Expense;
import al.bruno.financaime.model.ExpenseMaster;
import al.bruno.financaime.model.Settings;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "financa.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Budget.BudgetTable.Companion.getCREATE_BUDGET_TABLE());
        db.execSQL(Category.CategoryTable.Companion.getCREATE_CATEGORY_TABLE());
        db.execSQL(Expense.ExpenseTable.Companion.getCREATE_EXPENSE_TABLE());
        db.execSQL(Settings.SettingTable.Companion.getCREATE_SETTINGS_TABLE());
        //
        db.insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(), null, Category.CategoryTable.Companion.contentCategory(new Category("Pazar")));
        db.insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(), null, Category.CategoryTable.Companion.contentCategory(new Category("Transport")));
        db.insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(), null, Category.CategoryTable.Companion.contentCategory(new Category("Ushqim")));
        db.insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(), null, Category.CategoryTable.Companion.contentCategory(new Category("Qera")));
        db.insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(), null, Category.CategoryTable.Companion.contentCategory(new Category("Shkola")));
        db.insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(), null, Category.CategoryTable.Companion.contentCategory(new Category("Pushimet")));
        db.insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(), null, Category.CategoryTable.Companion.contentCategory(new Category("Kohe e lire")));
        db.insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(), null, Category.CategoryTable.Companion.contentCategory(new Category("Te tjera")));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertBudget(Budget budget) {
        return getWritableDatabase().insert(Budget.BudgetTable.Companion.getBUDGET_TABLE(), null, Budget.BudgetTable.Companion.contentBudget(budget));
    }

    public int updateBudgetValue(Budget budget) {
        return getWritableDatabase().update(Budget.BudgetTable.Companion.getBUDGET_TABLE(), Budget.BudgetTable.Companion.updateContentBudgetValue(budget), "_id =?", new String[]{String.valueOf(budget.getId())});
    }

    public int updateIncomesValue(Budget budget) {
        return getWritableDatabase().update(Budget.BudgetTable.Companion.getBUDGET_TABLE(), Budget.BudgetTable.Companion.updateContentIncomesValue(budget), "_id =?", new String[]{String.valueOf(budget.getId())});
    }

    public Budget budget (String month) {
        Budget budget = null;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT b._id AS _id, b._budget AS _budget, b._incomes AS _incomes, b._date AS _date , SUM(e._expense) AS _expense " +
                "FROM budget AS b LEFT JOIN expense AS e ON b._id = e._id_budget " +
                "WHERE strftime('%m', datetime(b._date/1000, 'unixepoch')) = ? " +
                "GROUP BY e._id_budget", new String[]{month});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            budget = Budget.BudgetTable.Companion.budgetCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return budget;
    }

    //DML

    public long insertExpense(Expense expense) {
        return getWritableDatabase().insert(Expense.ExpenseTable.Companion.getEXPENSE_TABLE(),null, Expense.ExpenseTable.Companion.contentExpense(expense));
    }

    public Expense expense (long id) {
        Expense expense = null;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM expense WHERE _id =? ", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            expense = Expense.ExpenseTable.Companion.expenseCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return expense;
    }

    public List<Expense> expense (String month) {
        List<Expense> expenses = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT _id, _expense_name, SUM(_expense) AS _expense, _date FROM expense WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = ? GROUP BY TRIM(_expense_name)", new String[]{month});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            expenses.add(Expense.ExpenseTable.Companion.expenseCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return expenses;
    }

    public List<Expense> expense(String month, String year) {
        List<Expense> expenses = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM expense " +
                "WHERE strftime('%m',datetime(_date/1000, 'unixepoch')) = ? " +
                "AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = ? " +
                "GROUP BY TRIM(_expense_name)", new String[]{month, year});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            expenses.add(Expense.ExpenseTable.Companion.expenseCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return expenses;
    }

    public List<Date> date() {
        List<Date> dates = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT _date FROM expense", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dates.add(new Date(cursor.getLong(cursor.getColumnIndex(Expense.ExpenseTable.Companion.getDATE()))));
            cursor.moveToNext();
        }
        cursor.close();
        return dates;
    }

    public ExpenseMaster expenseMaster(String date, String month, String year) {
        Cursor cursor = getReadableDatabase()
                .rawQuery("SELECT " +
                        "_id, " +
                        "_expense_name, " +
                        "_expense, " +
                        "_date, " +
                        "(SELECT sum(_expense) FROM expense WHERE strftime('%d', datetime(_date/1000, 'unixepoch')) = ? " +
                            "AND strftime('%m', datetime(_date/1000, 'unixepoch')) = ? " +
                            "AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = ?) AS _total " +
                        "FROM expense " +
                        "WHERE strftime('%d', datetime(_date/1000, 'unixepoch')) = ? AND strftime('%m', datetime(_date/1000, 'unixepoch')) = ? AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = ? " +
                        "ORDER BY _expense_name ASC", new String[]{date, month, year, date, month, year});
        return new ExpenseMaster(cursor);
    }

    public ExpenseMaster expenseMaster(long date) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT " +
                        "_id, " +
                        "_expense_name, " +
                        "_expense, " +
                        "_date, " +
                        "(SELECT sum(_expense) FROM expense WHERE _date = ?) AS _total " +
                        "FROM expense WHERE _date = ? ORDER BY _expense_name ASC", new String[]{String.valueOf(date), String.valueOf(date)});
        return new ExpenseMaster(cursor);
    }

    public BudgetMaster budgetMaster(String month, String year) {
        Cursor cursor = getReadableDatabase()

                .rawQuery("SELECT b._budget AS _budget, b._incomes AS _incomes, SUM(e._expense) AS _expense, b._incomes - SUM(e._expense) AS _balance " +
                        "FROM budget AS b LEFT JOIN expense AS e ON b._id = e._id_budget " +
                        "WHERE strftime('%m',datetime(b._date/1000, 'unixepoch')) = ? " +
                        "AND strftime('%Y', datetime(b._date/1000, 'unixepoch')) = ? " +
                        "GROUP BY e._id_budget", new String[]{month, year});
        return new BudgetMaster(cursor);
    }

    public long insertCategory(Category category) {
        return getWritableDatabase().insert(Category.CategoryTable.Companion.getCATEGORY_TABLE(),null, Category.CategoryTable.Companion.contentCategory(category));
    }

    public int updateCategory(Category category){
        return getWritableDatabase().update(Category.CategoryTable.Companion.getCATEGORY_TABLE(), Category.CategoryTable.Companion.contentCategory(category),"_id=?",new String[]{String.valueOf(category.getId())});
    }

    public int deleteCategory(Category category){
        return getWritableDatabase().delete(Category.CategoryTable.Companion.getCATEGORY_TABLE(), "_id=?", new String[]{String.valueOf(category.getId())});
    }

    public List<Category> categories () {
        List<Category> categories = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM category", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categories.add(Category.CategoryTable.Companion.categoryCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }

    public long insertSettings(Settings settings) {
        return getWritableDatabase().insert(Settings.SettingTable.Companion.getSETTINGS_TABLE(),null, Settings.SettingTable.Companion.contentSettings(settings));
    }
    public long updateBudgetSettings(Settings settings) {
        return getWritableDatabase().update(Settings.SettingTable.Companion.getSETTINGS_TABLE(), Settings.SettingTable.Companion.contentBudgetSettings(settings), null, null);
    }
    public long updateIncomesSettings(Settings settings) {
        return getWritableDatabase().update(Settings.SettingTable.Companion.getSETTINGS_TABLE(), Settings.SettingTable.Companion.contentIncomesSettings(settings), null, null);
    }
    public long updateAutoSettings(Settings settings) {
        return getWritableDatabase().update(Settings.SettingTable.Companion.getSETTINGS_TABLE(), Settings.SettingTable.Companion.contentAutoSettings(settings), null, null);
    }

    public Settings settings () {
        Settings settings = null;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM settings", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            settings = Settings.SettingTable.Companion.settingsCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return settings;
    }
}
