package slogup.ssing.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import slogup.ssing.Model.Post;
import slogup.ssing.R;

public class AddPostActivity extends BaseActivity {

    public enum AddType {

        Post,
        Comment
    }

    public static final String EXTRA_ADD_TYPE = "extraAddType";
    public static final String EXTRA_POST = "extraPost";
    private static final String LOG_TAG = AddPostActivity.class.getSimpleName();
    private AddType mAddType;
    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        enableToolbar();

        if (getIntent().hasExtra(EXTRA_ADD_TYPE))
            mAddType = (AddType) getIntent().getSerializableExtra(EXTRA_ADD_TYPE);
        if (getIntent().hasExtra(EXTRA_POST))
            mPost = getIntent().getParcelableExtra(EXTRA_POST);

    }

    private List<String> getHashTagStrings(String string) {

        Pattern hashTagPattern = Pattern.compile("#(\\S+)");
        Matcher mat = hashTagPattern.matcher(string);
        List<String> hashTags = new ArrayList<String>();

        while (mat.find()) {
            
            hashTags.add(mat.group(1));
        }

        return hashTags;
    }
}
