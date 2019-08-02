package com.example.applemusicdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}
