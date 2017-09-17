# UsingCommon
收集一点公共类，大部分是挑出来的。
> 本库暂时没有引用第三方的包，尽量做到最少。

##### 类说明
- [UrlEncode](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/encode/UrlEncode.java),对Url加密。
`UrlEncode#encodeUrl(String)`//URL编码，重复也有效果。

- [OnClickFastListener](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/gesture/OnClickFastListener.java),防止快速点击，可以设置间隔
```
View.setOnClickListener(new OnClickFastListener(){});
View.setOnClickListener(new OnClickFastListener(time){});
```

- [ZGesture](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/gesture/ZGesture.kt);手势监听类，监听了单双指的很多事件
> 事件见：[OnGesture](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/gesture/OnGesture.java);
> //使用方法与GestureDetector一致，仅对其进行封装，方便使用
`ZGesture(context,OnGesture).onTouchEvent(event)`

- [MediaUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/media/MediaUtils.java); 封装了本地文件的MediaPlay播放，支持连续播放多个。
```
MediaUtils.getInstance().play(String[] paths);//播放音频，只支持本地
MediaUtils.getInstance().play(String[] paths,OnPlayer pOnPlayer);//OnPlayer只写的stop回调
MediaUtils.getInstance().stop();//停止播放
```

- [SoundUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/media/SoundUtils.java);仅仅记录了如何使用。

- [WeakAsyncTask](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/weak/WeakAsyncTask.java);防止内存泄露的异步线程，常用。
`new WeakAsyncTask<Void,Void,Void,Context>(this).execute();`

- [WeakHandler](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/weak/WeakHandler.java);防止泄露的Handler;
` new  WeakHandler(new Handler.Callback(){...})`

- [ViewUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/view/ViewUtils.kt);//对View的一些操作
```
ViewUtils.INSTANCE.getMeasureSize(measureSpace,defautSize);//获取测量大小，onMeasure时使用
```

- [ActivityUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/activity/ActivityUtils.kt); activity的一些收集
```
ActivityUtils.INSTANCE.bgAlpha(this,0.5f);//设置背景透明度
```

- [IntentUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/intent/IntentUtils.kt);意图相关的操作
```
IntentUtils.INSTANCE.openCamera(activity,file,code);//打开相机拍照
IntentUtils.INSTANCE.openGallery(activity,code);//打开相册选图片
IntentUtils.INSTANCE.getGalleryPath(activity,code);//选完相册图片后，用这个得到路径
```

- [SpUtils](https://github.com/xuanu/UsingCommon/tree/master/common/src/main/java/zeffect/cn/common/sp/SpUtils.kt);SharedPreferences存储
```
putString(context,key,string);
getString(...)
putInt(...)
getInt(...)
putLong(...)
getLong(...)
putFloat(...)
getFloat(...)
putBoolean(...)
getBoolean(...)
```

- [L](https://github.com/xuanu/UsingCommon/tree/master/common/src/main/java/zeffect/cn/common/log/L.kt);日志打印类，同Log
> L.INSTANCE.isDebug,可以控制是否打印日志，发包时就可以关闭。