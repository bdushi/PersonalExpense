package al.bruno.calendar.view.binding.utils;

import al.bruno.calendar.view.adapter.MonthAdapter;
import al.bruno.calendar.view.adapter.MonthPagerAdapter;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

public class Adapter {
    @BindingAdapter(value = {"bind:adapter", "bind:listener"}, requireAll = false)
    public static void loadPager(ViewPager viewPager, MonthPagerAdapter monthPagerAdapter, ViewPager.OnPageChangeListener listener) {
        final int PREFILLED_MONTHS = 251;
        viewPager.setAdapter(monthPagerAdapter);
        viewPager.setCurrentItem(PREFILLED_MONTHS / 2);
        viewPager.addOnPageChangeListener(listener);
    }

    @BindingAdapter(value = {"bind:adapter", "bind:listener"}, requireAll = false)
    public static void loadPager(ViewPager viewPager, MonthAdapter monthAdapter, ViewPager.OnPageChangeListener listener) {
        final int PREFILLED_MONTHS = 251;
        viewPager.setAdapter(monthAdapter);
        viewPager.setCurrentItem(PREFILLED_MONTHS / 2);
        viewPager.addOnPageChangeListener(listener);
    }
}
