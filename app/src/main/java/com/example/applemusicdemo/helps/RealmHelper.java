package com.example.applemusicdemo.helps;

import android.content.Context;

import com.example.applemusicdemo.migration.Migration;
import com.example.applemusicdemo.models.AlbumModel;
import com.example.applemusicdemo.models.MusicModel;
import com.example.applemusicdemo.models.MusicResourceModel;
import com.example.applemusicdemo.models.UserModel;
import com.example.applemusicdemo.utils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper {
    private Realm mRealm;

    public RealmHelper() {

        this.mRealm = Realm.getDefaultInstance();
    }
    public void SaveUserInfo(UserModel model){
        mRealm.beginTransaction();
       // mRealm.insert(model);//存在主键冲突
        mRealm.insertOrUpdate(model);//主键不变的情况下，会进行该主键下的其他字段的更新；
        mRealm.commitTransaction();
    }
    public void  closeRealm(){
        if(mRealm!=null||!mRealm.isClosed()){
            mRealm.close();
        }
    }
    public List<UserModel>getAllUser(){
        RealmQuery<UserModel> query=mRealm.where(UserModel.class);
        RealmResults<UserModel>results=query.findAll();
        return results;
    }
    /**
     * 验证用户账号信息是否正确
     */
    public  boolean  isUserInfoCorrect(String phone,String password){
        boolean  isCorrect=false;
        RealmQuery<UserModel>query=mRealm.where(UserModel.class);
        query=query.equalTo("phone",phone).equalTo("password",password);
       //找到匹配的用户信息；
        UserModel model=query.findFirst();
        if(model!=null){
            isCorrect=true;//用户信息正确；
        }
        return isCorrect;
    }

    /**
     * 获取当前登录用户
     */
    public UserModel getLoginedUser(){
        RealmQuery<UserModel>query=mRealm.where(UserModel.class);
        UserModel model=query.equalTo("phone",UserHelper.getInstance().getPhone()).findFirst();
        return model;

    }
    /**
     * 修改密码
     */
    public void ChangeorUpdatePassword(String password){
        UserModel model=getLoginedUser();
        mRealm.beginTransaction();
        model.setPassword(password);
        mRealm.commitTransaction();
    }
    /**
     * 1.用户登录，存放数据；
     * 2.用户退出，删除数据；
     */
    public void saveMusicResource(Context context){
        String musicJson=DataUtils.getJsonDataFromAssets(context,"DataSource.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicResourceModel.class,musicJson);
        mRealm.commitTransaction();

    }
    /**
     * 删除数据源；
     * 1.RealmResult;
     * 2.Realm delete 删除模型下的所有数据；
     */
    public void removeMusicResource(){
        mRealm.beginTransaction();
        mRealm.delete(MusicResourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.commitTransaction();
    }

    /**
     * 关闭Realm数据库
     */
    public void Close(){
        if(mRealm!=null&&!mRealm.isClosed()){
            mRealm.close();
        }
    }
    /**
     * Realm数据库迁移
     * Realm数据库发生结构性变化（模型或者模型中的字段发生了新增、修改、删除）的时候，就需要对数据库进行迁移；
     */
    public static RealmConfiguration getRealmConfig(){
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new Migration())
                .build();
    }
    /**
     * 告诉realm 数据库数据需要迁移了，并且为Realm 设置最新的配置；
     */
    public static void migration(){
        RealmConfiguration conf=getRealmConfig();
        //为realm设置最新配置
        Realm.setDefaultConfiguration(conf);
        // 数据迁移
        try {
            Realm.migrateRealm(conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 返回音乐源数据
     */
    public  MusicResourceModel getMusicResource(){
        return  mRealm.where(MusicResourceModel.class).findFirst();
    }
    /**
     * 返回歌单
     */
    public AlbumModel getAlbum(String albumId){
        return mRealm.where(AlbumModel.class).equalTo("albumId",albumId).findFirst();
    }
    /**
     * 返回音乐
     */
    public MusicModel getMusic (String MusicId){
        return mRealm.where(MusicModel.class).equalTo("musicId",MusicId).findFirst();
    }
    //
    /**
     *     设置音乐时长
     */
    public void setMusicLength(String MusicId){
        MusicModel model=getMusic(MusicId);
        mRealm.beginTransaction();
        model.setLength(DataUtils.getRingDuring(model.getPath()));
        mRealm.commitTransaction();
    }

}
