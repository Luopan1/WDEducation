package com.test.com.study.activity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.backPress;

public class VideoPlayActivity extends BaseToolbarActivity {
    @BindView(R.id.video_player_item_1)
    JCVideoPlayerStandard mSuperVideoPlayer;
    @BindView(R.id.layout_fanhui)
    RelativeLayout layoutFanhui;
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_content)
    TextView tvContent;

    JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;
    SensorManager sensorManager;
    @Override
    protected int getContentView() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initData() {
        tvName.setText("视频播放");
        tvContent.setText(getIntent().getStringExtra("title"));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
        mSuperVideoPlayer.setUp(getIntent().getStringExtra("url"), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
//        mSuperVideoPlayer.setUp("/storage/emulated/0/filedownloader/luffy/FILETEMP/视频1.mp4", JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, "");
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void init(Bundle savedInstanceState) {
        isshowActionbar = false;
        isFullScreen = true;
        stateColor = R.color.translucent;
        super.init(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        sensorManager.unregisterListener(sensorEventListener);
    }




    @OnClick(R.id.layout_fanhui)
    public void onClick() {
        finish();
    }
}
