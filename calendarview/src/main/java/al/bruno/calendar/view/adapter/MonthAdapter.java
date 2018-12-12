package al.bruno.calendar.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

public class MonthAdapter extends PagerAdapter {
    @Nullable private SlideMonth slideImages;
    private int count;

    public MonthAdapter(@Nullable SlideMonth slideImages, int count) {
        this.slideImages = slideImages;
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return slideImages.slide(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface SlideMonth {
        View slide(ViewGroup viewGroup, int position);
    }
}