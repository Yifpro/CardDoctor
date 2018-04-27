package com.yunkahui.datacubeper.login.ui;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.BaseFragment;
import com.yunkahui.datacubeper.common.utils.CustomTextChangeListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends BaseFragment implements View.OnClickListener {

    private EditText mEditTextPhone;
    private EditText mEditTextAuthCode;
    private Button mButtonNext;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        mEditTextPhone = view.findViewById(R.id.edit_text_phone);
        mEditTextAuthCode = view.findViewById(R.id.edit_text_auth_code);
        mButtonNext = view.findViewById(R.id.button_next);

        mEditTextPhone.addTextChangedListener(new InnerTextChangerListener());
        mEditTextAuthCode.addTextChangedListener(new InnerTextChangerListener());
        view.findViewById(R.id.text_view_send_auth_code).setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forget_password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_send_auth_code:
                break;
            case R.id.button_next:
                break;
        }
    }

    private class InnerTextChangerListener extends CustomTextChangeListener {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(mEditTextPhone.getText().toString()) && !TextUtils.isEmpty(mEditTextAuthCode.getText().toString())) {
                mButtonNext.setEnabled(true);
                mButtonNext.setBackgroundResource(R.drawable.bg_button_login_selector);
            } else {
                mButtonNext.setEnabled(false);
                mButtonNext.setBackgroundColor(getResources().getColor(R.color.bg_button_gray_a9a9a9));
            }
        }
    }

}
