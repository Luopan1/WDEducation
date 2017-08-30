package com.test.com.fragements;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.SharedInterFace;
import com.test.com.UrlFactory;
import com.test.com.activity.LoginActivity;
import com.test.com.application.MyApplication;
import com.test.com.baseUi.BaseFragment;
import com.test.com.entity.UserInfo;
import com.test.com.fourthFragementActivity.AboutWenDuEducationActivity;
import com.test.com.fourthFragementActivity.ChangePassWorldActivity;
import com.test.com.fourthFragementActivity.DaiJinQuanActivity;
import com.test.com.fourthFragementActivity.MyOrderActivity;
import com.test.com.fourthFragementActivity.PersonalSettingActivity;
import com.test.com.study.activity.FileFormatActivity;
import com.test.com.study.activity.MajorChoiceActivity;
import com.test.com.utills.CircleImageView;
import com.test.com.utills.Constants;
import com.test.com.utills.DataCleanManager;
import com.test.com.utills.DensityUtil;
import com.test.com.utills.SPUtils;

import static com.test.com.R.id.userCicleImage;


/**
 * Created by LuoPan on 2017/8/1 10:15.
 */

public class FourthFragment extends BaseFragment implements View.OnClickListener {

    static SharedInterFace mSharedInterFace;
    private CircleImageView mUserImageView;
    private ImageView mTitleRelative;
    private TextView useRNickName;
    private RelativeLayout mVideoCacheRelative;
    private RelativeLayout layoutCuoti;
    private TwinklingRefreshLayout refreshLayout;
    private RelativeLayout mAboutWenDuEducation;
    private RelativeLayout mChangePwdRelative;
    private TextView mTv_cache;
    private RelativeLayout mClearChacheRelative;
    private Button mCheckout;
    private RelativeLayout mDaiJinQuanRelative;
    private RelativeLayout mJiHUoRelative;
    private PopupWindow mPopWindow;
    private TextView mStatus;
    private RelativeLayout mShareRelative;
    private RelativeLayout MyOrder;
    private Dialog mLoadingDialog;


    public FourthFragment() {

    }

    public static final FourthFragment newInstance(SharedInterFace sharedInterFace) {
        mSharedInterFace = sharedInterFace;
        FourthFragment fourthFragment = new FourthFragment();
        return fourthFragment;
    }


    @Override

    protected void initView() {
        refreshLayout = getView(R.id.refreshLayout);
        mUserImageView = getView(userCicleImage);
        mUserImageView.setBorderColor(Color.parseColor("#E0DBDA"));
        mUserImageView.setBorderWidth(DensityUtil.dip2px(getActivity(), 5));
        mTitleRelative = getView(R.id.titleImageView);
        useRNickName = getView(R.id.userNickName);
        mUserImageView.bringToFront();
        mVideoCacheRelative = getView(R.id.VideoCacheRelative);
        layoutCuoti = getView(R.id.layout_cuoti);
        mAboutWenDuEducation = getView(R.id.aboutWenDuEducation);
        mChangePwdRelative = getView(R.id.changePwdRelative);
        mTv_cache = getView(R.id.tv_cache);
        mClearChacheRelative = getView(R.id.clearChacheRelative);
        mCheckout = getView(R.id.checkout);
        MyOrder = getView(R.id.MyOrder);
        mDaiJinQuanRelative = getView(R.id.DaijinquanRelative);
        mJiHUoRelative = getView(R.id.jihuoStatue);
        mStatus = getView(R.id.status);
        mShareRelative = getView(R.id.ShareRelative);



    }


    @Override
    protected void initData() {
        Glide.with(getActivity()).load("http://pic44.huitu.com/res/20151214/800605_20151214154921103500_1.jpg").asBitmap().centerCrop().into(mTitleRelative);
        //        useRNickName.setText(MyApplication.getDataBase().getUserName());

        try {
            String cacheSize = DataCleanManager.getCacheSize(getActivity().getExternalCacheDir());
            Log.i("TAG", cacheSize);
            if (cacheSize.equals("0.0B")) {
                mTv_cache.setText(DataCleanManager.getCacheSize(getActivity().getCacheDir()));
            } else {
                mTv_cache.setText(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置刷新头
       /* SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.zhuanquan);
        headerView.setTextColor(0xff745D5C);
        refreshLayout.setHeaderView(headerView);*/
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!MyApplication.getDataBase().getToken().isEmpty()) {
                    refreshLayout.startRefresh();
                }
            }
        }, 4000);


    }

    @Override
    protected void setListener() {
        mUserImageView.setOnClickListener(this);
        mVideoCacheRelative.setOnClickListener(this);
        mAboutWenDuEducation.setOnClickListener(this);
        mChangePwdRelative.setOnClickListener(this);
        mClearChacheRelative.setOnClickListener(this);
        mCheckout.setOnClickListener(this);
        mDaiJinQuanRelative.setOnClickListener(this);
        mJiHUoRelative.setOnClickListener(this);
        mShareRelative.setOnClickListener(this);
        MyOrder.setOnClickListener(this);
        layoutCuoti.setOnClickListener(this);
        useRNickName.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                if (SPUtils.getCount().trim().isEmpty() && SPUtils.getUnionid().trim().isEmpty()) {
                    refreshLayout.finishRefreshing();
                } else if (MyApplication.getDataBase().getToken().isEmpty()) {
                    if (!SPUtils.getCount().trim().isEmpty()) {
                        RequestParams params = new RequestParams();
                        params.put("username", SPUtils.getCount());
                        params.put("password", SPUtils.getPWD());
                        getDataFromInternet(UrlFactory.Login, params, 0);


                    } else if (!SPUtils.getUnionid().isEmpty()) {
                        Log.e("TAG++++", "getUnionid");
                        RequestParams params = new RequestParams();
                        params.put("unionid", SPUtils.getUnionid());
                        getDataFromInternet(UrlFactory.appWeChatLogin, params, 1);
                    }

                } else {
                    if (!MyApplication.getDataBase().getToken().isEmpty()) {
                        RequestParams requestParams = new RequestParams();
                        getDataFromInternet(UrlFactory.getUserInfo, requestParams, 3);
                    } else {
                        Glide.with(getActivity()).load(R.mipmap.ic_launcher_round).asBitmap().into(mUserImageView);
                    }
                }


            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
                if ((!"".equals(spUtils.get("jihuo", Constants.uniqueness + "index", ""))) && (!MyApplication.getDataBase().getToken().trim().isEmpty())) {

                    mStatus.setText("已激活");
                } else {
                    mStatus.setText("未激活");
                }
                if (!MyApplication.getDataBase().getToken().isEmpty()) {
                    mCheckout.setText("退出登录");
                    mCheckout.setBackground(getResources().getDrawable(R.drawable.gray_color_corn5));
                } else {
                    mCheckout.setText("登录");
                    useRNickName.setText("点击登录");
                    mCheckout.setBackground(getResources().getDrawable(R.drawable.base_color_corn5));
                    mCheckout.setTextColor(getResources().getColor(R.color.white));
                }

                if ((!"".equals(spUtils.get("jihuo", Constants.uniqueness + "index", ""))) && (!MyApplication.getDataBase().getToken().trim().isEmpty())) {

                    mStatus.setText("已激活");
                } else {
                    mStatus.setText("未激活");
                }


            }
        });

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fourth_fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_cuoti:
                if (isLogined())
                    startActivity(new Intent(getActivity(), MajorChoiceActivity.class).putExtra("type", "6"));
                break;
            case R.id.userCicleImage:
                if (isLogined())
                    jumpToActivity(PersonalSettingActivity.class, false);
                break;
            case R.id.VideoCacheRelative:
                if (isLogined())
                    startActivity(new Intent(getActivity(), FileFormatActivity.class));
                //                jumpToActivity(CacheActivity.class, false);
                break;
            case R.id.aboutWenDuEducation:

                jumpToActivity(AboutWenDuEducationActivity.class, false);
                break;
            case R.id.changePwdRelative:
                if (isLogined())
                    jumpToActivity(ChangePassWorldActivity.class, false);
                break;
            case R.id.clearChacheRelative:
                try {
                    String cacheSize = DataCleanManager.getCacheSize(getActivity().getExternalCacheDir());
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        DataCleanManager.deleteFolderFile(getActivity().getExternalCacheDir().getAbsolutePath(), true);
                    }
                    DataCleanManager.deleteFolderFile(getActivity().getCacheDir().getPath(), true);
                    mTv_cache.setText(cacheSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.checkout:
                if (isLogined()) {
                    showBackPopWindow();
                }


                break;
            case R.id.DaijinquanRelative:
                if (isLogined())
                    jumpToActivity(DaiJinQuanActivity.class, false);
                break;
            case R.id.jihuoStatue:
                if (isLogined())
                    showPopWindow();
                break;
            case R.id.ShareRelative:
                mSharedInterFace.sendShared();
                break;
            case R.id.MyOrder:
                if (isLogined()) {
                    jumpToActivity(MyOrderActivity.class, false);
                }
                break;
            case R.id.userNickName:
                if (isLogined()) {

                }
                break;
        }
    }

    public void showBackPopWindow() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.check_out_pop, null);// 得到加载view
        // 取消
        TextView textViewContinue = (TextView) v.findViewById(R.id.comtinue);
        //退出
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
                MyApplication.getDataBase().setToken("");
                SPUtils.saveUserInfo("", "");
                SPUtils.setUnionid("");
                Glide.with(getActivity()).load(R.mipmap.ic_launcher_round).asBitmap().into(mUserImageView);
                jumpToActivity(LoginActivity.class, false);

            }
        });

        // 创建自定义样式dialog
        mLoadingDialog = new Dialog(getActivity(), R.style.DialogStyle);
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

    @Override
    public void onResume() {

        // TODO: 2017/8/21  开启刷新去获取头像和昵称
        Log.e(TAG, "onResume");
        refreshLayout.startRefresh();
        super.onResume();


    }

    void showPopWindow() {
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(getActivity(), R.layout.jihuo_popwindows, null);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        mPopWindow = new PopupWindow(popView, width, height);

        TextView cancle = (TextView) popView.findViewById(R.id.cancle);
        TextView confirm = (TextView) popView.findViewById(R.id.confirm);

        final EditText et_cardNumber = (EditText) popView.findViewById(R.id.et_cardNumber);
        final EditText et_passwrold = (EditText) popView.findViewById(R.id.et_Passworld);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getActivity())) {
                    RequestParams params = new RequestParams();
                    params.put("number", et_cardNumber.getText().toString().trim());
                    params.put("password", et_passwrold.getText().toString().trim());
                    params.put("uuid", SPUtils.getUUID());
                    getDataFromInternet(UrlFactory.activationMajor, params, 2);
                    showLoadingDialog("激活中");

                }
            }
        });
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 2:
                // TODO: 2017/8/24  存贮后台返回的激活账号
                Log.e("TAG++jihui", object.toString());
                if (object.getInteger("code") == 1) {
                    showSuccessDialog();
                    ShowToast(object.getString("msg"));

                    String code = object.getString("data");
                    mStatus.setText("已激活");
                    spUtils.put("jihuo", Constants.uniqueness + "index", code);
                    //
                    //                spUtils.get("jihuo","index","").toString()    这个状态是获取激活码
                    mPopWindow.dismiss();
                    showSuccessDialog();

                }


                break;

            case 0:
                /**登錄*/
                JSONObject jsonObject = JSON.parseObject(object.toString());
                if (jsonObject.getInteger("code").equals(1)) {
                    Log.e("TAG+++passworld", "登录成功");
                    String token = jsonObject.getJSONObject("data").getString("token");
                    MyApplication.getDataBase().setToken(token);
                    // TODO: 2017/8/11 uuid不保存在sp中

                    String uuid = object.getJSONObject("data").getString("uuid");
                    MyApplication.userBaseInfo.setUuid(uuid);
                    Constants.uniqueness = uuid;
                    ShowToast(object.getString("msg"));


                    /**登录成功后再去改变UI*/
                    if (!MyApplication.getDataBase().getToken().isEmpty()) {
                        mCheckout.setText("退出登录");
                        mCheckout.setBackground(getResources().getDrawable(R.drawable.gray_color_corn5));
                    } else {
                        mCheckout.setText("登录");
                        useRNickName.setText("点击登录");
                        mCheckout.setBackground(getResources().getDrawable(R.drawable.base_color_corn5));
                        mCheckout.setTextColor(getResources().getColor(R.color.white));
                    }

                    if ((!"".equals(spUtils.get("jihuo", Constants.uniqueness + "index", ""))) && (!MyApplication.getDataBase().getToken().trim().isEmpty())) {

                        mStatus.setText("已激活");
                    } else {
                        mStatus.setText("未激活");
                    }
                    refreshLayout.finishRefreshing();

                } else {
                    Log.e("TAG++passworld", "登录失败");
                    MyApplication.getDataBase().setToken(" ");
                }


                if (!MyApplication.getDataBase().getToken().isEmpty()) {
                    mCheckout.setText("退出登录");
                    mCheckout.setBackground(getResources().getDrawable(R.drawable.gray_color_corn5));
                } else {
                    mCheckout.setText("登录");
                    useRNickName.setText("点击登录");
                    mCheckout.setBackground(getResources().getDrawable(R.drawable.base_color_corn5));
                    mCheckout.setTextColor(getResources().getColor(R.color.white));
                }

                break;
            case 1:
                /**自動登錄*/
                jsonObject = JSON.parseObject(object.toString());
                if (jsonObject.getInteger("code").equals(1)) {
                    String token = jsonObject.getJSONObject("data").getString("token");
                    MyApplication.getDataBase().setToken(token);
                    // TODO: 2017/8/11 uuid不保存在sp中

                    String uuid = object.getJSONObject("data").getString("uuid");
                    MyApplication.userBaseInfo.setUuid(uuid);
                    Constants.uniqueness = uuid;

                    ShowToast(object.getString("msg"));
                }
                refreshLayout.finishRefreshing();


                if (!MyApplication.getDataBase().getToken().isEmpty()) {
                    mCheckout.setText("退出登录");
                    mCheckout.setBackground(getResources().getDrawable(R.drawable.gray_color_corn5));
                } else {
                    mCheckout.setText("登录");
                    useRNickName.setText("点击登录");
                    mCheckout.setBackground(getResources().getDrawable(R.drawable.base_color_corn5));
                    mCheckout.setTextColor(getResources().getColor(R.color.white));
                }

                break;
            case 3:
                /**獲取用戶信息*/
                Log.e("personalsetting", object.toString());
                refreshLayout.finishRefreshing();
                if (object.getInteger("code") == 1) {
                    Gson gson = new Gson();
                    UserInfo user = gson.fromJson(object.toString(), UserInfo.class);
                    useRNickName.setText(user.getData().getUserInfo().getNickname());
                    MyApplication.getDataBase().setUuid(user.getData().getUserInfo().getUuid());
                    Constants.uniqueness = user.getData().getUserInfo().getUuid();

                    if ((!"".equals(spUtils.get("jihuo", Constants.uniqueness + "index", ""))) && (!MyApplication.getDataBase().getToken().trim().isEmpty())) {

                        mStatus.setText("已激活");
                    } else {
                        mStatus.setText("未激活");
                    }

                    Glide.with(getActivity()).load(UrlFactory.imagePath + user.getData().getUserInfo().getHeadimg()).asBitmap().into(mUserImageView);
                }

                break;


        }
    }


    void showSuccessDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.jihuo_hint_pop, null);// 得到加载view
        Button confirm = (Button) v.findViewById(R.id.confirm);

        // 创建自定义样式dialog
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogStyle);
        dialog.setCancelable(true); // 是否可以按“返回键”消失
        dialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        dialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        dialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}


