package com.example.applemusicdemo.views;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int mspace;

    public GridItemDecoration(int space,RecyclerView parent) {
        mspace=space;
        getRecyclerViewOffset(parent);
    }

    /**
     *
     * @param outRect 矩形边界
     * @param view  ItemView
     * @param parent RecyclerView
     * @param state RecyclerView的状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //向右偏移4dp
        outRect.left=mspace;
        //判断是不是每一行的第一个item
//        if(parent.getChildAdapterPosition(view)%3==0){
//            outRect.left=0;
//        }

    }

    private void getRecyclerViewOffset(@NonNull RecyclerView parent) {
        //margin为正，则会与边界产生一个距离；
        //margin为负，则view会超出边界产生一个距离
        LinearLayout.LayoutParams  layoutParams= (LinearLayout.LayoutParams) parent.getLayoutParams();
        layoutParams.leftMargin=-mspace;
        parent.setLayoutParams(layoutParams);
    }
}
