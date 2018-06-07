package al.edu.feut.financaime.model;

import android.database.Cursor;

import java.util.Date;

import static al.edu.feut.financaime.model.BudgetMaster.BudgetMasterTable.BALANCE;
import static al.edu.feut.financaime.model.BudgetMaster.BudgetMasterTable.BUDGET;
import static al.edu.feut.financaime.model.BudgetMaster.BudgetMasterTable.EXPENSE;
import static al.edu.feut.financaime.model.BudgetMaster.BudgetMasterTable.INCOMES;

public class BudgetMaster {
    private double budget = 0;
    private double incomes = 0;
    private double expense = 0;
    private double balance = 0;
    private Date date;

    public BudgetMaster(Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            budget = cursor.getDouble(cursor.getColumnIndex(BUDGET));
            incomes = cursor.getDouble(cursor.getColumnIndex(INCOMES));
            expense = cursor.getDouble(cursor.getColumnIndex(EXPENSE));
            balance = cursor.getDouble(cursor.getColumnIndex(BALANCE));
            cursor.moveToNext();
        }
        cursor.close();
    }

    public BudgetMaster() {

    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getIncomes() {
        return incomes;
    }

    public void setIncomes(double incomes) {
        this.incomes = incomes;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public BudgetMaster setDate(Date date) {
        this.date = date;
        return this;
    }

    public static abstract class BudgetMasterTable {
        public static final String BUDGET = "_budget";
        public static final String INCOMES = "_incomes";
        public static final String EXPENSE = "_expense";
        public static final String BALANCE = "_balance";
    }
}
