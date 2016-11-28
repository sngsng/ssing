package slogup.ssing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Arrays;
import slogup.ssing.Adapter.TabPagerAdapter;
import slogup.ssing.Fragment.PostListFragment;
import slogup.ssing.View.CustomTabLayout;
import slogup.ssing.View.MainDrawerViewHolder;

import slogup.ssing.R;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private PostListFragment mLatestOrderPostListFragment;
    private PostListFragment mDistanceOrderPostListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpDrawer();
        setUpTabLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpDrawer() {

        MainDrawerViewHolder mainDrawerViewHolder = new MainDrawerViewHolder(this);
        mainDrawerViewHolder.initializeDrawerLayout();
    }

    private void setUpTabLayout() {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabTitles = new ArrayList<>();

        mLatestOrderPostListFragment = PostListFragment.newInstance(PostListFragment.PostListOrderType.Latest);
        mDistanceOrderPostListFragment = PostListFragment.newInstance(PostListFragment.PostListOrderType.Distance);

        fragments.add(mLatestOrderPostListFragment);
        fragments.add(mDistanceOrderPostListFragment);

        String[] titles = getResources().getStringArray(R.array.titles_main_tab);
        tabTitles.addAll(Arrays.asList(titles));

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        tabPagerAdapter.setFragments(fragments);
        tabPagerAdapter.setTitles(tabTitles);

        ViewPager pager = (ViewPager) findViewById(R.id.main_view_pager);
        pager.setAdapter(tabPagerAdapter);

        CustomTabLayout tabLayout = (CustomTabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setBoldTypeFaceEnabled(true);
        tabLayout.setupWithViewPager(pager);

    }
}