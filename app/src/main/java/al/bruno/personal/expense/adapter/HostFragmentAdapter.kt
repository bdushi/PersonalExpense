package al.bruno.personal.expense.adapter

import al.bruno.personal.expense.CategoriesFragment
import al.bruno.personal.expense.IncomesFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HostFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return CategoriesFragment()
            1 -> return IncomesFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
