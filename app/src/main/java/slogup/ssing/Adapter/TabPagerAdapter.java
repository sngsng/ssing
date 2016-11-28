package slogup.ssing.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by sngjoong on 16. 7. 6..
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> mFragments = new ArrayList<>();
    ArrayList<String> mTitles = new ArrayList<>();

    public void setFragments(ArrayList<Fragment> fragments) {
        mFragments = fragments;
    }

    public void setTitles(ArrayList<String> titles) {
        mTitles = titles;
    }

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTitles.get(position);
    }



    @Override
    public int getCount() {

        return mFragments.size();
    }

}