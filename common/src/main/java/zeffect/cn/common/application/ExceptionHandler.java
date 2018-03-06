package zeffect.cn.common.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import zeffect.cn.common.file.FileUtils;

/**
 * http://blog.csdn.net/qq282330332/article/details/51647823
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionHandler instance = new ExceptionHandler();
    private Context context;
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Map<String, String> devInfos = new HashMap<String, String>();

    public static ExceptionHandler getInstance() {
        return instance;
    }

    public void init(Context ctx) {
        context = ctx;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * @param thread 发生异常的线程
     * @param ex     抛出的异常
     * @return void
     * @name uncaughtException(Thread thread, Throwable ex)
     * @description 当发生UncaughtException时会回调此函数
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean isDone = doException(ex);
        if (!isDone && defaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理  
            defaultHandler.uncaughtException(thread, ex);
        } else {
            // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app  
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * @param ex 抛出的异常
     * @return 异常处理标志
     * @name doException(Throwable ex)
     * @description 处理异常
     */
    private boolean doException(Throwable ex) {
        if (ex == null) {
            return true;
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "程序出现错误,即将退出！", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        collectDeviceInfo(context);
        saveExceptionToFile(ex);
        return true;
    }


    /**
     * @param ctx
     * @return void
     * @name collectDeviceInfo(Context ctx)
     * @description 收集必须的设备信息
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                devInfos.put("versionName", pi.versionName);
                devInfos.put("versionCode", "" + pi.versionCode);
                devInfos.put("MODEL", "" + Build.MODEL);
                devInfos.put("SDK_INT", "" + Build.VERSION.SDK_INT);
                devInfos.put("PRODUCT", "" + Build.PRODUCT);
                devInfos.put("TIME", "" + getCurrentTime());
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                devInfos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
    }

    /**
     * @param ex 抛出的异常
     * @return void
     * @name saveExceptionToFile(Throwable ex)
     * @description 保存异常信息到文件中
     */
    private void saveExceptionToFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append("--------------------------------------------------------------------------------------\n");
        sb.append("bug发生时间:" + getCurrentTime() + "\n");
        for (Map.Entry<String, String> entry : devInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        //保存数据在Log文件夹
        try {
            File saveFile = new File(context.getExternalFilesDir("Log"), "error_" + getCurrentDate() + ".txt");
            FileUtils.INSTANCE.write(saveFile.getAbsolutePath(), sb.toString(), true, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 当前时间
     * @name getCurrentTime()
     * @description 获取当前时
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CurrentTime = sdf.format(new Date());
        return CurrentTime;
    }

    /**
     * @return 当前时间
     * @name getCurrentTime()
     * @description 获取当前时
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String CurrentTime = sdf.format(new Date());
        return CurrentTime;
    }

}  