package al.bruno.calendar.view.adapter;
import al.bruno.calendar.view.fragment.MonthFragment;
import al.bruno.calendar.view.listener.NavigationListener;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MonthPagerAdapter extends FragmentStatePagerAdapter {
    @Nullable private NavigationListener<MonthFragment, Integer> navigationListener;
    private int count;
    public MonthPagerAdapter(FragmentManager fm, int count, @Nullable NavigationListener<MonthFragment, Integer> navigationListener) {
        super(fm);
        this.navigationListener = navigationListener;
        this.count = count;
    }

    @Override
    public MonthFragment getItem(int position) {
        return navigationListener.update(position);
    }

    @Override
    public int getCount() {
        return count;
    }
}
