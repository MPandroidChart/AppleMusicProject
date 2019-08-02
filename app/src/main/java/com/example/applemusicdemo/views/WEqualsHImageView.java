package com.example.applemusicdemo.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WEqualsHImageView extends AppCompatImageView {

    public WEqualsHImageView(Context context) {
        super(context);
    }

    public WEqualsHImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WEqualsHImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        //通过widthMeasureSpec和heightMeasureSpec测量宽高
//        //获取View的宽高
//        int width=MeasureSpec.getSize(widthMeasureSpec);
//        //获取View的模式,包括match_parent、wrap_content或者是具体的dp;
//        int mode=MeasureSpec.getMode(widthMeasureSpec);
    }
}
