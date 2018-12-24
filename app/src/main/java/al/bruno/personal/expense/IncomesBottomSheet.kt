package al.bruno.personal.expense

import al.bruno.personal.expense.databinding.BottomSheetIncomesBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class IncomesBottomSheet: BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bottomSheetIncomesBinding = DataBindingUtil.inflate<BottomSheetIncomesBinding>(inflater, R.layout.bottom_sheet_incomes, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
