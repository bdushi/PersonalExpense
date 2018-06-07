package al.edu.feut.financaime.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static al.edu.feut.financaime.model.Budget.BudgetTable.BUDGET_TABLE;
import static al.edu.feut.financaime.model.Budget.BudgetTable.CREATE_BUDGET_TABLE;
import static al.edu.feut.financaime.model.Budget.BudgetTable.budgetCursor;
import static al.edu.feut.financaime.model.Budget.BudgetTable.contentBudget;
import static al.edu.feut.financaime.model.Budget.BudgetTable.updateContentBudgetValue;
import static al.edu.feut.financaime.model.Budget.BudgetTable.updateContentIncomesValue;
import static al.edu.feut.financaime.model.Category.CategoryTable.CATEGORY_TABLE;
import static al.edu.feut.financaime.model.Category.CategoryTable.CREATE_CATEGORY_TABLE;
import static al.edu.feut.financaime.model.Category.CategoryTable.categoryCursor;
import static al.edu.feut.financaime.model.Category.CategoryTable.contentCategory;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.CREATE_EXPENSE_TABLE;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.DATE;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.EXPENSE_TABLE;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.contentExpense;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.expenseCursor;
import static al.edu.feut.financaime.model.Settings.SettingTable.CREATE_SETTINGS_TABLE;
import static al.edu.feut.financaime.model.Settings.SettingTable.INSERT_OR_REPLACE;
import static al.edu.feut.financaime.model.Settings.SettingTable.SETTINGS_TABLE;
import static al.edu.feut.financaime.model.Settings.SettingTable.bindItem;
import static al.edu.feut.financaime.model.Settings.SettingTable.contentAutoSettings;
import static al.edu.feut.financaime.model.Settings.SettingTable.contentBudgetSettings;
import static al.edu.feut.financaime.model.Settings.SettingTable.contentIncomesSettings;
import static al.edu.feut.financaime.model.Settings.SettingTable.contentSettings;
import static al.edu.feut.financaime.model.Settings.SettingTable.settingsCursor;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "financa.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUDGET_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);
        //
        db.insert(CATEGORY_TABLE, null, contentCategory(new Category("Pazar")));
        db.insert(CATEGORY_TABLE, null, contentCategory(new Category("Transport")));
        db.insert(CATEGORY_TABLE, null, contentCategory(new Category("Ushqim")));
        db.insert(CATEGORY_TABLE, null, contentCategory(new Category("Qera")));
        db.insert(CATEGORY_TABLE, null, contentCategory(new Category("Shkola")));
        db.insert(CATEGORY_TABLE, null, contentCategory(new Category("Pushimet")));
        db.insert(CATEGORY_TABLE, null, contentCategory(new Category("Kohe e lire")));
        db.insert(CATEGORY_TABLE, null, contentCategory(new Category("Te tjera")));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertBudget(Budget budget) {
        return getWritableDatabase().insert(BUDGET_TABLE, null, contentBudget(budget));
    }

    public int updateBudgetValue(Budget budget) {
        return getWritableDatabase().update(BUDGET_TABLE, updateContentBudgetValue(budget), "_id =?", new String[]{String.valueOf(budget.getId())});
    }

    public int updateIncomesValue(Budget budget) {
        return getWritableDatabase().update(BUDGET_TABLE, updateContentIncomesValue(budget), "_id =?", new String[]{String.valueOf(budget.getId())});
    }

    public int deleteBudget(Budget budget) {
        return getWritableDatabase().delete(BUDGET_TABLE, "_id =?", new String[]{String.valueOf(budget.getId())});
    }

    public Budget budget (String month) {
        Budget budget = null;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM budget WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = ?", new String[]{month});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            budget = budgetCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return budget;
    }

    //DML

    public long insertExpense(Expense expense) {
        return getWritableDatabase().insert(EXPENSE_TABLE,null,contentExpense(expense));
    }

    public int updateExpense(Expense expense){
        return getWritableDatabase().update(EXPENSE_TABLE,contentExpense(expense),"_id=?",new String[]{String.valueOf(expense.getId())});
    }

    public int deleteExpense(Expense expense){
        return getWritableDatabase().delete(EXPENSE_TABLE, "_id=?", new String[]{String.valueOf(expense.getId())});
    }

    public Expense expense (long id) {
        Expense expense = null;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM expense WHERE _id =? ", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            expense = expenseCursor(cursor);
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
            expenses.add(expenseCursor(cursor));
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
            expenses.add(expenseCursor(cursor));
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
            dates.add(new Date(cursor.getLong(cursor.getColumnIndex(DATE))));
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
                .rawQuery("SELECT b._budget AS _budget, b._incomes AS _incomes, SUM(e._expense) AS _expense, b._budget - SUM(e._expense) AS _balance, b._budget - b._incomes AS _remaining " +
                        "FROM budget AS b  LEFT JOIN expense AS e ON b._id = e._id_budget " +
                        "WHERE strftime('%m',datetime(b._date/1000, 'unixepoch')) = ? " +
                        "AND strftime('%Y', datetime(b._date/1000, 'unixepoch')) = ? " +
                        "GROUP BY e._id_budget", new String[]{month, year});
        return new BudgetMaster(cursor);
    }

    public long insertCategory(Category category) {
        return getWritableDatabase().insert(CATEGORY_TABLE,null,contentCategory(category));
    }

    public int updateCategory(Category category){
        return getWritableDatabase().update(CATEGORY_TABLE,contentCategory(category),"_id=?",new String[]{String.valueOf(category.getId())});
    }

    public int deleteCategory(Category category){
        return getWritableDatabase().delete(CATEGORY_TABLE, "_id=?", new String[]{String.valueOf(category.getId())});
    }

    public List<Category> categories () {
        List<Category> categories = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM category", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categories.add(categoryCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }

    public long insertSettings(Settings settings) {
        return getWritableDatabase().insert(SETTINGS_TABLE,null, contentSettings(settings));
    }
    public long updateBudgetSettings(Settings settings) {
        return getWritableDatabase().update(SETTINGS_TABLE, contentBudgetSettings(settings), null, null);
    }
    public long updateIncomesSettings(Settings settings) {
        return getWritableDatabase().update(SETTINGS_TABLE, contentIncomesSettings(settings), null, null);
    }
    public long updateAutoSettings(Settings settings) {
        return getWritableDatabase().update(SETTINGS_TABLE, contentAutoSettings(settings), null, null);
    }

    public long insertOrReplaceSettings(Settings settings) {
        return bindItem(getWritableDatabase().compileStatement(INSERT_OR_REPLACE), settings);
    }

    public int updateSettings(Settings settings){
        return getWritableDatabase().update(SETTINGS_TABLE,contentSettings(settings),"_id=?",new String[]{String.valueOf(settings.getId())});
    }

    public int deleteSettings(Settings settings){
        return getWritableDatabase().delete(SETTINGS_TABLE, "_id=?", new String[]{String.valueOf(settings.getId())});
    }

    public Settings settings () {
        Settings settings = null;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM settings", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            settings = settingsCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return settings;
    }



}
