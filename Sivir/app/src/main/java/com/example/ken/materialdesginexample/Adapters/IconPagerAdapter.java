package com.example.ken.materialdesginexample.Adapters;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.ken.materialdesginexample.R;

/**
 * Created by Ken on 08/03/2015.
 */
public class IconPagerAdapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    private final int[] ICONS = {R.drawable.noticiason, R.drawable.mapaon, R.drawable.categoriason};
    private Activity activity;

    public IconPagerAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getPageIconResId(int i) {
        return ICONS[i];
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // looks a little bit messy here
        TextView v = new TextView(activity);
        v.setBackgroundResource(R.color.white);
        v.setText("PAGE " + (position + 1));
        final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, activity.getResources()
                .getDisplayMetrics());
        v.setPadding(padding, padding, padding, padding);
        v.setGravity(Gravity.CENTER);
        container.addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View v, Object o) {
        return v == ((View) o);
    }
}
