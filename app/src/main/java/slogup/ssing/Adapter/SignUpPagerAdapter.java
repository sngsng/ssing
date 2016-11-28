package slogup.ssing.Adapter;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shawnlin.numberpicker.NumberPicker;
import com.slogup.sgcore.manager.CoreManager;
import com.slogup.sgcore.model.Meta;

import java.util.Locale;

import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 11. 28..
 */

public class SignUpPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mLayoutResourceIds;

    private Button mGenderMaleButton;
    private Button mGenderFemaleButton;

    private NumberPicker mBirthNumberPicker;

    private TextInputLayout mNickInputLayout;
    private EditText mNickEditText;



    public SignUpPagerAdapter(Context context, int[] layoutResourceIds) {

        mContext = context;
        mLayoutResourceIds = layoutResourceIds;
    }

    @Override
    public int getCount() {
        return mLayoutResourceIds.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int layoutResourceId = mLayoutResourceIds[position];
        View view = LayoutInflater.from(mContext).inflate(layoutResourceId, container, false);

       setUpViews(layoutResourceId, view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private void setUpViews(int layoutResourceId, View rootView) {

        switch (layoutResourceId) {

            case R.layout.view_sign_up_gender_input: {

                mGenderMaleButton = (Button) rootView.findViewById(R.id.sign_up_gender_male_button);
                mGenderMaleButton.setSelected(true);
                mGenderFemaleButton = (Button) rootView.findViewById(R.id.sign_up_gender_female_button);

                mGenderMaleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mGenderMaleButton.setSelected(true);
                        mGenderFemaleButton.setSelected(false);
                    }
                });

                mGenderFemaleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mGenderFemaleButton.setSelected(true);
                        mGenderMaleButton.setSelected(false);
                    }
                });

            }
                break;
            case R.layout.view_sign_up_birth_input: {

                mBirthNumberPicker = (NumberPicker)rootView.findViewById(R.id.sign_up_birth_picker);
                mBirthNumberPicker.setValue(2000);
                mBirthNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    }
                });
            }
                break;
            case R.layout.view_sign_up_nick_input: {

                mNickInputLayout = (TextInputLayout)rootView.findViewById(R.id.sign_up_nick_input_layout);
                mNickEditText = (EditText)rootView.findViewById(R.id.sign_up_nick_name_edittext);

                mNickEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        String input = editable.toString();
                        validateNick(input);
                    }
                });
            }
                break;
        }
    }

    public String getSelectedGender() {

        if (mGenderMaleButton.isSelected()) {

            return CoreManager.getInstance().getMeta().getGenderMale();
        }
        else {

            return CoreManager.getInstance().getMeta().getGenderFemale();
        }
    }

    public int getSelectedBirth() {

        return mBirthNumberPicker.getMinValue();
    }

    public String getInputNickName() {

        return mNickEditText.getText().toString();
    }

    public void setInputError(String msg) {

        mNickInputLayout.setError(msg);
    }

    public boolean validateNick(String input) {

        Meta meta = CoreManager.getInstance().getMeta();
        int minLength = meta.getMinNickLength();
        int maxLength = meta.getMaxNickLength();


        if (input.isEmpty()) {

            setInputError(mContext.getString(R.string.validate_error_empty_nick));
            return false;
        }
        else if (input.length() < minLength || input.length() > maxLength) {

            setInputError(String.format(Locale.KOREA, mContext.getString(R.string.validate_error_format_nick_length), minLength, maxLength));
            return false;
        }
        else {

            mNickInputLayout.setErrorEnabled(false);
            return true;
        }
    }

}
