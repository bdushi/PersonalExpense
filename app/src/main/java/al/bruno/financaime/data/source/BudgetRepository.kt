package al.bruno.financaime.data.source

class BudgetRepository(budgetDataSource: BudgetDataSource) : BudgetDataSource {
    val budgetDataSource: BudgetDataSource
    init {
        this.budgetDataSource = budgetDataSource
    }

    companion object {
        var INSTANCE: BudgetRepository? = null
        fun getInstance(budgetDataSource: BudgetDataSource) : BudgetRepository? {
            if(INSTANCE == null)
                INSTANCE = BudgetRepository(budgetDataSource)
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}