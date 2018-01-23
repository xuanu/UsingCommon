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
> 默认不输出日志，请使用下面的方法修改，在App模块调用`L.isDebug=BuildConfig.DEBUG;`
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
NetUtils.INSTANCE.getWifiIp();//获取本机的ip ,需要网络开启，权限：android.Manifest.permission.INTERNET。
NetUtils.INSTANCE.getMac();//获取本机mac;需要网络开启，权限：android.Manifest.permission.INTERNET  参考：[android4.0-7.0获取mac地址，方法是google提供。](http://blog.csdn.net/dazhang357/article/details/73903831);
```
- [DarkButton.kt](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/view/DarkButton.kt);默认实现按下效果的控件
- [DarkImage.kt](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/view/DarkImage.kt);默认实现按下效果的控件
- [DarkText.kt](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/view/DarkText.kt);默认实现按下效果的控件
> 默认实现了按下的效果，文字颜色的变化是用当前文字的透明度*0.5来实现的。

- [PackageUtils.java]();抄自：Trinea/[android-common](https://github.com/Trinea/android-common/blob/master/src/cn/trinea/android/common/util/PackageUtils.java)
```
 * <li>{@link PackageUtils#installNormal(Context, String)}</li>//安装程序，普通方式。
 * <li>{@link PackageUtils#installSilent(Context, String)}</li>//安装程序，静默安装
 * <li>{@link PackageUtils#install(Context, String)}</li>//安装程序，静默优先
 * <li>{@link PackageUtils#uninstallNormal(Context, String)}</li>//卸载程序，普通方式。
 * <li>{@link PackageUtils#uninstallSilent(Context, String)}</li>//卸载，静默
 * <li>{@link PackageUtils#uninstall(Context, String)}</li>//卸载，静默优先
 * <li>{@link PackageUtils#isSystemApplication(Context)}</li>//是否系统应用
 * <li>{@link PackageUtils#isSystemApplication(Context, String)}</li>//指定应用是否为系统应用
 * <li>{@link PackageUtils#isSystemApplication(PackageManager, String)}</li>//指定应用是否为系统应用
 * <li>{@link PackageUtils#getInstallLocation()} get system install location</li> //
 * <li>{@link PackageUtils#startInstalledAppDetails(Context, String)} start InstalledAppDetails Activity</li>  //打开已安装应用详情
```

- [UsageStatsUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/app/UsageStatsUtils.java);//用于获取最近运行的程序，配合AppUtils#getTopPackageName();使用
```
UsageStatsUtils.hasUsageOption(context);//有无有UsageStats的选项
UsageStatsUtils.isOpen(context);//打开状态
UsageStatsUtils.openUsageSetting(activity,int code);//打开开关，只有用户打开了本程序的这个选项，才能统计最近应用的信息
```


- [AppUtils.kt](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/app/AppUtils.kt);//应用相关
```
 AppUtils.getApps(context,type:int);//获取本机应用：0用户安装的，1系统应用，2全部应用
 AppUtils.isPackageExist(context,packagename);//指定包名是否安装
 AppUtils.getHasMainInfo(context);//本机带启动界面的应用列表
  AppUtils.getTopPackageName(context,defaultTime:Int=5);//获取最后运行的应用，当前运行的应用  。
```

- [Md5Encrypt](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/encode/Md5Encrypt.kt);MD5加密码
`Md5Encrypt.md5(string)`;加密字符

- [AssetsUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/assets/AssetsUtils.kt);  获取Assets文件
```
AssetsUtils.fileString(context,fileName);//获取指定文件的内容，filename可以带路径
```

- [FileUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/file/FileUtils.kt);文件读写
```
FileUtils.read(path);//读取文件内容
FileUtils.write(path,content,append);//写入文件，是否追加
FileUtils.deleteFiles(path);//删除文件或文件夹
FileUtils.getFileMd5(file);//读取文件MD5
```

- [OpenFiles.java](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/file/OpenFiles.java);//本地文件 打开方式。
```
openFiles(Context context, Uri uri, String suffix)
getSuffix(String filePath)
//传入Uri，可以适配7.0以后的文件 权限
//Uri tempUri = FileProvider.getUriForFile(pContext, "zeffect.cn.apks.filemanager", new File(param));
//7.0以下可以传入Uri.from(new File(path));
```

-[Mp3Utils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/mp3/Mp3Utils.java);//mp3相关
```
heBingMp3(File savePath, String... paths); //合并MP3
clip(String inputPath, String outputPath, int start, int end);//裁剪MP3
```