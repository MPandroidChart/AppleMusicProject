package com.example.applemusicdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.DashPathEffect;
import android.text.TextUtils;

import com.example.applemusicdemo.constants.UserConstants;
import com.example.applemusicdemo.helps.UserHelper;

public class SPUtils {
    /**
     * 当用户登录时，通过SharedPreferences保存用户登录标记；
     * @param context
     * @param phone
     * @return
     */
    public static boolean saveUser(Context context,String phone){
            SharedPreferences sp=context.getSharedPreferences(UserConstants.SP_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString(UserConstants.USER_PHONE,phone);
            boolean result=editor.commit();
            return  result;
    }
    /**
     *  验证是否存在已登录用户
     */
    public static boolean isLoginUser(Context context){
        boolean result=false;
        SharedPreferences sp=context.getSharedPreferences(UserConstants.SP_NAME,Context.MODE_PRIVATE);
        String phone=sp.getString(UserConstants.USER_PHONE,"");
        if(!TextUtils.isEmpty(phone)){
            result=true;
            UserHelper.getInstance().setPhone(phone);
        }
        return  result;
    }
    /**
     * 删除用户登录标记
     */
    public static boolean removeLoginState(Context context){
        SharedPreferences sp=context.getSharedPreferences(UserConstants.SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.remove(UserConstants.USER_PHONE);
        boolean isRemove=ed.commit();
        return isRemove;
    }
}
