package com.example.applemusicdemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.applemusicdemo.R;
import com.example.applemusicdemo.adapters.MusicListAdapter;
import com.example.applemusicdemo.helps.RealmHelper;
import com.example.applemusicdemo.models.AlbumModel;
import com.example.applemusicdemo.models.MusicModel;

public class AlbumListActivity extends BaseActivity {
    private RecyclerView rv_album;
    private MusicListAdapter adapter;
    public static final String ALBUM_ID="albumId";
    private String albumId;
    private RealmHelper helper;
    private AlbumModel albumModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        initData();
        initView();

    }

    private void initData() {
        Intent i=getIntent();
        albumId=i.getStringExtra(ALBUM_ID);
        helper=new RealmHelper();
        albumModel=helper.getAlbum(albumId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeRealm();
    }

    private void initView() {
        initNavBar(true,"专辑音乐",false);
        rv_album=findViewById(R.id.rv_album_list);
        rv_album.setLayoutManager(new LinearLayoutManager(this));
        rv_album.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter=new MusicListAdapter(this,null,albumModel.getList());
        rv_album.setAdapter(adapter);
    }
}
