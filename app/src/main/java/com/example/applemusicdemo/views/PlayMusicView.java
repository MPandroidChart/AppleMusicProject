package com.example.applemusicdemo.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.applemusicdemo.R;
import com.example.applemusicdemo.helps.MediaPlayHelper;
import com.example.applemusicdemo.models.MusicModel;
import com.example.applemusicdemo.services.MusicService;

import java.io.IOException;


public class PlayMusicView extends FrameLayout {
    private Context mcontext;
    private View mView;
    private ImageView musicbk_icon;
    private Animation mplaydic_ainm,mplayneedle_anim,mleaveneedle_anim;
    private FrameLayout fl_play_dic;
    private ImageView play_needle;
    private boolean isplaying,isBindService;
    private MusicService.MusicBind  bind;
    private MediaPlayHelper mMediaPlayHelper;
    private Intent serviceIntent;
    private MusicModel musicModel;

    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    private void init(Context context){
        mcontext=context;
        mView= LayoutInflater.from(mcontext).inflate(R.layout.play_music_view,this,false);
        musicbk_icon=mView.findViewById(R.id.music_bk_icon);
        fl_play_dic=mView.findViewById(R.id.play_dic);
//        fl_play_dic.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    trigger();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        play_needle=mView.findViewById(R.id.play_needle);
        //iv_play_music=mView.findViewById(R.id.iv_play_music);
        /**
         * 一、定义所需要实现的动画
         * 1、光盘旋转动画
         * 2、指针指向光盘的动画
         * 3、指针离开光盘的动画；
         * 二、执行动画
         * startAnimation();
         */
        //加载动画；
        mplaydic_ainm= AnimationUtils.loadAnimation(mcontext,R.anim.play_disc_anim);
        mplayneedle_anim=AnimationUtils.loadAnimation(mcontext,R.anim.play_needle_anim);
        mleaveneedle_anim=AnimationUtils.loadAnimation(mcontext,R.anim.leave_needle_anim);
        addView(mView);
        //mMediaPlayHelper=MediaPlayHelper.getInstance(context);//获取帮助类的单例；
    }
    public void setMusicBkImg(){
        Glide.with(mcontext)
                .load(musicModel.getPoster())
                .into(musicbk_icon);

    }
    /**
     * 切换播放状态
     */
     private void trigger() throws IOException {
         if(isplaying){
             stopMusic();
         }else{
             playMusic();
         }
     }
     public void setMusic(MusicModel music){
         musicModel=music;
         setMusicBkImg();
     }
    /**
     *
     * 播放音乐
     */
    public void playMusic() throws IOException {
        isplaying=true;
        fl_play_dic.startAnimation(mplaydic_ainm);
        play_needle.startAnimation(mplayneedle_anim);
        //iv_play_music.setVisibility(View.GONE);
        /**
         * 1.判断当前音乐是否是已经在播放的音乐；
         * 2、如果当前的音乐是已经在播放的音乐，直接执行start方法；
         * 3.如果当前音乐不是需要播放的音乐，先调用setPath方法
          */
//        if(mMediaPlayHelper.getPath()!=null&&
//                mMediaPlayHelper.getPath().equals(path)){
//            mMediaPlayHelper.start();
//        }else{
//            mMediaPlayHelper.setPath(path);
//            mMediaPlayHelper.setOnMediaPlayHelperListener(new MediaPlayHelper.OnMediaPlayHelperListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    mMediaPlayHelper.start();
//                }
//            });
//        }
        startService();

    }
    /**
     *
     * 停止音乐
     */
    public void stopMusic(){
        isplaying=false;
        fl_play_dic.clearAnimation();
        play_needle.startAnimation(mleaveneedle_anim);
        //iv_play_music.setVisibility(View.VISIBLE);
        //mMediaPlayHelper.pause();
        if(bind!=null)
        bind.stopMusic();
    }
    /**
     * 启动服务
     */
    public void startService() throws IOException {
        if(serviceIntent==null){
            serviceIntent=new Intent(mcontext, MusicService.class);
            mcontext.startService(serviceIntent);

        }else{
            bind.playMusic();
        }
        bindService();
    }
    /**
     * 绑定音乐服务
     */
    public  void bindService(){
        if(!isBindService){
            isBindService=true;
            mcontext.bindService(serviceIntent,conn,Context.BIND_AUTO_CREATE);
        }
    }
    ServiceConnection conn=new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bind= (MusicService.MusicBind) iBinder;
            bind.setMusic(musicModel);
            try {
                bind.playMusic();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    /**
     * 解除绑定
     */
    public void unBindService(){
        if(isBindService){
            isBindService=false;
            mcontext.unbindService(conn);
        }
    }
}
