package al.bruno.personal.expense.dialog

import al.bruno.personal.expense.callback.OnEditListener
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment

import java.util.Calendar

class DatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {
    @Nullable private var timeInMillis = 0.toLong()
    @Nullable
    private var documentCallback: OnEditListener<Long>? = null

    fun setOnCalendarInstance(timeInMillis: Long): al.bruno.personal.expense.dialog.DatePickerDialog {
        this.timeInMillis = timeInMillis
        return this
    }

    fun onEditListener(documentCallback: OnEditListener<Long>): al.bruno.personal.expense.dialog.DatePickerDialog {
        this.documentCallback = documentCallback
        return this
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        return DatePickerDialog(context!!, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        documentCallback!!.onEdit(cal.timeInMillis)
    }
}