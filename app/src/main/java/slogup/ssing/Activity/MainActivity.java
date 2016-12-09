package slogup.ssing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.slogup.sgcore.manager.AccountManager;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import com.slogup.sgcore.network.core.SessionClientHelper;

import java.util.ArrayList;
import java.util.Arrays;

import slogup.ssing.Adapter.TabPagerAdapter;
import slogup.ssing.Fragment.PostListFragment;
import slogup.ssing.Fragment.SignUpDialogFragment;
import slogup.ssing.Util.CommonUtils;
import slogup.ssing.View.CustomTabLayout;
import slogup.ssing.View.MainDrawerViewHolder;

import slogup.ssing.R;

public class MainActivity extends BaseActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static long sBackPressedTime;
    private static final int BACK_PRESS_TIME_DELAY = 2000;

    private PostListFragment mLatestOrderPostListFragment;
    private PostListFragment mMyActivityPostListFragment;
    private MainDrawerViewHolder mMainDrawerViewHolder;
    private SignUpDialogFragment mSignUpDialogFragment;
    private ImageButton mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpDrawer();
        setUpTabLayout();
        setUpSearchButton();

    }


    @Override
    protected void onStart() {
        super.onStart();
        mMainDrawerViewHolder.updateDrawerByLoginState();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainDrawerViewHolder.updateDrawerByLoginState();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mSignUpDialogFragment != null)
            mSignUpDialogFragment.onActivityResult(requestCode, resultCode, data);

        mMainDrawerViewHolder.updateDrawerByLoginState();
    }


    @Override
    public void onBackPressed() {

        if (mMainDrawerViewHolder.isDrawerOpen()) {

            mMainDrawerViewHolder.toggleDrawer();
        } else {
            if (sBackPressedTime + BACK_PRESS_TIME_DELAY > System.currentTimeMillis()) {

                super.onBackPressed();

            } else {
                Toast.makeText(getBaseContext(), getString(R.string.title_ask_app_exit),
                        Toast.LENGTH_SHORT).show();
            }
            sBackPressedTime = System.currentTimeMillis();
        }
    }

    private void setUpSearchButton() {

        mSearchButton = (ImageButton)findViewById(R.id.main_search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PostSearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_down);
            }
        });
    }

    private void setUpDrawer() {

        mMainDrawerViewHolder = new MainDrawerViewHolder(this, new MainDrawerViewHolder.DrawerMenuButtonCallback() {
            @Override
            public void onLoginButtonClick() {

                showLogginDialog();
            }

            @Override
            public void onLogoutButtonClick() {

                logout();
            }

            @Override
            public void onAddPostButtonClick() {

                if (!isLoggedIn()) {

                    showLogginDialog();
                } else {

                    startAddPostActivity();
                }
            }

            @Override
            public void onMyActivityButtonClick() {

                if (!isLoggedIn()) {

                }
            }

            @Override
            public void onSettingsButtonClick() {

                if (!isLoggedIn()) {

                }
            }
        });
        mMainDrawerViewHolder.initializeDrawerLayout();
    }

    private void startAddPostActivity() {

        Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
        intent.putExtra(AddPostActivity.EXTRA_ADD_TYPE, TagSettingsActivity.AddType.Post);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_down);
    }

    private void setUpTabLayout() {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabTitles = new ArrayList<>();

        mLatestOrderPostListFragment = PostListFragment.newInstance(PostListFragment.PostListOrderType.Latest);
        mMyActivityPostListFragment = PostListFragment.newInstance(PostListFragment.PostListOrderType.MyActivity);

        fragments.add(mLatestOrderPostListFragment);
        fragments.add(mMyActivityPostListFragment);

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

    private boolean isLoggedIn() {

        return AccountManager.getInstance().isLoggedIn(this);

    }

    private void showLogginDialog() {

        mSignUpDialogFragment = SignUpDialogFragment.newInstance();
        mSignUpDialogFragment.setDismissCallback(new SignUpDialogFragment.DismissCallback() {
            @Override
            public void onDismiss() {

                mMainDrawerViewHolder.updateDrawerByLoginState();
                if (isLoggedIn()) mMyActivityPostListFragment.updateMyActivityList(MainActivity.this);
            }
        });
        mSignUpDialogFragment.show(getSupportFragmentManager(), SignUpDialogFragment.class.getSimpleName());
    }

    private void logout() {

        SessionClientHelper.logout(this, new RestClient.RestListener() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(Object response) {

                mMainDrawerViewHolder.updateDrawerByLoginState();
                mMyActivityPostListFragment.updateMyActivityList(MainActivity.this);

            }

            @Override
            public void onFail(CoreError error) {

                CommonUtils.showToast(getApplicationContext(), error.errorMsg);
            }

            @Override
            public void onError(CoreError error) {

                CommonUtils.showToast(getApplicationContext(), error.errorMsg);
            }
        });
    }
}
