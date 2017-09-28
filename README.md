# UsingCommon
收集一点公共类，大部分是挑出来的。
> 本库暂时没有引用第三方的包，尽量做到最少。

##### 类说明
- [UrlEncode](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/encode/UrlEncode.kt),对Url加密。
`UrlEncode#encodeUrl(String)`//URL编码，重复也有效果。

- [OnClickFastListener](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/gesture/OnClickFastListener.kt),防止快速点击，可以设置间隔
```
View.setOnClickListener(new OnClickFastListener(){});
View.setOnClickListener(new OnClickFastListener(time){});
```

- [ZGesture](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/gesture/ZGesture.kt);手势监听类，监听了单双指的很多事件
> 事件见：[OnGesture](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/gesture/OnGesture.kt);
> //使用方法与GestureDetector一致，仅对其进行封装，方便使用
`ZGesture(context,OnGesture).onTouchEvent(event)`

- [MediaUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/media/MediaUtils.kt); 封装了本地文件的MediaPlay播放，支持连续播放多个。
```
MediaUtils.getInstance().play(String[] paths);//播放音频，只支持本地
MediaUtils.getInstance().play(String[] paths,OnPlayer pOnPlayer);//OnPlayer只写的stop回调
MediaUtils.getInstance().stop();//停止播放
```

- [SoundUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/media/SoundUtils.java);仅仅记录了如何使用。

- [WeakAsyncTask](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/weak/WeakAsyncTask.kt);防止内存泄露的异步线程，常用。
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
IntentUtils.INSTANCE.openPhone(context,number:Int=1);//打电话
IntentUtils.INSTANCE.openSetting(context);//打开系统设置
IntentUtils.INSTANCE.openCamera(context);//打开相机，啥都不做
IntentUtils.INSTANCE.openSms(context,number:Int=1,content="");//发短信
IntentUtils.INSTANCE.openRecord(context);//打开录音机
IntentUtils.INSTANCE.openPeople(context);//打开通讯录
IntentUtils.INSTANCE.openWifiSetting(context);//打开网络设置
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
> release包时，会默认屏蔽日志，可修改。
> L.INSTANCE.isDebug,可以控制是否打印日志，发包时就可以关闭。

- [FontUtils.kt](https://github.com/xuanu/UsingCommon/tree/master/common/src/main/java/zeffect/cn/common/font/FontUtils.kt);字体相关工具
```
FontUtils.INSTANCE.fontHeight(fontsize);//获取字体高度
```

- [NetUtils.kt](https://github.com/xuanu/UsingCommon/tree/master/common/src/main/java/zeffect/cn/common/network/NetUtils.kt);网络变化
> 使用isWifi或isMobile时，请先判断网络是否连接。
```
NetUtils.INSTANCE.isConnected(context);//网络是否可用（不是是否连接）
NetUtils.INSTANCE.isWifi(context);//WIFI
NetUtils.INSTANCE.isMobile(context);//流量
NetUtils.INSTANCE.openSetting(activity); //打开设置界面
NetUtils.INSTANCE.getWifiIp();//获取本机的ip ,需要权限：android.Manifest.permission.INTERNET
NetUtils.INSTANCE.getMac();//获取本机mac;需要权限：android.Manifest.permission.INTERNET  参考：[android4.0-7.0获取mac地址，方法是google提供。](http://blog.csdn.net/dazhang357/article/details/73903831);
```
- [DarkButton.kt](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/view/DarkButton.kt);默认实现按下效果的控件
- [DarkImage.kt](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/view/DarkImage.kt);默认实现按下效果的控件
- [DarkText.kt](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/view/DarkText.kt);默认实现按下效果的控件
> 默认实现了按下的效果，文字颜色的变化是用当前文字的透明度*0.5来实现的。
