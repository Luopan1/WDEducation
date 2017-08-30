package com.test.com.study.adapter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.test.com.R;
import com.test.com.study.activity.ErrorAnswerActivity;
import com.test.com.study.activity.FileFormatActivity;
import com.test.com.study.activity.MajorChoiceActivity;
import com.test.com.study.activity.OldExamActivity;
import com.test.com.study.activity.VideoPlayActivity;
import com.test.com.study.bean.DownloadBean;
import com.test.com.study.bean.StudyHomeBanner;
import com.test.com.study.bean.StudyHomeErrorBean;
import com.test.com.study.bean.StudyHomeType;
import com.test.com.study.utils.BaseRecyclerAdapter;
import com.test.com.study.utils.BaseRecyclerHolder;
import com.test.com.study.utils.ItemAnimatorFactory;
import com.test.com.study.utils.RecycleViewDivider;
import com.test.com.study.utils.SizeUtils;
import com.test.com.study.view.DividerGridItemDecoration;
import com.test.com.utills.Constants;
import com.test.com.utills.NetImageLoaderHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2017/6/15.
 */

public class StudyHomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private List<Integer> results;

    private SizeUtils sizeUtils;
    //type
    public static final int TYPE_1 = 0xff01;
    public static final int TYPE_2 = 0xff02;
    public static final int TYPE_3 = 0xff03;
    public static final int TYPE_4 = 0xff04;
    public static final int TYPE_5 = 0xff05;
    public static final int TYPE_6 = 0xff06;
    private OnItemClickListener listener;//点击事件监听器
    private RecyclerView recyclerView;
    private List<StudyHomeBanner> studyHomeBannerList;
    private List<StudyHomeType> studyHomeTypeList;
    private List<StudyHomeErrorBean> studyHomeErrorBeanArrayList;
    private List<DownloadBean> downloadBeanList;
    private BaseRecyclerAdapter<StudyHomeType> adapter;
    private BaseRecyclerAdapter<StudyHomeErrorBean> wrongAdapter;
    private BaseRecyclerAdapter<DownloadBean> cacheAdapter;
    //在RecyclerView提供数据的时候调用
    fm.jiecao.jcvideoplayer_lib.SPUtils spUtils;

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public StudyHomeRecyclerViewAdapter(Activity context, List<Integer> results,List<StudyHomeBanner> studyHomeBannerList,List<StudyHomeType> studyHomeTypeList,List<StudyHomeErrorBean> studyHomeErrorBeanArrayList,List<DownloadBean> downloadBeanList) {
        this.context = context;
        this.results = results;
        this.studyHomeBannerList = studyHomeBannerList;
        this.studyHomeTypeList = studyHomeTypeList;
        this.studyHomeErrorBeanArrayList = studyHomeErrorBeanArrayList;
        this.downloadBeanList = downloadBeanList;
        sizeUtils = new SizeUtils(context);
        spUtils = new fm.jiecao.jcvideoplayer_lib.SPUtils(context);
        Log.e("results", results.toString() + results.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_1:
                return new BannerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_study, parent, false));
            case TYPE_2:
                return new ClassificationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classification_study, parent, false));
            case TYPE_3:
                return new WrongHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_head, parent, false));
            case TYPE_4:
                return new WrongListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_list, parent, false));
            case TYPE_5:
                return new CacheHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_head, parent, false));
            case TYPE_6:
                return new CacheListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_list, parent, false));
            default:
                Log.d("error", "viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerHolder) {
            getBannerData((BannerHolder) holder, position);
        } else if (holder instanceof ClassificationHolder) {
            getClassificationData((ClassificationHolder) holder, position);
        } else if (holder instanceof WrongHolder) {
            getWrongData((WrongHolder) holder, position);
        } else if (holder instanceof WrongListHolder) {
            getWrongListData((WrongListHolder) holder, position);
        } else if (holder instanceof CacheHolder) {
            getCacheData((CacheHolder) holder, position);
        } else if (holder instanceof CacheListHolder) {
            getCacheListData((CacheListHolder) holder, position);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null && view != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    listener.onItemClick(recyclerView, view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (results.get(position) == 1)
            return TYPE_1;
        else if (results.get(position) == 2)
            return TYPE_2;
        else if (results.get(position) == 3)
            return TYPE_3;
        else if (results.get(position) == 4)
            return TYPE_4;
        else if (results.get(position) == 5)
            return TYPE_5;
        else
            return TYPE_6;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_1:
                        case TYPE_2:
                        case TYPE_3:
                        case TYPE_4:
                        case TYPE_5:
                        case TYPE_6:
                            return gridManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }
    }

    private void getBannerData(BannerHolder holder, final int position) {
        sizeUtils.setLayoutSizeHeight(holder.CarouselImage, 399);
        List<String> imagsList = new ArrayList<>();
        for (int i = 0; i < studyHomeBannerList.size(); i++) {
            imagsList.add(Constants.ImagHost+studyHomeBannerList.get(i).getBa_img());
        }
        int[] dots = {R.mipmap.circle1, R.mipmap.circle2};
        holder.CarouselImage.setPointViewVisible(true);//设置小圆点可见
        holder.CarouselImage.setPageIndicator(dots);//设置小圆点
        holder.CarouselImage.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        holder.CarouselImage.setManualPageable(true);//手动滑动
        holder.CarouselImage.setPages(new CBViewHolderCreator<NetImageLoaderHolder>() {
            @Override
            public NetImageLoaderHolder createHolder() {
                return new NetImageLoaderHolder();
            }
        }, imagsList);

        if(!holder.CarouselImage.isTurning())
        {
            holder.CarouselImage.startTurning(3000);//自动轮滑
        }
    }
    private void getClassificationData(ClassificationHolder holder, final int position) {

        if(adapter == null)
        {
            adapter = new BaseRecyclerAdapter<StudyHomeType>(context, studyHomeTypeList, R.layout.item_classification_study_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, StudyHomeType item, int position, boolean isScrolling) {
//                    sizeUtils.setLayoutSize(holder.getView(R.id.layout_bg), sizeUtils.screenWidth() / 4, sizeUtils.screenWidth() / 4);
                    sizeUtils.setLayoutSize(holder.getView(R.id.iv_icon), 60, 60);
                    sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title), 17);
                    holder.setImageByUrl(R.id.iv_icon,Constants.ImagHost+studyHomeTypeList.get(position).getLogo());
                    holder.setText(R.id.tv_title,studyHomeTypeList.get(position).getName());
                }
            };
            adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    if(studyHomeTypeList.size()-1!=position)
                    {
                            if("".equals(spUtils.get("jihuo",Constants.uniqueness+"index","").toString()))
                            {
                                if("1".equals(studyHomeTypeList.get(position).getId()))
                                {
                                    context.startActivity(new Intent(context, MajorChoiceActivity.class).putExtra("type",studyHomeTypeList.get(position).getId()));
                                }
                                else
                                {
                                    Toast.makeText(context,"用户尚未激活",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                context.startActivity(new Intent(context, MajorChoiceActivity.class).putExtra("type",studyHomeTypeList.get(position).getId()));
                            }
                    }
                    else
                        context.startActivity(new Intent(context, OldExamActivity.class));
                }
            });
            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
            holder.myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
            holder.myRecyclerView.addItemDecoration(new DividerGridItemDecoration(context));
            holder.myRecyclerView.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }


    private void getWrongData(WrongHolder holder, final int position) {
        sizeUtils.setLayoutSizeHeight(holder.layoutBg,100);
        sizeUtils.setLayoutSize(holder.view,5,100-70);
        sizeUtils.setTextSize(holder.tvTitle,22);
        sizeUtils.setTextSize(holder.tvChakan,20);
        sizeUtils.setLayoutSize(holder.ivRight,20,20);
        holder.layoutTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!"".equals(spUtils.get("jihuo",Constants.uniqueness+"index","").toString()))
                        context.startActivity(new Intent(context, MajorChoiceActivity.class).putExtra("type","6"));
                    else Toast.makeText(context,"用户尚未激活",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getWrongListData(WrongListHolder holder, final int position) {
        wrongAdapter = new BaseRecyclerAdapter<StudyHomeErrorBean>(context, studyHomeErrorBeanArrayList, R.layout.item_study_list_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, StudyHomeErrorBean item, int position, boolean isScrolling) {
                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.layout_bg), 225);
                sizeUtils.setLayoutSize(holder.getView(R.id.iv_icon), 176, 174);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title), 22);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_time), 20);
                if (studyHomeErrorBeanArrayList.size() - 1 == position)
                    holder.getView(R.id.view).setVisibility(View.GONE);

                holder.setImageByUrl(R.id.iv_icon,Constants.ImagHost+studyHomeErrorBeanArrayList.get(position).getImg());
                holder.setText(R.id.tv_title,studyHomeErrorBeanArrayList.get(position).getDeails_id());
                holder.setText(R.id.tv_time,studyHomeErrorBeanArrayList.get(position).getTime());
            }

        };
        holder.myRecyclerView.setLayoutManager(new LinearLayoutManager(holder.myRecyclerView.getContext()));
        holder.myRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL));
        holder.myRecyclerView.setAdapter(wrongAdapter);
        wrongAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                context.startActivity(new Intent(context,ErrorAnswerActivity.class).putExtra("id",studyHomeErrorBeanArrayList.get(position).getQuestions_id()).putExtra("title",studyHomeErrorBeanArrayList.get(position).getTi_name()).putExtra("cid",studyHomeErrorBeanArrayList.get(position).getCourse_id()));
            }
        });



    }

    private void getCacheData(CacheHolder holder, final int position) {
        sizeUtils.setLayoutSizeHeight(holder.layoutBg,100);
        sizeUtils.setLayoutSize(holder.view,5,100-70);
        sizeUtils.setTextSize(holder.tvTitle,22);
        sizeUtils.setTextSize(holder.tvChakan,20);
        sizeUtils.setLayoutSize(holder.ivRight,20,20);
        holder.tvTitle.setText("我的缓存");
        holder.layoutTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(spUtils.get("jihuo",Constants.uniqueness+"index","").toString()))
                context.startActivity(new Intent(context, FileFormatActivity.class));
                else Toast.makeText(context,"用户尚未激活",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCacheListData(CacheListHolder holder, final int position) {
        final List<String> strings = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            strings.add("1");
        }
        cacheAdapter = new BaseRecyclerAdapter<DownloadBean>(context, downloadBeanList, R.layout.item_study_list_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, DownloadBean item, int position, boolean isScrolling) {
                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.layout_bg), 225);
                sizeUtils.setLayoutSize(holder.getView(R.id.iv_icon), 176, 174);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title), 22);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_time), 20);
                if (strings.size() - 1 == position)
                    holder.getView(R.id.view).setVisibility(View.GONE);
                holder.setText(R.id.tv_title,downloadBeanList.get(position).getTitle());
                holder.setText(R.id.tv_time,downloadBeanList.get(position).getTime());

                ImageView ivIcion = holder.getView(R.id.iv_icon);
                MediaMetadataRetriever media = new MediaMetadataRetriever();
                media.setDataSource(downloadBeanList.get(position).getSrc());
                ivIcion.setImageBitmap(media.getFrameAtTime());
            }
        };
        holder.myRecyclerView.setLayoutManager(new LinearLayoutManager(holder.myRecyclerView.getContext()));
        holder.myRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL));
        holder.myRecyclerView.setAdapter(cacheAdapter);
        cacheAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                context.startActivity(new Intent(context, VideoPlayActivity.class).putExtra("url", downloadBeanList.get(position).getSrc()).putExtra("title",downloadBeanList.get(position).getTitle()));
            }
        });
    }


    static class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.CarouselImage)
        ConvenientBanner CarouselImage;

        BannerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class ClassificationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        RecyclerView myRecyclerView;

        ClassificationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class WrongHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view)
        View view;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_chakan)
        TextView tvChakan;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.layout_to)
        LinearLayout layoutTo;
        @BindView(R.id.layout_bg)
        RelativeLayout layoutBg;

        WrongHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class WrongListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        RecyclerView myRecyclerView;

        WrongListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class CacheHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view)
        View view;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_chakan)
        TextView tvChakan;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.layout_to)
        LinearLayout layoutTo;
        @BindView(R.id.layout_bg)
        RelativeLayout layoutBg;

        CacheHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class CacheListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        RecyclerView myRecyclerView;

        CacheListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
