package com.test.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.com.R;
import com.test.com.entity.ExamPlanLists;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/12 16:18.
 */

public class ExamPlamListAdapter extends BaseAdapter {
    private List<ExamPlanLists.Course_list> lists;
    private Context mContext;


    public ExamPlamListAdapter(List<ExamPlanLists.Course_list> lists, Context mContext) {
        this.lists = lists;
        Log.e("TAG++++++++",lists.toString());
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHoler holder = new viewHoler();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_exampan_listview, null);
            holder.textviewCode= (TextView) convertView.findViewById(R.id.code);
            holder.textviewName= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (viewHoler) convertView.getTag();
        }
        holder.textviewCode.setText(lists.get(position).getCourse_code());
        holder.textviewName.setText(lists.get(position).getCourse_name());

        return convertView;
    }

    class viewHoler {
        TextView textviewCode;
        TextView textviewName;

    }
}
