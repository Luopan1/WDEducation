package com.test.com.baseUi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;


/**
 * listView重写
 * Created by Administrator on 2017/3/22.
 */

public class MListView extends ListView {
    private boolean isDispatching = true;

    //设置listview抢占onitemclick
    public void setDispatching(boolean isDispatching) {
        this.isDispatching = isDispatching;
    }


    public MListView(Context context) {
        // TODO Auto-generated method stub
        super(context);
    }

    public MListView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        super(context, attrs);
    }

    public MListView(Context context, AttributeSet attrs, int defStyle) {
        // TODO Auto-generated method stub
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    //false onitemclick 可用
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isDispatching) {
            return isDispatching;
        }

        //不然普通的onitemclick不能点击
        return super.dispatchTouchEvent(ev);
    }
}
