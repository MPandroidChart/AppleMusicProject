package com.example.applemusicdemo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applemusicdemo.R;
import com.example.applemusicdemo.constants.UserConstants;
import com.example.applemusicdemo.helps.UserHelper;
import com.example.applemusicdemo.utils.SPUtils;
import com.example.applemusicdemo.utils.UserUtils;

public class MeActivity extends BaseActivity {
    private TextView mine_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initView();
    }

    private void initView() {

        initNavBar(true, "个人中心", false);
        mine_account = findViewById(R.id.mine_account);
        String account = shezhiAccountName(this);
        if (!TextUtils.isEmpty(account)) {
            mine_account.setText(account);
           //或者 mine_account.setText(UserHelper.getInstance().getPhone());
        }
    }

    private String shezhiAccountName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(UserConstants.SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(UserConstants.USER_PHONE, "");
    }

    public void OnForgetPassClick(View view) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    public void OnExitLoginClick(View view) {
        UserUtils.LogOut(this);
    }
}
