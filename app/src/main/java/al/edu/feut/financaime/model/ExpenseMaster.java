package al.edu.feut.financaime.model;
import android.database.Cursor;

import java.util.List;

import al.edu.feut.financaime.util.Utilities;

import static al.edu.feut.financaime.model.Expense.ExpenseTable.DATE;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.EXPENSE;
import static al.edu.feut.financaime.model.Expense.ExpenseTable.EXPENSE_NAME;
import static al.edu.feut.financaime.util.Utilities.format;

//ignore
public class ExpenseMaster {
    private String total;
    private List<Expense> expenses;

    public ExpenseMaster(List<Expense> expenses) {
        this.expenses = expenses;
    }
    public ExpenseMaster() {
    }
    public ExpenseMaster(Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            setTotal(format(cursor.getDouble(cursor.getColumnIndex("total"))));
            while (!cursor.isAfterLast()) {
                expenses.add(new Expense(
                        cursor.getString(cursor.getColumnIndex(EXPENSE_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(EXPENSE)),
                        Utilities.fromTimestamp(cursor.getLong(cursor.getColumnIndex(DATE))))
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
}
