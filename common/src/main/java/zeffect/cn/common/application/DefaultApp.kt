package zeffect.cn.common.application

import android.app.ActivityManager
import android.app.Application
import android.content.Context


/**
 * Created by Administrator on 2018/3/6.
 */
class DefaultApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (isMainProcess()) {
            ExceptionHandler.getInstance().init(applicationContext)
        }
    }


    /**
     * 获取当前进程名
     */
    private fun getCurrentProcessName(): String {
        val pid = android.os.Process.myPid()
        var processName = ""
        val manager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid == pid) {
                processName = process.processName
            }
        }
        return processName
    }

    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    private fun isMainProcess(): Boolean {
        return applicationContext.packageName == getCurrentProcessName()
    }
}