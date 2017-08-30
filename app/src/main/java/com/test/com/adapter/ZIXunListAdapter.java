package com.test.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.WebViewActivity;
import com.test.com.entity.ZiXun;

import java.util.List;


/**
 * Created by LuoPan on 2017/7/28.
 */

public class ZIXunListAdapter extends BaseAdapter {
    private Context mContext;
    List<ZiXun> lists;
    Holder holder;
    private callBack mcallback;

    public ZIXunListAdapter(Context context, List<ZiXun> lists, callBack callback) {
        this.mContext = context;
        this.lists = lists;
        this.mcallback = callback;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_zixunlistview, null);
            holder = new Holder(view);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        if (i == 0) { //

            final List<ZiXun.Understand> college = lists.get(0).getData().getUnderstand();
            if (college.size() == 0) {
                holder.allRl.setVisibility(View.GONE);
            } else {
                holder.kind.setText("了解自考");

                if (college.size() == 1) {
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    holder.Title.setText(college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.thirdRelative.setVisibility(View.GONE);
                    holder.forthRelative.setVisibility(View.GONE);
                    holder.hengxian.setVisibility(View.GONE);

                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });


                } else if (college.size() == 2) {
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(1).getImg()).asBitmap().into(holder.newImage1);

                    holder.Title.setText(college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.Title1.setText(college.get(1).getZikao_title());
                    holder.sendTime1.setText(college.get(1).getTime());
                    holder.forthRelative.setVisibility(View.GONE);
                    holder.hengxian1.setVisibility(View.GONE);

                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });
                    holder.thirdRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(1).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });


                } else if (college.size() == 3) {
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(1).getImg()).asBitmap().into(holder.newImage1);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(2).getImg()).asBitmap().into(holder.newImage2);
                    holder.Title.setText(college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.Title1.setText(college.get(1).getZikao_title());
                    holder.sendTime1.setText(college.get(1).getTime());
                    holder.Title2.setText(college.get(2).getZikao_title());
                    holder.sendTime2.setText(college.get(2).getTime());


                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });
                    holder.thirdRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(1).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });

                    holder.forthRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(2).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });

                }
            }
        } else if (i == 1) { //

            final List<ZiXun.College> college = lists.get(0).getData().getCollege();
            if (college.size() == 0) {
                holder.allRl.setVisibility(View.GONE);
            } else {
                holder.kind.setText("自考专业");

                if (college.size() == 1) {
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    holder.Title.setText(college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });


                    holder.thirdRelative.setVisibility(View.GONE);
                    holder.forthRelative.setVisibility(View.GONE);
                    holder.hengxian.setVisibility(View.GONE);
                } else if (college.size() == 2) {
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(1).getImg()).asBitmap().into(holder.newImage1);
                    holder.Title.setText(college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.Title1.setText(college.get(1).getZikao_title());
                    holder.sendTime1.setText(college.get(1).getTime());
                    holder.forthRelative.setVisibility(View.GONE);
                    holder.hengxian1.setVisibility(View.GONE);

                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);

                        }
                    });
                    holder.thirdRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(1).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });


                } else if (college.size() == 3) {
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(1).getImg()).asBitmap().into(holder.newImage1);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(2).getImg()).asBitmap().into(holder.newImage2);
                    holder.Title.setText(college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.Title1.setText(college.get(1).getZikao_title());
                    holder.sendTime1.setText(college.get(1).getTime());
                    holder.Title2.setText(college.get(2).getZikao_title());
                    holder.sendTime2.setText(college.get(2).getTime());


                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });
                    holder.thirdRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(1).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });

                    holder.forthRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(2).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });

                }
            }
        } else if (i == 2) { //
            final List<ZiXun.Join> college = lists.get(0).getData().getJoin();
            if (college.size() == 0) {
                holder.allRl.setVisibility(View.GONE);
            } else {
                holder.kind.setText("加入我们");

                if (college.size() == 1) {
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    holder.Title.setText(UrlFactory.imagePath + college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.thirdRelative.setVisibility(View.GONE);
                    holder.forthRelative.setVisibility(View.GONE);
                    holder.hengxian.setVisibility(View.GONE);
                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });

                } else if (college.size() == 2) {
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(1).getImg()).asBitmap().into(holder.newImage1);
                    holder.Title.setText(college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.Title1.setText(college.get(1).getZikao_title());
                    holder.sendTime1.setText(college.get(1).getTime());
                    holder.forthRelative.setVisibility(View.GONE);
                    holder.hengxian1.setVisibility(View.GONE);

                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });
                    holder.thirdRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(1).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });

                } else if (college.size() == 3) {

                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(0).getImg()).asBitmap().into(holder.newImage);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(1).getImg()).asBitmap().into(holder.newImage1);
                    Glide.with(mContext).load(UrlFactory.imagePath + college.get(2).getImg()).asBitmap().into(holder.newImage2);
                    holder.Title.setText(college.get(0).getZikao_title());
                    holder.sendTime.setText(college.get(0).getTime());
                    holder.Title1.setText(college.get(1).getZikao_title());
                    holder.sendTime1.setText(college.get(1).getTime());
                    holder.Title2.setText(college.get(2).getZikao_title());
                    holder.sendTime2.setText(college.get(2).getTime());

                    holder.secondRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(0).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });
                    holder.thirdRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(1).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });

                    holder.forthRelative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + college.get(2).getId());
                            intent.putExtra(WebViewActivity.TITLE, "详情");
                            mContext.startActivity(intent);
                        }
                    });
                }
            }
        }
        holder.seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallback.send(view, i);
            }
        });
        return view;
    }

    private static class Holder {
        RelativeLayout secondRelative;
        RelativeLayout thirdRelative;
        RelativeLayout forthRelative;
        RelativeLayout allRl;
        View mView;
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

        TextView hengxian;
        TextView hengxian1;

        public Holder(View view) {
            hengxian = (TextView) view.findViewById(R.id.hengxian);
            hengxian1 = (TextView) view.findViewById(R.id.hengxian1);
            vertivalLine = (TextView) view.findViewById(R.id.vertivalLine);
            allRl = (RelativeLayout) view.findViewById(R.id.all_rl);

            secondRelative = (RelativeLayout) view.findViewById(R.id.secondRelative);
            thirdRelative = (RelativeLayout) view.findViewById(R.id.thirdRelative);
            forthRelative = (RelativeLayout) view.findViewById(R.id.forthRelative);


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

    public interface callBack {
        void send(View v, int position);
    }
}
