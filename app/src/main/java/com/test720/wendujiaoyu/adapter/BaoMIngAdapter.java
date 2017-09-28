package com.test720.wendujiaoyu.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.test720.wendujiaoyu.BaomingCallBack;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.application.MyApplication;
import com.test720.wendujiaoyu.entity.BaoMingUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test720.wendujiaoyu.R.id.delete;

/**
 * Created by LuoPan on 2017/8/22 15:52.
 */

public class BaoMIngAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BaoMingUser> mData = new ArrayList<>();// 存储的EditText值
    public Map<String, String> editorValue = new HashMap<String, String>();//
    List<String> zaiduLists;
    List<String> baoKaolists;
    BaomingCallBack callback;

    public BaoMIngAdapter(Context context, List<BaoMingUser> data, List<String> zaiduLists, List<String> baoKaolists, BaomingCallBack callback) {
        mData = data;
        mInflater = LayoutInflater.from(context);
        this.zaiduLists = zaiduLists;
        this.baoKaolists = baoKaolists;
        this.callback = callback;
        init();
    }

    // 初始化
    private void init() {
        editorValue.clear();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Integer index = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // convertView为null的时候初始化convertView。
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_baomingadd, null);
            holder.name = (EditText) convertView
                    .findViewById(R.id.EditName);
            holder.idCard = (EditText) convertView
                    .findViewById(R.id.EditName1);
            holder.phoneNumber = (EditText) convertView
                    .findViewById(R.id.EditName2);
            holder.commit = (Button) convertView.findViewById(R.id.commitOrder);

            holder.zaiDuYuanXiao = (MaterialSpinner) convertView.findViewById(R.id.EditName3);
            holder.baoKaoZhuanYe = (MaterialSpinner) convertView.findViewById(R.id.EditName4);
            if (zaiduLists.size() != 0 && baoKaolists.size() != 0) {
                holder.zaiDuYuanXiao.setItems(zaiduLists);
                holder.baoKaoZhuanYe.setItems(baoKaolists);
            }


            holder.idCard.setTag(position);
            holder.phoneNumber.setTag(position);
            holder.name.setTag(position);
            holder.zaiDuYuanXiao.setTag(position);
            holder.baoKaoZhuanYe.setTag(position);

            holder.delete = (ImageView) convertView.findViewById(delete);
            holder.add = (TextView) convertView.findViewById(R.id.add);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.name.setTag(position);
            holder.idCard.setTag(position);
            holder.phoneNumber.setTag(position);
            holder.zaiDuYuanXiao.setTag(position);
            holder.baoKaoZhuanYe.setTag(position);

        }
        holder.commit.setTag(position);

        if (position == 0) {
            holder.delete.setVisibility(View.GONE);
        } else {
            holder.delete.setVisibility(View.VISIBLE);
        }

        if (position == mData.size() - 1) {
            holder.commit.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.VISIBLE);
        } else {
            holder.add.setVisibility(View.INVISIBLE);
            holder.commit.setVisibility(View.GONE);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.add(new BaoMingUser());
                notifyDataSetChanged();


            }
        });


        final ViewHolder finalHolder = holder;

        holder.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < mData.size(); i++) {
                        if (mData.get(i).getName().trim().isEmpty() && mData.get(i).getName() != null) {
                            Toast.makeText(MyApplication.getContext(), "请填写姓名", Toast.LENGTH_SHORT).show();
                        } else if (mData.get(i).getUser_Idcard().trim().isEmpty() && mData.get(i).getUser_Idcard() != null) {
                            Toast.makeText(MyApplication.getContext(), "请填写身份证", Toast.LENGTH_SHORT).show();
                        } else if (mData.get(i).getPhone().trim().isEmpty() && mData.get(i).getPhone() != null||mData.get(i).getPhone().toString().trim().length()!=11) {
                            Toast.makeText(MyApplication.getContext(), "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                        } else if (mData.get(i).getStay_school() == null) {
                            Toast.makeText(MyApplication.getContext(), "请选择在读院校", Toast.LENGTH_SHORT).show();
                        } else if (mData.get(i).getBaokao_major() == null) {
                            Toast.makeText(MyApplication.getContext(), "请选择报考专业", Toast.LENGTH_SHORT).show();

                        } else {
                            if (((int) finalHolder.commit.getTag() == position) && (i == mData.size() - 1)) {
                                callback.CallBack(mData);
                            }
                        }

                    }

                } catch (NullPointerException e) {
                    Toast.makeText(MyApplication.getContext(), "请填写所有数据", Toast.LENGTH_SHORT).show();
                }
            }


        });

        class MyTextWatcher implements TextWatcher {
            public MyTextWatcher(ViewHolder holder) {
                mHolder = holder;
            }

            private ViewHolder mHolder;

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !"".equals(s.toString())) {
                    int position = (Integer) mHolder.name.getTag();
                    mData.get(position).setName(s.toString());// 当EditText数据发生改变的时候存到data变量中
                }
            }
        }
        class IDCardTextWather implements TextWatcher {
            public IDCardTextWather(ViewHolder holder) {
                mHolder = holder;
            }

            private ViewHolder mHolder;

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !"".equals(s.toString())) {
                    int position = (Integer) mHolder.idCard.getTag();
                    mData.get(position).setUser_Idcard(s.toString());// 当EditText数据发生改变的时候存到data变量中
                }
            }
        }
        class PhoneNumberWatcher implements TextWatcher {
            public PhoneNumberWatcher(ViewHolder holder) {
                mHolder = holder;
            }

            private ViewHolder mHolder;

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !"".equals(s.toString())) {
                    int position = (Integer) mHolder.phoneNumber.getTag();
                    mData.get(position).setPhone(s.toString());// 当EditText数据发生改变的时候存到data变量中
                }
            }
        }
        holder.name.addTextChangedListener(new MyTextWatcher(holder));
        holder.idCard.addTextChangedListener(new IDCardTextWather(holder));
        holder.phoneNumber.addTextChangedListener(new PhoneNumberWatcher(holder));

        String value = mData.get(position).getName();
        if (value != null)
            holder.name.setText(value);
        else
            holder.name.setText("");
        holder.name.clearFocus();
        if (index != -1 && index == position) {
            holder.name.requestFocus();
        }

        String idcard = mData.get(position).getUser_Idcard();
        if (idcard != null)
            holder.idCard.setText(idcard);
        else
            holder.idCard.setText("");
        holder.idCard.clearFocus();
        if (index != -1 && index == position) {
            holder.idCard.requestFocus();
        }

        String phone = mData.get(position).getPhone();
        if (phone != null)
            holder.phoneNumber.setText(phone);
        else
            holder.phoneNumber.setText("");
        holder.phoneNumber.clearFocus();
        if (index != -1 && index == position) {
            holder.phoneNumber.requestFocus();
        }

        holder.baoKaoZhuanYe.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position1, long id, Object item) {
                mData.get(position).setBaokao_major(baoKaolists.get(position1));
                Log.e("baoKaoZhuanYe", mData.get(position).getBaokao_major());
                Log.e("baoKaoZhuanYe++", item.toString());
            }
        });
        holder.zaiDuYuanXiao.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position1, long id, Object item) {
                mData.get(position).setStay_school(zaiduLists.get(position1));
                Log.e("TAG++", item.toString());
            }
        });

        if (mData.get(position).getStay_school() == null) {

            mData.get(position).setStay_school(zaiduLists.get(0));
        }
        if (mData.get(position).getBaokao_major() == null) {
            mData.get(position).setBaokao_major(baoKaolists.get(0));
        }

        return convertView;
    }

    public final class ViewHolder {
        public EditText name;
        public EditText idCard;
        public EditText phoneNumber;// ListView中的输入
        public ImageView delete;
        public TextView add;
        Button commit;
        MaterialSpinner zaiDuYuanXiao;
        MaterialSpinner baoKaoZhuanYe;

    }
}
