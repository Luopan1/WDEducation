package com.test.com.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.loopj.android.http.RequestParams;
import com.test.com.NewsCallBack;
import com.test.com.R;
import com.test.com.SharedInterFace;
import com.test.com.UrlFactory;
import com.test.com.application.MyApplication;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.TabEntity;
import com.test.com.firstFragmentactivity.NewsActivity1;
import com.test.com.fragements.BaoMingFragment;
import com.test.com.fragements.FirstFragment1;
import com.test.com.fragements.FourthFragment;
import com.test.com.fragements.SecondFragment1;
import com.test.com.fragements.ZiXunFragment;
import com.test.com.services.MyService;
import com.test.com.utills.SPUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class MainActivity extends BaseToolbarActivity implements SharedInterFace, NewsCallBack {
    @BindView(R.id.bottomBar)
    CommonTabLayout mTabLayout;
    int index = 0;
    private int[] iconUnselect = {
            R.mipmap.shouye, R.mipmap.zixunxun,
            R.mipmap.baoming11,
            R.mipmap.zixun, R.mipmap.wode2};
    private int[] iconSelect = {
            R.mipmap.shouye1, R.mipmap.zixunxun2,
            R.mipmap.baoming11,
            R.mipmap.zixunse, R.mipmap.wode};
    private String[] mTitles = {"首页", "资讯", "报名", "咨询", "我的"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> frgments = new ArrayList<>();
    private ImageView mImageRight;
    public static boolean hasPremission = false;
    public static boolean isHasNoReadNews = true;
    public static int indexCount = 0;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    /*有未读消息*/
                    if (isHasNoReadNews)
                        initToobar(-1, "文都教育", R.mipmap.xiaoxi);
                    else {
                        initToobar(-1, "文都教育", R.mipmap.wuxiaoxi);
                    }
                    index = 0;
                } else if (position == 1) {
                    initToobar(-1, "资讯", -1);
                    index = 1;
                } else if (position == 2) {
                    if (!MyApplication.getDataBase().getToken().isEmpty()) {
                        initToobar(-1, "报名", -1);
                        index = 2;
                    } else {
                        jumpToActivity(LoginActivity.class, false);
                    }

                } else if (position == 3) {
                    initToobar(-1, "咨询", -1);
                    index = 3;
                } else if (position == 4) {
                    initToobar(-1, "我的", -1);
                    index = 4;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void hasNes() {
        if (index == 0) {
            /**消息数为0*/
            initToobar(-1, "文都教育", R.mipmap.wuxiaoxi);
            isHasNoReadNews = false;

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        if(indexCount == 1)
        {
            mTabLayout.setCurrentTab(4);
        }
        else
            mTabLayout.setCurrentTab(index);
        super.onResume();

//        if(indexCount == 1)
//        {
//            indexCount = 0;
//           new  Handler().post(new Runnable() {
//               @Override
//               public void run() {
//
//               }
//           });
//        }

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initBase() {
        isShowToolBar = false;
        isshowActionbar = true;
    }

    @Override
    protected void initView() {


        ImageView viewById = (ImageView) findViewById(R.id.imageRight);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(NewsActivity1.class, false);
            }
        });
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], iconSelect[i], iconUnselect[i]));
        }
        FirstFragment1 firstFragment1 = new FirstFragment1().newInstance(this);
        SecondFragment1 secondFragment1 = new SecondFragment1();
        BaoMingFragment baoMingFragment = new BaoMingFragment();

        frgments.add(firstFragment1);
        frgments.add(secondFragment1);
        frgments.add(baoMingFragment);
        frgments.add(new ZiXunFragment());
        frgments.add(new FourthFragment().newInstance(this));
        mTabLayout.setTabData(mTabEntities, this, R.id.content_relative, frgments);
        mTabLayout.setCurrentTab(0);
        initToobar(-1, "文都教育", R.mipmap.xiaoxi);


        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        registerMsesageReceiver();

        getPermission();
    }


    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                JSONObject jsonObject = JSON.parseObject(object.toString());
                if (jsonObject.getInteger("code").equals(1)) {
                    Log.e("TAG+++passworld", "登录成功");
                    String token = jsonObject.getJSONObject("data").getString("token");
                    MyApplication.getDataBase().setToken(token);
                    // TODO: 2017/8/11 uuid不保存在sp中
                    MyApplication.userBaseInfo.setUuid("uuid");

                    Toast.makeText(MyApplication.getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("TAG++passworld", "登录失败");
                    MyApplication.getDataBase().setToken(" ");
                }
                break;
            case 1:
                jsonObject = JSON.parseObject(object.toString());
                if (jsonObject.getInteger("code").equals(1)) {
                    String token = jsonObject.getJSONObject("data").getString("token");
                    MyApplication.getDataBase().setToken(token);
                    // TODO: 2017/8/11 uuid不保存在sp中
                    MyApplication.userBaseInfo.setUuid("uuid");
                    Log.e("TAG+++unionid", "登录成功");
                    Toast.makeText(MyApplication.getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                // TODO: 2017/8/19 有账号绑定账号回来  应该有一个token
                super.getSuccess(object, what);
                ShowToast(object.getString("msg"));
                Log.i("TAG+++", object.getString("msg"));
                MyApplication.getDataBase().setToken(object.getJSONObject("data").getString("token"));
                SPUtils.setUnionid(MyApplication.getDataBase().getUnionid());
                break;
        }


    }


    private void getPermission() {
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入设备", R.drawable.permission_ic_storage));
        permissonItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "读取设备", R.drawable.permission_ic_storage));
        permissonItems.add(new PermissionItem(Manifest.permission.CALL_PHONE, "电话", R.drawable.permission_ic_phone));

        HiPermission.create(this).permissions(permissonItems).checkMutiPermission(new PermissionCallback() {
            @Override
            public void onClose() {
                ShowToast("用户关闭了权限");
                hasPremission = false;
            }

            @Override
            public void onFinish() {
                hasPremission = true;

            }

            @Override
            public void onDeny(String permission, int position) {
                Log.i(TAG, "onDeny");
                hasPremission = false;
            }

            @Override
            public void onGuarantee(String permission, int position) {
                /**   点击允许之后才走*/
                hasPremission = true;
                Log.i(TAG, "onGuarantee");

            }
        });
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMsesageReceiver() {
        Log.i("TAG", "注册广播");
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra("message");
                String extras = intent.getStringExtra("extras");
                StringBuilder showMsg = new StringBuilder();
                showMsg.append("message" + " : " + messge + "\n");
                if (null != extras) {
                    showMsg.append("extras" + " : " + extras + "\n");
                }
                Toast.makeText(MainActivity.this, showMsg.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.e("TAG++onStart", platform.toString());
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("TAG+onResult", platform.toString());
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e("TAG+onError", platform.toString() + t.toString());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.e("TAG+onCancel", platform.toString());

        }
    };

    @Override
    public void sendShared() {

        UMWeb web = new UMWeb("http://120.26.141.238/wendustudy/index.php");
        web.setTitle("This is music title");//标题
        web.setDescription("文都教育");//描述

        new ShareAction(MainActivity.this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .open();
    }

    @Override
    public void bindWeixin() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            showLoadingDialog();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            try {
                cancleLoadingDialog();
                String unionid = data.get("uid");
                Log.e("TAG++onComplete", unionid + "+++");
                MyApplication.getDataBase().setUnionid(unionid);
                SPUtils.setUnionid(unionid);
                Log.e("TAG+getUUId", SPUtils.getUnionid() + "+++");
                weChatLoginResult(unionid);

                //// TODO: 2017/8/18  没有账号 跳转到微信LoginActivity，有账号  跳转到登录界面 去绑定手机号
                Log.e("TAG++onComplete", data.toString());
            } catch (Exception e) {
                ShowToast("出错了 请稍候再试");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.e("TAG+++",t.toString());
            ShowToast("绑定失败");
            cancleLoadingDialog();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            cancleLoadingDialog();
        }
    };

    private void weChatLoginResult(String unionid) {
        // TODO: 2017/8/18  加上微信的id参数
        RequestParams params = new RequestParams();
        params.put("phone", SPUtils.getCount());
        params.put("password",SPUtils.getPWD());
        params.put("unionid", MyApplication.getDataBase().getUnionid());
        getDataFromInternet(UrlFactory.weChatBindPhone, params, 2);
        showLoadingDialog();
    }

}
