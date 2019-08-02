package com.example.applemusicdemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.applemusicdemo.R;
import com.example.applemusicdemo.helps.RealmHelper;
import com.example.applemusicdemo.models.MusicModel;
import com.example.applemusicdemo.views.PlayMusicView;

import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 1.代码编写流程及规范；
 * 2.了解运行原理非常重要；
 * 3.Realm 数据库
 */

public class PlayActivity extends BaseActivity {
    private ImageView music_bg_img;
    private PlayMusicView playMusicView;
    public static final String MUSIC_ID="musicId";
    private String musicId;
    private MusicModel musicModel;
    private RealmHelper helper;
    private TextView play_music_name,play_music_author;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    }

    private void initView() throws IOException {
        music_bg_img=findViewById(R.id.music_bk_img);
        play_music_author=findViewById(R.id.play_music_author);
        play_music_name=findViewById(R.id.play_music_name);
        //glide_transformation
        //实现高斯模糊效果
        Glide.with(this).load(musicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,10)))
                .into(music_bg_img);
        play_music_name.setText(musicModel.getName());
        play_music_author.setText(musicModel.getAuthor());
        playMusicView=findViewById(R.id.playMusicView);
        playMusicView.setMusic(musicModel);
        playMusicView.playMusic();
    }

    public void OnPlayBackClick(View view){
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeRealm();
        playMusicView.unBindService();
    }
}
