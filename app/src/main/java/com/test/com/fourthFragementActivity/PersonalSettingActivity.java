package com.test.com.fourthFragementActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.test.com.CompressImageUtils;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.activity.MainActivity;
import com.test.com.application.MyApplication;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.UserInfo;
import com.test.com.utills.CircleImageView;
import com.test.com.utills.DensityUtil;
import com.test.com.utills.GlideImageLoader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import butterknife.BindView;

public class PersonalSettingActivity extends BaseToolbarActivity {
    @BindView(R.id.userCicleImage)
    CircleImageView userCicleImage;
    @BindView(R.id.userCicleImageRelavie)
    RelativeLayout userCicleImageRelavie;
    @BindView(R.id.userNickNameRelative)
    RelativeLayout userNickNameRelative;
    @BindView(R.id.userNickName)
    TextView userNickName;
    @BindView(R.id.bt_complete)
    Button bt_complete;
    @BindView(R.id.majroInfo)
    RelativeLayout majroInfo;
    @BindView(R.id.zaiduzhuanyeRela)
    RelativeLayout zaiduzhuanyeRela;
    @BindView(R.id.baokaozhuanyeRela)
    RelativeLayout baokaozhuanyeRela;
    @BindView(R.id.ChangeMailaRelative)
    RelativeLayout ChangeMailaRelative;
    @BindView(R.id.school)
    TextView mSchool;
    @BindView(R.id.majorIn)
    TextView mMajorIn;
    @BindView(R.id.major)
    TextView mMajor;
    @BindView(R.id.mail)
    TextView mMail;

    private ImagePicker imagePicker;
    private ArrayList<ImageItem> imageList = new ArrayList<>();
    // 拍照成功，读取相册成功，裁减成功
    private final int ALBUM_OK = 1, CAMERA_OK = 2, CUT_OK = 3;
    private static final int IMAGE_PICKER = 1;
    int sex = 1;
    private ByteArrayInputStream header;

    @Override
    protected void initView() {
        initToobar("个人设置", R.mipmap.fanhui);
        initImagePicker();
        userCicleImage.setBorderWidth(DensityUtil.dip2px(this, 2));
        userCicleImage.setBorderColor(getResources().getColor(R.color.base_color));


    }


    @Override
    protected int getContentView() {
        return R.layout.activity_personal_setting;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        getDataFromInternet(UrlFactory.getUserInfo, params, 1);
        showLoadingDialog();

    }

    public void setData(final String image) {
        header = CompressImageUtils.CompressImageUtils(image);
        RequestParams params = new RequestParams();
        params.put("uuid", MyApplication.getDataBase().getUuid());
        params.put("header", header, "head.png", "image/png");
        getDataFromInternet(UrlFactory.editUserInfo, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                if (object.getInteger("code") == 1) {
                    ShowToast(object.getString("msg"));
                    if (imageList.size() != 0) {
                        Glide.with(this).load(imageList.get(0).path).asBitmap().centerCrop().into(userCicleImage);
                    }
                } else {
                    ShowToast(object.getString("msg"));
                }
                break;
            case 1:
                if (object.getInteger("code") == 1) {
                    Gson gson = new Gson();
                    UserInfo info = gson.fromJson(object.toString(), UserInfo.class);
                    Glide.with(this).load(UrlFactory.imagePath + info.getData().getUserInfo().getHeadimg()).asBitmap().into(userCicleImage);
                    userNickName.setText(info.getData().getUserInfo().getNickname());
                    mSchool.setText(info.getData().getUserInfo().getSchool());
                    mMajorIn.setText(info.getData().getUserInfo().getStay_major());
                    mMajor.setText(info.getData().getUserInfo().getExamination_major());
                    mMail.setText(info.getData().getUserInfo().getEmail());
                }
                break;
        }

    }

    @Override
    protected void setListener() {
        userCicleImageRelavie.setOnClickListener(this);
        userNickNameRelative.setOnClickListener(this);
        bt_complete.setOnClickListener(this);
        majroInfo.setOnClickListener(this);
        zaiduzhuanyeRela.setOnClickListener(this);
        baokaozhuanyeRela.setOnClickListener(this);
        ChangeMailaRelative.setOnClickListener(this);

    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userCicleImageRelavie:
                if (MainActivity.hasPremission) {

                    Intent intent = new Intent(PersonalSettingActivity.this, ImageGridActivity.class);
                    startActivityForResult(intent, IMAGE_PICKER);
                }

                break;
            case R.id.userNickNameRelative:
                Bundle bundle = new Bundle();
                bundle.putString(ChangeUserNickNameActivity.TITLE, "昵称");
                bundle.putInt(ChangeUserNickNameActivity.TYPE, 1);
                jumpToActivity(ChangeUserNickNameActivity.class, bundle, false);
                break;
            case R.id.bt_complete:
                break;
            case R.id.majroInfo:
                // TODO: 2017/8/10  跳转错误 选择服务器的数据 
                bundle = new Bundle();
                bundle.putString(ChangeUserNickNameActivity.TITLE, "在读院校");
                bundle.putInt(ChangeUserNickNameActivity.TYPE, 2);
                jumpToActivity(ChangeUserNickNameActivity.class, bundle, false);
                break;
            case R.id.zaiduzhuanyeRela:
                bundle = new Bundle();
                bundle.putString(ChangeUserNickNameActivity.TITLE, "在读专业");
                bundle.putInt(ChangeUserNickNameActivity.TYPE, 3);
                jumpToActivity(ChangeUserNickNameActivity.class, bundle, false);
                break;
            case R.id.baokaozhuanyeRela:
                // TODO: 2017/8/10  跳转错误 选择服务器的数据
                bundle = new Bundle();
                bundle.putString(ChangeUserNickNameActivity.TITLE, "报考专业");
                bundle.putInt(ChangeUserNickNameActivity.TYPE, 4);
                jumpToActivity(ChangeUserNickNameActivity.class, bundle, false);
                break;
            case R.id.ChangeMailaRelative:
                bundle = new Bundle();
                bundle.putString(ChangeUserNickNameActivity.TITLE, "更改邮箱");
                bundle.putInt(ChangeUserNickNameActivity.TYPE, 5);
                jumpToActivity(ChangeUserNickNameActivity.class, bundle, false);
                break;


        }
    }

    /**
     * 第三方拍照和裁剪
     */
    private void initImagePicker() {
        imageList = new ArrayList<>();
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

    }

    /*
     * 相机拍照得到的图片设置到ImageView上面去
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                imageList = (ArrayList<ImageItem>) data.getSerializableExtra(imagePicker.EXTRA_RESULT_ITEMS);
                setData(imageList.get(0).path);

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!ChangeUserNickNameActivity.nickName.trim().isEmpty()) {
            userNickName.setText(ChangeUserNickNameActivity.nickName);
        }
        if (!ChangeUserNickNameActivity.Colloge.trim().isEmpty()) {
            mSchool.setText(ChangeUserNickNameActivity.Colloge);
        }
        if (!ChangeUserNickNameActivity.majorIn.trim().isEmpty()) {
            mMajorIn.setText(ChangeUserNickNameActivity.majorIn);
        }
        if (!ChangeUserNickNameActivity.major.trim().isEmpty()) {
            mMajor.setText(ChangeUserNickNameActivity.major);
        }
        if (!ChangeUserNickNameActivity.mail.trim().isEmpty()) {
            mMail.setText(ChangeUserNickNameActivity.mail);
        }


    }


}
