package com.example.applemusicdemo.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * 播放音乐的三种方式：
 * 1.直接在Activity 中去创建播放音乐，音乐与Activity绑定，Activity 运行时播放音乐，销毁时音乐停止；
 * 2.通过全局单例类与Application绑定，Application 运行时播放音乐，Application销毁时音乐停止；
 * 3.通过service进行音乐播放；
 */

public class MediaPlayHelper {
    private static MediaPlayHelper instance;
    private Context mcontext;
    private MediaPlayer mMediaPlayer;
    private OnMediaPlayHelperListener onMediaPlayHelperListener;
    private String mPath;

    public void setOnMediaPlayHelperListener(OnMediaPlayHelperListener onMediaPlayHelperListener) {
        this.onMediaPlayHelperListener = onMediaPlayHelperListener;
    }

    //单例模式
    public static MediaPlayHelper getInstance(Context context){
        if(instance==null){
            synchronized (MediaPlayHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayHelper(context);
                }
            }
        }
            return instance;

    }
    public MediaPlayHelper(Context context) {
        mcontext=context;
        mMediaPlayer=new MediaPlayer();
    }
    /**
     * 1、setPath：音乐路径；
     * 2、start：播放音乐；
     * 3、pause：暂停音乐；
     */
    public void setPath(String path) throws IOException {
        /**
         * 1.判断音乐播放状态，若音乐正在播放，重制播放路径；
         * 2。设置播放路径；
         * 3。准备播放；
         */

        if(mMediaPlayer.isPlaying()||!path.equals(mPath)){
            //音乐正在播放或者切换了音乐，则重置音乐播放状态；
            mMediaPlayer.reset();
        }
        mPath=path;
        mMediaPlayer.setDataSource(mcontext, Uri.parse(path));
        mMediaPlayer.prepareAsync();//异步加载网络音乐资源
      //  mMediaPlayer.prepare();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if(onMediaPlayHelperListener!=null){
                    onMediaPlayHelperListener.onPrepared(mediaPlayer);
                }

            }
        });
        /**
         *  监听音乐播放完成
         */
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(onMediaPlayHelperListener!=null){
                    onMediaPlayHelperListener.onCompletion(mediaPlayer);
                }

            }
        });
    }

    /**
     * 返回正在播放的音乐的路径；
     * @return
     */
    public String getPath(){
        return mPath;
    }

    /**
     * 播放音乐
     */
    public void start(){
        if(mMediaPlayer.isPlaying())return;
        mMediaPlayer.start();
    }

    /**
     * 暂停音乐
     */
    public void pause(){
        mMediaPlayer.pause();
    }
    public interface  OnMediaPlayHelperListener{
        void onPrepared(MediaPlayer mediaPlayer);
        void onCompletion(MediaPlayer mediaPlayer);
    }
    public void setPlayPosition(int position){
        mMediaPlayer.seekTo(position);
    }
}
