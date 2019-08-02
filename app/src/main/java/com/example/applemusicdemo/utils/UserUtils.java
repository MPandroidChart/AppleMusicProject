package com.example.applemusicdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.applemusicdemo.R;
import com.example.applemusicdemo.activities.LoginActivity;
import com.example.applemusicdemo.activities.MeActivity;
import com.example.applemusicdemo.helps.RealmHelper;
import com.example.applemusicdemo.helps.UserHelper;
import com.example.applemusicdemo.models.UserModel;

import java.util.List;

import io.realm.RealmQuery;

public class UserUtils {
    /**
     * 验证用户登录
     * @param context
     * @param phone
     * @param password
     * @return
     */
    public static boolean validateLoginInfo(Context context, String phone, String password) {

        // RegexUtils.isMobileSimple(phone);//简单识别
        //精确识别
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         * 1.用户当前手机号是否已经被注册；
         * 2.用户输入的手机号和密码是否匹配；
         */
        if(!isUserExistByPhone(phone)){
            Toast.makeText(context, "当前用户未被注册！", Toast.LENGTH_SHORT).show();
            return false;
        }
        RealmHelper help=new RealmHelper();
        boolean reasult=help.isUserInfoCorrect(phone,EncryptUtils.encryptMD5ToString(password));

        if(!reasult){
            Toast.makeText(context, "手机号或者密码错误！", Toast.LENGTH_SHORT).show();

            return false;
        }
        //保存用户登录标记
        boolean res=SPUtils.saveUser(context,phone);
        if(!res){
            Toast.makeText(context, "系统错误，请稍后重试！", Toast.LENGTH_SHORT).show();

            return false;
        }
     //保存用户标记到全局单例类中
        UserHelper.getInstance().setPhone(phone);
        //保存音乐数据源；
        help.saveMusicResource(context);
        help.Close();
      return true;
    }

    /**
     * 用户注册信息合法性检验
     * @param context
     * @param phone
     * @param password
     * @param passwordEnsure
     */
    public static boolean  UserRegister(Context context,String phone,String password,String passwordEnsure){
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(password)||!password.equals(passwordEnsure)){
            Toast.makeText(context, "请确认密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        //查询用户注册手机号是否可以注册
        //Todo
        if(isUserExistByPhone(phone)){
            Toast.makeText(context,"该手机号已经被注册！",Toast.LENGTH_SHORT).show();
            return false;
        }
        //注册成功，将用户信息存储进用户模型；
        UserModel userModel=new UserModel();
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));
        userModel.setPhone(phone);
        SavaUserModel(userModel);
        Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT).show();
        return true;

    }
    //存储用户信息
    public static void SavaUserModel(UserModel userModel){
        RealmHelper help=new RealmHelper();
        help.SaveUserInfo(userModel);
        help.Close();//释放引用

    }
    /**
     *
     * 判断用户是否存在
     */
    public static  boolean isUserExistByPhone(String phone){
        boolean isExist=false;
        RealmHelper helper=new RealmHelper();
        List<UserModel>models=helper.getAllUser();
        for(UserModel userModel:models){
            if(userModel.getPhone().equals(phone)){
                isExist=true;
                break;
            }
        }
        helper.Close();
        return isExist;

    }
    /**
     * 验证是否存在已登录用户
     */
    public static boolean userLoginState(Context context){
        return SPUtils.isLoginUser(context);
    }
    /**
     *  修改密码
     *  1。验证原密码；
     *  2.新密码是否输入，并且新旧密码是否一样；
     *  3.利用Realm 模型的自动更新特性完成密码的修改；
     */
    public static boolean changeUserPassword(Context context,String oldpass,String newpass,String ensurepass){
        if(TextUtils.isEmpty(oldpass)){
            Toast.makeText(context,"请输入原密码！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(newpass)||!newpass.equals(ensurepass)){
            Toast.makeText(context,"请确认密码！",Toast.LENGTH_SHORT).show();
            return false;
        }
        //验证原密码
        RealmHelper helper=new RealmHelper();
        UserModel model=helper.getLoginedUser();
        if(!EncryptUtils.encryptMD5ToString(oldpass).equals(model.getPassword())){
            Toast.makeText(context,"原密码错误！",Toast.LENGTH_SHORT).show();
            return false;

        }
        helper.ChangeorUpdatePassword(EncryptUtils.encryptMD5ToString(newpass));
        helper.Close();
        return true;

    }

    /**
     * 退出登录
     * @param context
     */
    public static void LogOut(Context context){
        // 清除用户登录标记，返回清除结果
        boolean isRemove = SPUtils.removeLoginState(context);
        if (!isRemove) {
            Toast.makeText(context, "系统错误，请稍后重试！", Toast.LENGTH_SHORT).show();
            return;
        }
        //删除音乐数据源
        RealmHelper helper=new RealmHelper();
        helper.removeMusicResource();
        helper.Close();
        Intent i = new Intent(context, LoginActivity.class);

        //为Intent设置标志符，清理task栈，新生成一个task栈
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        //执行切入切出动画
        ((Activity)context).overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
    }

}