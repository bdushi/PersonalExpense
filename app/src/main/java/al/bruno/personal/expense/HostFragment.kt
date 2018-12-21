package al.bruno.personal.expense

import al.bruno.personal.expense.adapter.HostFragmentAdapter
import al.bruno.personal.expense.databinding.FragmentHostBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class HostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragment: FragmentHostBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_host, container, false)
        fragment.adapter = HostFragmentAdapter(fragmentManager!!)
        return fragment.root
    }
}
