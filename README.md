# UsingCommon
收集一点公共类，大部分是挑出来的。

##### 类说明
- [UrlEncode](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/encode/UrlEncode.java),对Url加密。
`UrlEncode#encodeUrl(String)`

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
MediaUtils.getInstance().play(String[] paths);
MediaUtils.getInstance().play(String[] paths,OnPlayer pOnPlayer);//OnPlayer只写的stop回调
MediaUtils.getInstance().stop();
```

- [SoundUtils](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/media/SoundUtils.java);仅仅记录了如何使用。

- [WeakAsyncTask](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/weak/WeakAsyncTask.java);防止内存泄露的异步线程，常用。
`new WeakAsyncTask<Void,Void,Void,Context>(this).execute();`

- [WeakHandler](https://github.com/xuanu/UsingCommon/blob/master/common/src/main/java/zeffect/cn/common/weak/WeakHandler.java);防止泄露的Handler;
` new  WeakHandler(new Handler.Callback(){...})`

