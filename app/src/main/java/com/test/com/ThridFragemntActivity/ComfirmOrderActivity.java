package com.test.com.ThridFragemntActivity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.adapter.CommonAdaper;
import com.test.com.adapter.ViewHolder;
import com.test.com.alipay.CallBack;
import com.test.com.alipay.PayMain;
import com.test.com.alipay.PayResult;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.baseUi.MListView;
import com.test.com.entity.BaoMIngCuXiaoMa;
import com.test.com.entity.BaoMingDaijinQuan;
import com.test.com.entity.BaoMingUser;
import com.test.com.entity.OrderChanger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ComfirmOrderActivity extends BaseToolbarActivity {
    @BindView(R.id.BaomingInfoListView)
    MListView baoMingInfoListView;

    @BindView(R.id.MoneyText)
    TextView mMoneyText;
    @BindView(R.id.zhifubao)
    RadioButton mZhifubao;
    @BindView(R.id.weixin)
    RadioButton mWeixin;
    @BindView(R.id.tv_youhui)
    TextView mTvYouhui;
    @BindView(R.id.commitBuy)
    TextView mCommitBuy;

    @BindView(R.id.totalMoney)
    TextView totalMoney;

    @BindView(R.id.DaijinquanRelative)
    RelativeLayout DaijinquanRelative;
    @BindView(R.id.CuXiaoMaRenaltive)
    RelativeLayout CuXiaoMaRelative;
    @BindView(R.id.daijunqunName)
    TextView daijinquanName;
    @BindView(R.id.cuxiaomaName)
    TextView cuxiaomaName;
    private Dialog mLoadingDialog;
    private List<Integer> mLists;
    private List<BaoMingUser> mBaoMingUserList = new ArrayList<>();
    private String mDid;
    private String mPrice = "";

    private int PayMethod = 1;//支付默认是1  支付宝支付 2是微信支付
    /**
     * 1是使用代金券   2是使用促销码
     */
    private int useCode = 0;
    private List<OrderChanger.DataBean.UserListBean> mOrderchangerLists;
    private BaoMingDaijinQuan.DataBean.DaijinBean mDaijinQuan;
    private BaoMIngCuXiaoMa.DataBean.CuxiaoBean mCuxiaoma;


    @Override
    protected int getContentView() {
        return R.layout.activity_comfirm_order;
    }

    @Override
    protected void initData() {


    }

    private void setAdapter() {
        CommonAdaper adaper = new CommonAdaper<BaoMingUser>(this, mBaoMingUserList, R.layout.item_commitirder) {

            @Override
            public void convert(ViewHolder holder, BaoMingUser item, int position) {
                holder.setText(R.id.number, "报名用户" + (position + 1) + "的基本信息");
                holder.setText(R.id.name, item.getName());
                holder.setText(R.id.idCard, item.getUser_Idcard());
                holder.setText(R.id.phoneNumber, item.getPhone());
                holder.setText(R.id.zaiduyuanxiao, item.getStay_school());
                holder.setText(R.id.baokaozhuanye, item.getBaokao_major());
            }
        };
        baoMingInfoListView.setAdapter(adaper);

    }

    @Override
    protected void setListener() {
        DaijinquanRelative.setOnClickListener(this);
        CuXiaoMaRelative.setOnClickListener(this);
        mCommitBuy.setOnClickListener(this);
        mWeixin.setOnClickListener(this);
        mZhifubao.setOnClickListener(this);
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
        isShowToolBar = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DaijinquanRelative:
                Intent intent = new Intent(this, DaiJinQuanActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.CuXiaoMaRenaltive:
                intent = new Intent(this, CuXiaoMaActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.zhifubao:
                PayMethod = 1;
                break;
            case R.id.weixin:
                PayMethod = 2;
                break;

            case R.id.commitBuy:
                RequestParams params = new RequestParams();
                params.put("did", mDid);
                if (useCode == 1) {
                    params.put("couponId", mDaijinQuan.getCoupon_id());
                } else if (useCode == 2) {
                    params.put("codeId", mCuxiaoma.getId());
                }
                if (PayMethod == 1) {
                    params.put("type", 0);
                } else if (PayMethod == 2) {
                    params.put("type", 1);
                }
                Log.e("TAG++params+", params.toString());
                getDataFromInternet(UrlFactory.uporder, params, 1);
                showLoadingDialog();
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == 3) {
                useCode = 1;
                mDaijinQuan = (BaoMingDaijinQuan.DataBean.DaijinBean) data.getSerializableExtra("daijinquan");
                daijinquanName.setText("面值￥" + mDaijinQuan.getMoney());
                cuxiaomaName.setText("");
                if (mBaoMingUserList.size() != 0) {
                    mTvYouhui.setText("已优惠￥0");
                    mTvYouhui.setText("已优惠￥" + mDaijinQuan.getMoney() + "×1");
                    totalMoney.setText("合计￥" + ((Double.parseDouble(mPrice) * mBaoMingUserList.size()) - Double.parseDouble(mDaijinQuan.getMoney())));
                } else if (mOrderchangerLists.size() != 0) {
                    mTvYouhui.setText("已优惠￥" + mDaijinQuan.getMoney() + "×1");
                    totalMoney.setText("合计￥" + ((Double.parseDouble(mPrice) * mOrderchangerLists.size()) - Double.parseDouble(mDaijinQuan.getMoney())));

                }
            }
            if (resultCode == 4) {
                useCode = 2;
                mCuxiaoma = (BaoMIngCuXiaoMa.DataBean.CuxiaoBean) data.getSerializableExtra("cuxiaoma");
                daijinquanName.setText("");
                cuxiaomaName.setText("报任何科目只需￥" + mCuxiaoma.getMoney());
                if (mBaoMingUserList.size() != 0) {

                    totalMoney.setText("合计￥" + (Double.parseDouble(mCuxiaoma.getMoney())) * (mBaoMingUserList.size()));
                    /**促销码是所有人*/
                    mTvYouhui.setText("已优惠￥" + (Double.parseDouble(mPrice) - Double.parseDouble(mCuxiaoma.getMoney()) + "x" + mBaoMingUserList.size()));
                } else if (mOrderchangerLists.size() != 0) {
                    totalMoney.setText("合计￥" + (Double.parseDouble(mCuxiaoma.getMoney())) * (mOrderchangerLists.size()));
                    mTvYouhui.setText("已优惠￥" + (Double.parseDouble(mPrice) - Double.parseDouble(mCuxiaoma.getMoney()) + "x" + mOrderchangerLists.size()));

                }
            }
            /**如果useCode=1 说明使用的是代金券 useCode=2 说明使用的是促销码 传递的时候就根据useCode的不同传递*/
        }
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent.getIntExtra("type", 0) == 1) {
            mPrice = intent.getStringExtra("price");
            mDid = intent.getStringExtra("did");
            mBaoMingUserList = (List<BaoMingUser>) intent.getSerializableExtra("data");
            mMoneyText.setText(mBaoMingUserList.size() + "×" + mPrice);
            totalMoney.setText("￥" + (Double.parseDouble(mPrice) * mBaoMingUserList.size()));
            setAdapter();
        } else if (intent.getIntExtra("type", 0) == 2) {
            // TODO: 2017/8/23  根据订单的id获取信息
            mDid = intent.getStringExtra("did");
            RequestParams params = new RequestParams();
            params.put("did", mDid);
            getDataFromInternet(UrlFactory.orderDetail, params, 0);
            showLoadingDialog();


        }
        initToobar("确认订单", R.mipmap.fanhui);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Gson gson = new Gson();
                OrderChanger orderChanger = gson.fromJson(object.toString(), OrderChanger.class);
                mPrice = orderChanger.getData().getOld_price();

                mMoneyText.setText(orderChanger.getData().getUserList().size() + "×" + mPrice);
                mOrderchangerLists = new ArrayList<>();
                mOrderchangerLists.addAll(orderChanger.getData().getUserList());
                totalMoney.setText("合计￥" + (mOrderchangerLists.size() * Double.parseDouble(mPrice)));

                setOrderChangerAdapter();

                Log.e("TAG+++", object.toString());
                break;
            case 1:
                if (object.getInteger("code") == 1) {
                    String orderNumber = object.getJSONObject("data").getString("dingdanhao");
                    Log.e("TAG+order", orderNumber);
                    if (PayMethod == 1) {
                        /**支付宝支付*/
                        String allNeedMoney = totalMoney.getText().toString().trim();
                        int starIndex = allNeedMoney.lastIndexOf("￥");
                        String price = allNeedMoney.substring(starIndex + 1, allNeedMoney.length());
                        Log.e("prices", price + "++++");
                        PayMain payMain = new PayMain(ComfirmOrderActivity.this, callBack, UrlFactory.Syntony);
                        // TODO: 2017/8/24 更改金额
                        payMain.Pay(price, orderNumber);

                    } else if (PayMethod == 2) {
                        /**微信支付*/
                    }
                }
                break;
        }
    }

    private void setOrderChangerAdapter() {
        CommonAdaper adaper = new CommonAdaper<OrderChanger.DataBean.UserListBean>(this, mOrderchangerLists, R.layout.item_commitirder) {

            @Override
            public void convert(ViewHolder holder, OrderChanger.DataBean.UserListBean item, int position) {
                holder.setText(R.id.number, "报名用户" + (position + 1) + "的基本信息");
                holder.setText(R.id.name, item.getName());
                holder.setText(R.id.idCard, item.getUser_idcard());
                holder.setText(R.id.phoneNumber, item.getPhone());
                holder.setText(R.id.zaiduyuanxiao, item.getStay_school());
                holder.setText(R.id.baokaozhuanye, item.getBaokao_major());
            }
        };
        baoMingInfoListView.setAdapter(adaper);

    }

    @Override
    public void LeftOnClick() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            } else {
                mLoadingDialog.show();
            }
        } else {
            showBackPopWindow();
        }

    }

    @Override
    public void onBackPressed() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            } else {
                mLoadingDialog.show();
            }
        } else {
            showBackPopWindow();
        }

    }

    public void showBackPopWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.comfiem_back_pop_win, null);// 得到加载view
        TextView textViewContinue = (TextView) v.findViewById(R.id.comtinue);
        TextView textViewCancle = (TextView) v.findViewById(R.id.cancle);

        textViewContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingDialog.dismiss();
            }
        });
        textViewCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingDialog.dismiss();
                finish();
            }
        });

        // 创建自定义样式dialog
        mLoadingDialog = new Dialog(this, R.style.DialogStyle);
        mLoadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        mLoadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        mLoadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = mLoadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.AnimBottom);
        mLoadingDialog.show();
    }


    CallBack callBack = new CallBack(mActivity) {

        @Override
        public void call(int what, Object obj) {
            // TODO Auto-generated method stub
        }

        @Override
        public void call(PayResult payResult) {
            // TODO Auto-generated method stub

            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                ShowToast("支付成功");
                jumpToActivity(BuySuccessActivity.class, true);
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                ShowToast("付款失败");
                Log.e("order", resultInfo + "+");
            }

        }
    };


}
