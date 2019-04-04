package al.bruno.personal.expense.sync

data class SyncService (val expenses: List<Expense>, val categories: List<Categories>) {
    constructor() : this(expenses = emptyList(), categories = emptyList())
}