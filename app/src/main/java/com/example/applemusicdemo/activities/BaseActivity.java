package com.example.applemusicdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applemusicdemo.R;

public class BaseActivity extends Activity {
    private ImageView iv_back,iv_me;
    private TextView nav_title;
    //设置导航栏图标和标题的显示；
    protected  void initNavBar(boolean isshowBack,String navTitle,boolean isshowMe){
          iv_back=findViewById(R.id.iv_back);
          iv_me=findViewById(R.id.iv_me);
          nav_title=findViewById(R.id.nav_title);
          iv_me.setVisibility(isshowMe? View.VISIBLE:View.GONE);
          iv_back.setVisibility(isshowBack?View.VISIBLE:View.GONE);
          nav_title.setText(navTitle);
          iv_back.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  onBackPressed();
              }
          });
          iv_me.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(BaseActivity.this,MeActivity.class));
              }
          });
    }
}
