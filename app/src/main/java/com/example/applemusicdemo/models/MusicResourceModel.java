package com.example.applemusicdemo.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MusicResourceModel extends RealmObject {
    private RealmList<AlbumModel>album;//推荐歌单列表
    private RealmList<MusicModel>hot;//最新音乐列表

    public RealmList<AlbumModel> getAlbum() {
        return album;
    }

    public void setAlbum(RealmList<AlbumModel> album) {
        this.album = album;
    }

    public RealmList<MusicModel> getHot() {
        return hot;
    }

    public void setHot(RealmList<MusicModel> hot) {
        this.hot = hot;
    }
}
