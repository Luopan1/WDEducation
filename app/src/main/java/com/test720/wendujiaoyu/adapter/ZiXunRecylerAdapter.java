package com.test720.wendujiaoyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.entity.ZiXunEntity;

import java.util.List;


/**
 * Created by LuoPan on 2017/7/28.
 */

public class ZiXunRecylerAdapter extends RecyclerView.Adapter<ZiXunRecylerAdapter.Holder> {
    private Context mContext;
    private List<ZiXunEntity> mlists;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private callBack mcallback;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public ZiXunRecylerAdapter(Context context, List<ZiXunEntity> mlists, OnItemClickListener mOnItemClickListener, callBack callback) {
        this.mContext = context;
        this.mlists = mlists;
        this.mOnItemClickListener = mOnItemClickListener;
        this.mcallback = callback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zixunlistview, parent, false);
        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        holder.kind.setText("自考资讯");
        holder.Title.setText(mlists.get(position).getTitle());
        Glide.with(mContext).load(mlists.get(position).getImageUrl()).into(holder.newImage);
        holder.sendTime.setText(mlists.get(position).getTime());

        holder.Title1.setText(mlists.get(position).getTitle());
        Glide.with(mContext).load(mlists.get(position).getImageUrl()).into(holder.newImage1);
        holder.sendTime1.setText(mlists.get(position).getTime());

        holder.Title2.setText(mlists.get(position).getTitle());
        Glide.with(mContext).load(mlists.get(position).getImageUrl()).into(holder.newImage2);
        holder.sendTime2.setText(mlists.get(position).getTime());

        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }

        holder.seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallback.send(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  mlists.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView vertivalLine;
        ImageView arrow;
        TextView kind;
        TextView seeAll;
        ImageView newImage;
        TextView Title;
        TextView sendTime;
        RelativeLayout kindRelative;
        ImageView newImage1;
        TextView Title1;
        TextView sendTime1;

        ImageView newImage2;
        TextView Title2;
        TextView sendTime2;

        public Holder(View view) {
            super(view);
            vertivalLine = (TextView) view.findViewById(R.id.vertivalLine);
            arrow = (ImageView) view.findViewById(R.id.arrow);
            kind = (TextView) view.findViewById(R.id.kind);
            seeAll = (TextView) view.findViewById(R.id.seeAll);
            newImage = (ImageView) view.findViewById(R.id.newImage);
            Title = (TextView) view.findViewById(R.id.Title);
            sendTime = (TextView) view.findViewById(R.id.sendTime);
            kindRelative = (RelativeLayout) view.findViewById(R.id.infoKind);

            newImage1 = (ImageView) view.findViewById(R.id.newImage1);
            Title1 = (TextView) view.findViewById(R.id.Title1);
            sendTime1 = (TextView) view.findViewById(R.id.sendTime1);

            newImage2 = (ImageView) view.findViewById(R.id.newImage2);
            Title2 = (TextView) view.findViewById(R.id.Title2);
            sendTime2 = (TextView) view.findViewById(R.id.sendTime2);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public interface callBack {
        void send(View v);
    }
}
