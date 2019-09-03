package com.example.applemusicdemo.migration;

import com.example.applemusicdemo.models.MusicModel;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Realm数据库迁移实现类
 */
public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
         //第一次迁移
        if(oldVersion==0){
            RealmSchema  schema=realm.getSchema();
            /**
             * private String musicId;
             *     private String name;
             *     private String poster;
             *     private String path;
             *     private String author;
             */
            schema.create("MusicModel")
                    .addField("musicId",String.class)
                    .addField("name",String.class)
                    .addField("poster",String.class)
                    .addField("path",String.class)
                    .addField("author",String.class)
                    .addField("length",Integer.class);
/**
 * private String albumId;
 *     private String name;
 *     private String poster;
 *     private String playNum;
 *     private RealmList<MusicModel>list;
 */
            schema.create("AlbumModel")
                    .addField("albumId",String.class)
                    .addField("name",String.class)
                    .addField("poster",String.class)
                    .addField("playNum",String.class)
                    .addRealmListField("list",schema.get("MusicModel"));
            /**
             * **
             *  * private RealmList<AlbumModel>album;//推荐歌单列表
             *  * private RealmList<MusicModel>hot;//最新音乐列表
             *  */

            schema.create("MusicResourceModel")
                    .addRealmListField("album",schema.get("AlbumModel"))
                    .addRealmListField("hot",schema.get("MusicModel"));

            oldVersion=newVersion;
        }
    }
}
