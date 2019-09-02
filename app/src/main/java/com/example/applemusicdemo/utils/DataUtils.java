package com.example.applemusicdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *读取资源文件中json数据
 */

public class DataUtils {
    public static String getJsonDataFromAssets(Context context,String filename){
        /**
         * 1.Stringbuilder 存放读取出的数据；
         * 2.AssetManager 资源管理器，Open 方法  打开指定的资源文件，返回InputStream;
         * 3.InputStreamReader() 字节到字符的桥接器，BufferedReader(存放读取字符的缓冲区)；
         * 4.循环利用BufferedReader的readLine方法读取每一行的数据，并把数据放入到StringBuilder里面。
         * 5.返回读取的数据
         */
        // 1.Stringbuilder 存放读取出的数据；
        StringBuilder sb=new StringBuilder();
        AssetManager am=context.getAssets();
        try {
            InputStream is=am.open(filename);
            InputStreamReader isb=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isb);
            String line;
            while ((line=br.readLine())!=null){
                //每读一行，将内容追加到StringBuilder中；
                 sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    /**
     * 获取网络音乐时长；
     * @throws
     */
    public static int getRingDuring(String mUri){
        String duration=null;
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();

        try {
            if (mUri != null) {
                HashMap<String, String> headers=null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            }
            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        int musc_length=0;
        try {
            musc_length = Integer.parseInt(duration);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return musc_length;
    }

}
