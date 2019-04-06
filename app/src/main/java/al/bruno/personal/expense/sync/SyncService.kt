package al.bruno.personal.expense.sync

data class SyncService (val expenses: List<Expense>, val categories: List<Categories>) {
    constructor() : this(expenses = emptyList(), categories = emptyList())

    fun expenseConvert(): List<al.bruno.personal.expense.model.Expense> {
        val ex: ArrayList<al.bruno.personal.expense.model.Expense> = ArrayList()
        for (expense in expenses) {
            ex.add(al.bruno.personal.expense.model.Expense(type = expense.type, category = expense.category, memo =  expense.memo, amount =  expense.amount, date =  expense.date, syncTime =  expense.syncTime))
        }
        return ex
    }
    fun categoriesConvert(): List<al.bruno.personal.expense.model.Categories> {
        val ex: ArrayList<al.bruno.personal.expense.model.Categories> = ArrayList()
        for (category in categories) {
            ex.add(al.bruno.personal.expense.model.Categories(category = category.category, type = category.type, syncTime = category.syncTime))
        }
        return ex
    }
}