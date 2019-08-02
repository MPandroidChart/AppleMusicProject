package com.example.applemusicdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.applemusicdemo.R;
import com.example.applemusicdemo.utils.UserUtils;
import com.example.applemusicdemo.views.InputView;

public class RegisterActivity extends BaseActivity {
    private InputView input_phone,input_password,input_password_ensure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        initNavBar(true,"注册",false);
        input_password=findViewById(R.id.input_rg_password);
        input_password_ensure=findViewById(R.id.input_ensure_password);
        input_phone=findViewById(R.id.input_rg_phone);
    }
    /**
     * 注册按钮点击事件：
     * 1.用户输入合法性验证
     * （1）用户输入 的 手机号是否合法；
     * （2）用户是否输入了密码和确定密码，并且两次输入是否相同；
     * （3）输入的手机号是否已经被注册；
     * 2.保存用户输入的手机号和密码（MD5加密密码）
     */
    public void OnRgClick(View view){
        String phone=input_phone.getInputContent();
        String password=input_password.getInputContent();
        String password_ensure=input_password_ensure.getInputContent();
       boolean isRegisterSuccess= UserUtils.UserRegister(this,phone,password,password_ensure);
       if(!isRegisterSuccess)return;
        onBackPressed();


    }
}
