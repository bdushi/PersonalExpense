package al.bruno.personal.expense.adapter

import al.bruno.personal.expense.PersonalExpensesFragment
import al.bruno.personal.expense.IncomesFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HostFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return PersonalExpensesFragment()
            1 -> return IncomesFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
