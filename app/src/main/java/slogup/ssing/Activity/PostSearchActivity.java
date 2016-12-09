package slogup.ssing.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;

import slogup.ssing.Fragment.PostListFragment;
import slogup.ssing.R;

public class PostSearchActivity extends BaseActivity {

    private static final String LOG_TAG = PostSearchActivity.class.getSimpleName();
    private Button mSearchOptionBodyButton;
    private Button mSearchOptionHashTagButton;
    private SearchView mSearchView;
    private boolean mIsBodySearch = true;
    private PostListFragment mPostListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_search);

        enableToolbar();
        bindWidgets();
        addFragment();
        setUpActions();
    }

    private void bindWidgets() {

        mSearchOptionBodyButton = (Button)findViewById(R.id.post_search_option_body_button);
        mSearchOptionHashTagButton = (Button)findViewById(R.id.post_search_option_hashtag_button);
        mSearchView = (SearchView)findViewById(R.id.post_search_search_view);
    }

    private void setUpActions() {

        mSearchOptionBodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSearchOptionBodyButton.setSelected(true);
                mSearchOptionHashTagButton.setSelected(false);
                mIsBodySearch = true;
                mSearchView.setQueryHint(getString(R.string.title_search_body_hint));
            }
        });

        mSearchOptionHashTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSearchOptionBodyButton.setSelected(false);
                mSearchOptionHashTagButton.setSelected(true);
                mIsBodySearch = false;
                mSearchView.setQueryHint(getString(R.string.title_search_hashtag_hint));
            }
        });

        mSearchOptionBodyButton.performClick();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!query.isEmpty()) {

                    mPostListFragment.searchPost(query, mIsBodySearch);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void addFragment() {

        mPostListFragment = PostListFragment.newInstance(PostListFragment.PostListOrderType.Search);
        getSupportFragmentManager().beginTransaction().add(R.id.post_search_fragment_container, mPostListFragment, LOG_TAG).commit();

    }
}
