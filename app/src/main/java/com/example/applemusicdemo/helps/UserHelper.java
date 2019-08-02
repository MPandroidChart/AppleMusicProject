package com.example.applemusicdemo.helps;
/**
 * 1。用户登录
 * （1用户没有登录过应用，通过SharedPreferences保存登录用户标识（用户手机号）；
 * （2利用全局单例类UserHelper保存用户登录信息：
 *     a.用户登录之后；
 *     b.用户打开应用程序，检测检测SharedPreferences 中是否存在登录用户标记，若存在则为UserHelpe 进行赋值，并且进入主页，否则进入登录页面；
 * 2。用户退出
 * 删除SharedPreferences中用户登录的标记
 */

public class UserHelper {
    private static UserHelper instance;

    public UserHelper() {
    }
    //将UserHelper设计成单例模式
    public static UserHelper getInstance(){
        if(instance==null){
            synchronized (UserHelper.class){
                if(instance==null){
                    instance=new UserHelper();
                }
            }
        }
        return  instance;

    }
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
