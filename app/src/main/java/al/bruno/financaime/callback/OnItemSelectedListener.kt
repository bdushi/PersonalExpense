package al.bruno.financaime.callback

import android.view.View
import android.widget.AdapterView

interface OnItemSelectedListener {
    fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
}