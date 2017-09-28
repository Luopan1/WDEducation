package com.test720.wendujiaoyu.baseUi;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LuoPan on 2017/7/22.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int top,left;

    public SpaceItemDecoration(int top,int left) {
        this.top = top;

        this.left=left;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left =left;
        outRect.top = top;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0||parent.getChildPosition(view) ==1){
            outRect.top = top/2;
        }


    }
}
