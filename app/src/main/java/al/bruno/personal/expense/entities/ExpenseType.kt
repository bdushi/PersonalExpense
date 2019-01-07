package al.bruno.personal.expense.entities

import al.bruno.personal.expense.callback.Selection

class ExpenseType(val type:String, var selected: Boolean) : Selection {
    override fun selection(selectio: Boolean) {
        selected = selectio
    }
}
