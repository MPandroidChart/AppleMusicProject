package com.example.applemusicdemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.applemusicdemo.helps.RealmHelper;

import io.realm.Realm;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化工具类
        Utils.init(this);
        //初始化Realm数据库
        Realm.init(this);
        RealmHelper.migration();
    }
}
