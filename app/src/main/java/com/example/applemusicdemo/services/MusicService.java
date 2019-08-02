package com.example.applemusicdemo.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.example.applemusicdemo.R;
import com.example.applemusicdemo.activities.PlayActivity;
import com.example.applemusicdemo.activities.WelcomeActivity;
import com.example.applemusicdemo.helps.MediaPlayHelper;
import com.example.applemusicdemo.models.MusicModel;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayHelper helper;
    private MusicModel musicModel;
    private static final int NOTIFICATION_ID=1;
    public MusicService() {
    }
   public  class MusicBind extends Binder{
        /**
         *         1.设置音乐（MusicModel）
         *         2.播放音乐；
         *         3.暂停音乐；
          */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void setMusic(MusicModel mMusicModel){
            musicModel=mMusicModel;
            setMusicServiceVisible();
        }
        public void playMusic() throws IOException {
            /**
             * 1.判断当前音乐是否是已经在播放的音乐；
             * 2、如果当前的音乐是已经在播放的音乐，直接执行start方法；
             * 3.如果当前音乐不是需要播放的音乐，先调用setPath方法
             */
            if(helper.getPath()!=null&&
                    helper.getPath().equals(musicModel.getPath())){
                    helper.start();
            }else{
                helper.setPath(musicModel.getPath());
                helper.setOnMediaPlayHelperListener(new MediaPlayHelper.OnMediaPlayHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        helper.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                         stopSelf();
                    }
                });
            }
        }
        public void stopMusic(){
            helper.pause();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        helper=MediaPlayHelper.getInstance(this);
    }
    /**
     * 系统默认不允许不可见的后台服务播放音乐
     * 通过Notification使音乐播放可见
     */
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setMusicServiceVisible(){
        /**
         * 通知栏跳转的Intent
         */
        PendingIntent intent=PendingIntent.getActivity(this,0,new Intent(this,
                WelcomeActivity.class),PendingIntent.FLAG_CANCEL_CURRENT);
        /**
         * 创建Notification
         */
        Notification notification = null;
        /**
         * android API 26 以上 NotificationChannel 特性适配
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = createNotificationChannel();
            notification = new Notification.Builder(this, channel.getId())
                    .setContentTitle("苹果音乐正在播放："+"《"+musicModel.getName()+"》")
                    .setContentText("作者："+musicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(intent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        } else {
            notification = new Notification.Builder(this)
                    .setContentTitle("苹果音乐正在播放："+"《"+musicModel.getName()+"》")
                    .setContentText("作者："+musicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(intent)
                    .build();
        }



        startForeground(NOTIFICATION_ID,notification);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel () {
        String channelId = "apple";
        String channelName = "AppleMusicService";
        String Description = "AppleMusic";
        NotificationChannel channel = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(Description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        channel.setShowBadge(false);

        return channel;

    }
}
