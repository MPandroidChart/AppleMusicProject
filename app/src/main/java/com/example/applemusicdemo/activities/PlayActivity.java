package com.example.applemusicdemo.activities;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.applemusicdemo.R;
import com.example.applemusicdemo.helps.MediaPlayHelper;
import com.example.applemusicdemo.helps.RealmHelper;
import com.example.applemusicdemo.models.MusicModel;
import com.example.applemusicdemo.views.PlayMusicView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 1.代码编写流程及规范；
 * 2.了解运行原理非常重要；
 * 3.Realm 数据库
 */
//https://blog.csdn.net/tracydragonlxy/article/details/77978630
public class PlayActivity extends BaseActivity implements View.OnClickListener{
    private ImageView music_bg_img;
    private PlayMusicView playMusicView;
    public static final String MUSIC_ID="musicId";
    private String musicId;
    private MusicModel musicModel;
    private RealmHelper helper;
    private TextView play_music_name,play_music_author;
    private SeekBar music_sb;
    private ImageButton play_music_ib,next_music_ib,last_music_ib;
    private TextView music_current,music_length;
    private MediaPlayHelper mediaPlayHelper;
    private boolean isSeekBarChanging;//互斥变量，防止进度条与定时器冲突。
    private int currentPosition;//当前音乐播放的进度
    private  SimpleDateFormat format=new SimpleDateFormat("mm:ss");
    private boolean isplaying=false;
    private String path;
    private Timer timer;
    private static final  String TAG="PlayActivity";
    private  int musc_length = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        //隐藏TitleBar,全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inidata();
        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void inidata() {
        Intent i=getIntent();
        musicId=i.getStringExtra(MUSIC_ID);
        helper=new RealmHelper();
        musicModel=helper.getMusic(musicId);
        path=musicModel.getPath();
        mediaPlayHelper=MediaPlayHelper.getInstance(this);

    }

    private void initView() throws IOException {
        music_bg_img=findViewById(R.id.music_bk_img);
        play_music_author=findViewById(R.id.play_music_author);
        play_music_name=findViewById(R.id.play_music_name);
        play_music_ib=findViewById(R.id.play_music_ib);
        next_music_ib=findViewById(R.id.play_next_ib);
        last_music_ib=findViewById(R.id.play_last_ib);
        play_music_ib.setOnClickListener(this);
        next_music_ib.setOnClickListener(this);
        last_music_ib.setOnClickListener(this);
        music_current=findViewById(R.id.music_cur);
        music_length=findViewById(R.id.music_length);
        music_sb=findViewById(R.id.seekBar);
        music_sb.setOnSeekBarChangeListener(new MySeekBar());
        music_current.setText("00:00");

        //glide_transformation
        //实现高斯模糊效果
        Glide.with(this).load(musicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,10)))
                .into(music_bg_img);
        play_music_name.setText(musicModel.getName()+"-");
        play_music_author.setText(musicModel.getAuthor());
        playMusicView=findViewById(R.id.playMusicView);
        playMusicView.setMusic(musicModel);
        musc_length=musicModel.getLength();
        String length=format.format(musc_length);
        music_length.setText(length);
        music_sb.setMax(musc_length);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeRealm();
        playMusicView.unBindService();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
       isSeekBarChanging=true;
    }
    /**
     * 切换播放状态
     */
    private void trigger() throws IOException {
        isplaying=playMusicView.isplay();
        if(isplaying){
            play_music_ib.setImageResource(R.mipmap.play);
            playMusicView.stopMusic();
            currentPosition=mediaPlayHelper.getCurrentPosition();
        }else{
            mediaPlayHelper.setPlayPosition(currentPosition);
            play_music_ib.setImageResource(R.mipmap.stop);
            playMusicView.playMusic();
            music_length.setText(format.format(mediaPlayHelper.getDuration())+"");
            //监听播放时回调函数
            timer = new Timer();
            timer.schedule(new TimerTask() {

                Runnable updateUI = new Runnable() {
                    @Override
                    public void run() {
                        music_current.setText(format.format(mediaPlayHelper.getCurrentPosition())+"");
                        currentPosition=mediaPlayHelper.getCurrentPosition();
                    }
                };
                @Override
                public void run() {
                    if(!isSeekBarChanging){
                        music_sb.setProgress(mediaPlayHelper.getCurrentPosition());
                        currentPosition=mediaPlayHelper.getCurrentPosition();
                        runOnUiThread(updateUI);
                    }
                }
            },0,50);
        }
    }
    @Override
    public void onClick(View v) {
     switch(v.getId()){
         case  R.id.play_music_ib:
           try {
               trigger();
           } catch (IOException e) {
               e.printStackTrace();
           }
           break;
         case R.id.play_next_ib:
             break;
         case R.id.play_last_ib:
             break;
     }
    }
    /*进度条处理*/
    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        /*滚动时,应当暂停后台定时器*/
        public void onStartTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = true;
        }

        /*滑动结束后，重新设置值*/
        public void onStopTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = false;
            currentPosition=seekBar.getProgress();
            mediaPlayHelper.setPlayPosition(seekBar.getProgress());
        }
    }

//    /**
//     * 获取网络音乐时长；
//     * @throws
//     */
//    public  String getRingDuring(String mUri){
//        String duration=null;
//        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
//
//        try {
//            if (mUri != null) {
//                HashMap<String, String> headers=null;
//                if (headers == null) {
//                    headers = new HashMap<String, String>();
//                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
//                }
//                mmr.setDataSource(mUri, headers);
//            }
//            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
//        } catch (Exception ex) {
//        } finally {
//            mmr.release();
//        }
//        Log.e(TAG,"duration "+duration);
//        try {
//            musc_length = Integer.parseInt(duration);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return format.format(musc_length);
//    }


}
