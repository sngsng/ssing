package slogup.ssing.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 12. 5..
 */

public class RotateProgressDialog extends ProgressDialog {

    private RotateLoading mRotateProgressDialog;

    public RotateProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rotate_progress);


        mRotateProgressDialog = (RotateLoading)findViewById(R.id.rotate_progress_bar);

        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }


    @Override
    public void onAttachedToWindow() {

        super.onAttachedToWindow();
        mRotateProgressDialog.start();

    }

    @Override
    public void onDetachedFromWindow() {

        super.onDetachedFromWindow();
        mRotateProgressDialog.stop();

    }
}
