package com.example.applemusicdemo.activities;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.applemusicdemo.R;
import com.example.applemusicdemo.adapters.MusicGridAdapter;
import com.example.applemusicdemo.adapters.MusicListAdapter;
import com.example.applemusicdemo.helps.RealmHelper;
import com.example.applemusicdemo.models.MusicModel;
import com.example.applemusicdemo.models.MusicResourceModel;
import com.example.applemusicdemo.utils.DataUtils;
import com.example.applemusicdemo.views.GridItemDecoration;

import io.realm.Realm;

public class MainActivity extends BaseActivity {
    private RecyclerView rv_grid,rv_list;
    private MusicGridAdapter adapter;
    private MusicListAdapter listAdapter;
    private MusicResourceModel musicResourceModel;
    private RealmHelper helper;
    private int musicNum;
    private boolean isfirstin=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        helper=new RealmHelper();
        musicResourceModel=helper.getMusicResource();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁才能清理引用；
        helper.closeRealm();
    }

    private void initView() {
        initNavBar(false,"苹果音乐",true);
        rv_grid=findViewById(R.id.rv_grid);
        rv_grid.setLayoutManager(new GridLayoutManager(this,3));
        //让RecyclerView本身不能滑动；
        rv_grid.setNestedScrollingEnabled(false);
        adapter=new MusicGridAdapter(this,musicResourceModel.getAlbum());
        rv_grid.addItemDecoration(new GridItemDecoration(4,rv_grid));
        rv_grid.setAdapter(adapter);
        rv_list=findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        listAdapter=new MusicListAdapter(this,rv_list,musicResourceModel.getHot());
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setAdapter(listAdapter);
        musicNum=musicResourceModel.getHot().size();
        if(isfirstin) {
            getMusicLength();
            isfirstin=false;
        }

    }

    private void getMusicLength() {
                for(int i=0;i<musicNum;i++) {
                    helper.setMusicLength(musicResourceModel.getHot().get(i).getMusicId());
                }
    }

}

