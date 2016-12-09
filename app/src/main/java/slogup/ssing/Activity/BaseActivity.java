package slogup.ssing.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import slogup.ssing.R;
import slogup.ssing.View.RotateProgressDialog;

import static slogup.ssing.R.anim.scale_down;
import static slogup.ssing.R.anim.slide_in_from_right;


public class BaseActivity extends AppCompatActivity {

    private TextView mToolbarTextView;
    private boolean mTransitionAnimated = true;
    private RotateProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void setTransitionAnimated(boolean transitionAnimated) {
        mTransitionAnimated = transitionAnimated;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {

            case android.R.id.home: {
                finish();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {

        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();

        if (mTransitionAnimated) overridePendingTransition(R.anim.scale_up, R.anim.slide_out_to_right);
    }

    public void enableToolbar() {

        setToolbar();
    }

    protected void setToolbarTitle(String title) {

        if (mToolbarTextView != null) {

            mToolbarTextView.setText(title);
        }
    }

    protected void disableUpButton() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

    }

    public void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    public void showProgressDialog() {

        if (mProgressDialog == null) mProgressDialog = new RotateProgressDialog(this);

        if (!mProgressDialog.isShowing()) mProgressDialog.show();
    }

    public void dismissProgressDialog() {

        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

}
