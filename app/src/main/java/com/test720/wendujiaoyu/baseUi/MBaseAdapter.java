package com.test720.wendujiaoyu.baseUi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by jie on 2016/8/17.
 */

public abstract class MBaseAdapter<T> extends BaseAdapter {
    protected List<T> list;
    protected Context mContext;
    protected Activity mActivity;
    protected LayoutInflater inflater;
    protected int stats;
    protected List<String> errorList;
    public MBaseAdapter(List<T> list, Activity mActivity) {
        this.list = list;
        this.mActivity = mActivity;
        inflater = LayoutInflater.from(mActivity);
    }

    public MBaseAdapter(List<T> list, Activity mActivity,int stats) {
        this.list = list;
        this.mActivity = mActivity;
        this.stats = stats;
        inflater = LayoutInflater.from(mActivity);
    }

    public MBaseAdapter(List<T> list, Activity mActivity,int stats,List<String> errorList) {
        this.list = list;
        this.mActivity = mActivity;
        this.stats = stats;
        this.errorList = errorList;
        inflater = LayoutInflater.from(mActivity);
    }


    @Override
    public int getCount() {
        if (list != null && list.size() > 0)
            return list.size();
        else
            return 0;
    }

    @Override
    public T getItem(int position) {
        if (list != null && list.size() > 0)
            return list.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getXView(position, convertView, parent);
    }

    public abstract View getXView(int position, View convertView, ViewGroup parent);
}