package al.bruno.personal.expense.work.manager

import al.bruno.personal.expense.model.Categories
import al.bruno.personal.expense.model.Expense

data class PushExpense (val expenses: List<Expense>, val categories: List<Categories>) {
    constructor() : this(expenses = emptyList(), categories = emptyList())
}