package com.c.m;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ph.camera.JCameraView;
import com.ph.camera.listener.ClickListener;
import com.ph.camera.listener.ErrorListener;
import com.ph.camera.listener.JCameraListener;
import com.ph.camera.listener.RecordStateListener;

import java.io.File;

/**
 * 视频拍摄界面
 */

public class MainActivity extends AppCompatActivity {
    private JCameraView mJCameraView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //绑定控件
        mJCameraView = (JCameraView) findViewById(R.id.jcameraview);
        //请求权限
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 0);

        //设置视频保存路径
        mJCameraView.setSaveVideoPath(getCacheDir() + File.separator + "JCamera");
        mJCameraView.setMinDuration(3000); //设置最短录制时长
        mJCameraView.setDuration(10000); //设置最长录制时长
        mJCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_RECORDER);
        mJCameraView.setTip("长按拍摄, 3~10秒");
        mJCameraView.setRecordShortTip("录制时间3~10秒");
        mJCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        mJCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.d("CJT", "camera error");
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(MainActivity.this, "给点录音权限可以?", Toast.LENGTH_SHORT).show();
            }
        });

        //JCameraView监听
        mJCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmaps
                Log.e("TAG", "bitmap = " + bitmap.getWidth());
//                String path = FileUtil.saveBitmap("JCamera", bitmap);
//                Intent intent = new Intent();
//                intent.putExtra("path", path);
//                setResult(101, intent);
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //获取视频路径
//                String path = FileUtil.saveBitmap("small_video", firstFrame);
                Log.e("TAG", "url ==== " + url);
//                Log.d("CJT", "url:" + url + ", firstFrame:" + path);
//                Intent intent = new Intent();
//                intent.putExtra("path", path);
//                setResult(101, intent);

//                TrimVideoActivity.startActivity(MainActivity.this, url);
                finish();
            }
        });


        mJCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });


        mJCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });


        mJCameraView.setRecordStateListener(new RecordStateListener() {
            @Override
            public void recordStart() {

            }

            @Override
            public void recordEnd(long time) {
                Log.e("录制状态回调", "录制时长：" + time);
            }

            @Override
            public void recordCancel() {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    private void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mJCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mJCameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
