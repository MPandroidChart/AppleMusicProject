package com.example.applemusicdemo.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.applemusicdemo.R;
import com.example.applemusicdemo.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        init();
    }
 /*初始化*/
    private void init() {
        //判断用户登录状态
        final boolean isLogin= UserUtils.userLoginState(this);
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //延时之后执行方法；
                if(isLogin){
                    toMain();//已经登录则跳转到主页面
                }else {
                    toLogin();//未登录状态，则跳转到登录页面
                }
            }
        },3*1000);
    }

    private void toLogin() {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }
    private void toMain(){
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }
}
