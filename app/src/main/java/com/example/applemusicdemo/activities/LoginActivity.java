package com.example.applemusicdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.applemusicdemo.R;
import com.example.applemusicdemo.utils.UserUtils;
import com.example.applemusicdemo.views.InputView;

public class LoginActivity extends BaseActivity {
    private InputView input_phone,input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        initNavBar(false,"登录",false);
        input_password=findViewById(R.id.input_password);
        input_phone=findViewById(R.id.input_phone);
    }

    /**
     * 用户注册
     * @param view
     */
    public void  OnRegisterClick(View view){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    /**
     * 用户登录
     * @param view
     */
    public void OnLoginClick(View view){
        String password=input_password.getInputContent();

        String phone=input_phone.getInputContent();

       if(!UserUtils.validateLoginInfo(this,phone,password)){
           return;
       }else
           {
           startActivity(new Intent(LoginActivity.this,MainActivity.class  ));
           finish();
       }
    }
}
