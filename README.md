# ShortVideo
仿微信短视频
参考https://github.com/MachineHou/androidVideoEditor

#### 集成步骤

* 如果你的项目 Gradle 配置是在 `7.0 以下`，需要在 `build.gradle` 文件中加入

```groovy
allprojects {
    repositories {
        // JitPack 远程仓库：https://jitpack.io
        maven { url 'https://jitpack.io' }
    }
}
```

* 如果你的 Gradle 配置是 `7.0 及以上`，则需要在 `settings.gradle` 文件中加入

```groovy
dependencyResolutionManagement {
    repositories {
        // JitPack 远程仓库：https://jitpack.io
        maven { url 'https://jitpack.io' }
    }
}
```

* 配置完远程仓库后，在项目 app 模块下的 `build.gradle` 文件中加入远程依赖

```groovy
android {
    // 支持 JDK 1.8
    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
        sourceCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
       implementation 'com.github.dahui888:HPermissions:1.2'
}
```

#### AndroidX

* 如果项目是基于 **AndroidX** 包，请在项目 `gradle.properties` 文件中加入

```text
# 表示将第三方库迁移到 AndroidX
android.enableJetifier = true
```

* 如果项目是基于 **Support** 包则不需要加入此配置

#### 分区存储

* 如果项目已经适配了 Android 10 分区存储特性，请在 `AndroidManifest.xml` 中加入

```xml
<manifest>

    <application>

        <!-- 表示当前项目已经适配了分区存储特性 -->
        <meta-data
            android:name="ScopedStorage"
            android:value="true" />

    </application>

</manifest>
```

* 如果当前项目没有适配这特性，那么这一步骤可以忽略

* 需要注意的是：这个选项是框架用于判断当前项目是否适配了分区存储，需要注意的是，如果你的项目已经适配了分区存储特性，可以使用 `READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 来申请权限，如果你的项目还没有适配分区特性，就算申请了 `READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 权限也会导致无法正常读取外部存储上面的文件，如果你的项目没有适配分区存储，请使用 `MANAGE_EXTERNAL_STORAGE` 来申请权限，这样才能正常读取外部存储上面的文件，你如果想了解更多关于 Android 10 分区存储的特性，可以[点击此处查看和学习](https://www.jianshu.com/p/9a9d260e10b0)。

#### 使用

* 需要的权限

```xml
    <uses-permission android:name="android.permission.FLASHLIGHT" />
    <!-- 授予该程序使用麦克疯的权限 -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <!-- 授予该程序使用摄像头的权限 -->
    <uses-permission android:name="android.permission.CAMERA" />
    <!-- 授予使用外部存储器的权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

```
```java
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

```

