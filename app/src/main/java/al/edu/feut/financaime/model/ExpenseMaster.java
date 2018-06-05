package al.edu.feut.financaime.model;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import al.edu.feut.financaime.util.Utilities;

import static al.edu.feut.financaime.model.Expense.ExpenseTable.DATE;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.EXPENSE;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.EXPENSE_NAME;
import static al.edu.feut.financaime.model.ExpenseMaster.ExpenseMasterTable.TOTAL;
import static al.edu.feut.financaime.util.Utilities.format;

//ignore
public class ExpenseMaster {
    private String total;
    private long id = 1;
    private List<Expense> expenses = new ArrayList<>();

    public ExpenseMaster(List<Expense> expenses) {
        this.expenses = expenses;
    }
    public ExpenseMaster() {
    }
    public ExpenseMaster(Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            setTotal(format(cursor.getDouble(cursor.getColumnIndex(TOTAL))));
            while (!cursor.isAfterLast()) {
                expenses.add(new Expense(
                        cursor.getString(cursor.getColumnIndex(EXPENSE_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(EXPENSE)),
                        Utilities.fromTimestamp(cursor.getLong(cursor.getColumnIndex(DATE))),
                        id++)
                );
                cursor.moveToNext();
            }
            setExpenses(expenses);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public static abstract class ExpenseMasterTable {
        public static final String TOTAL = "_total";
    }
}
