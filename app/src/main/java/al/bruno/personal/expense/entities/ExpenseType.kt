package al.bruno.personal.expense.entities

import al.bruno.adapter.Selection

class ExpenseType(val key:String, val type:String, var selected: Boolean) : Selection {
    override fun selection(selectio: Boolean) {
        selected = selectio
    }
}
