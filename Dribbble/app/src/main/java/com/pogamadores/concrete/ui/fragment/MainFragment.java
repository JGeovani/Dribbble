package com.pogamadores.concrete.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.pogamadores.concrete.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener {

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ShortPagerAdapter adapter = new ShortPagerAdapter(getChildFragmentManager());

        ViewPager mViewPager = ((ViewPager) rootView.findViewById(R.id.viewPager));
        mViewPager.setAdapter(adapter);
        mViewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics()));

        PagerSlidingTabStrip tabs = ((PagerSlidingTabStrip) rootView.findViewById(R.id.tabs));
        tabs.setViewPager(mViewPager);
        tabs.setOnPageChangeListener(this);

        return rootView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageSelected(int position) {}
    @Override
    public void onPageScrollStateChanged(int state) {}


    public class ShortPagerAdapter extends FragmentPagerAdapter {

        private String[] TITLES;
        private final String[] NODES;

        public ShortPagerAdapter(FragmentManager manager) {
            super(manager);
            TITLES = getResources().getStringArray(R.array.categories);
            NODES = getResources().getStringArray(R.array.nodes);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            return ShortsFragment.newInstance(NODES[position]);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

}
