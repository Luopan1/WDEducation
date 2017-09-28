package com.test720.wendujiaoyu.study.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.study.bean.CurriculumClassificationListBean;
import com.test720.wendujiaoyu.study.utils.SizeUtils;
import com.test720.wendujiaoyu.study.view.SlidingButtonView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MJJ on 2015/7/25.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements SlidingButtonView.IonSlidingButtonListener {

    private Activity mContext;

    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private List<CurriculumClassificationListBean> mDatas;

    private SlidingButtonView mMenu = null;
    private SizeUtils sizeUtils;

    public Adapter(Activity context,List<CurriculumClassificationListBean> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
        sizeUtils = new SizeUtils(mContext);
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvTitle.setText(mDatas.get(position).getName());
        //设置内容布局的宽为屏幕宽度
        holder.layoutContent.getLayoutParams().width = sizeUtils.screenWidth();
        sizeUtils.setLayoutSizeHeight(holder.layoutBg, 100);
        sizeUtils.setTextSize(holder.tvTitle, 20);
        sizeUtils.setLayoutSizeWidht(holder.tvDelete, 100);
        holder.layoutContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }

            }
        });
        holder.tvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item, arg0, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.layout_right)
        RelativeLayout layoutRight;
        @BindView(R.id.layout_content)
        RelativeLayout layoutContent;
        @BindView(R.id.layout_bg)
        SlidingButtonView layoutBg;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ((SlidingButtonView) view).setSlidingButtonListener(Adapter.this);
        }
    }




    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);

    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        Log.i("asd", "mMenu为null");
        return false;
    }


    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);

        void onDeleteBtnCilck(View view, int position);
    }


}

