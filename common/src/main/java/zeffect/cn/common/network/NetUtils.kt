package zeffect.cn.common.network

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


/**
 * <pre>
 *      author  ：zzx
 *      e-mail  ：zhengzhixuan18@gmail.com
 *      time    ：2017/09/26
 *      desc    ：
 *      version:：1.0
 * </pre>
 * @author zzx
 */
object NetUtils {
    /**
     * 判断网络是否连接
     *
     * @param context 上下文
     * @return 判断网络是否连接
     */
    fun isConnected(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (null != connectivity) {
            val info = connectivity.activeNetworkInfo
            if (null != info && info.isAvailable) {
                return true
            }
        }
        return false
    }

    /**
     * 判断是否是wifi连接
     *
     * @param context 上下文
     * @return 是否是wifi移动网络连接
     */
    fun isWifi(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false
        return if (cm.activeNetworkInfo == null) {
            false
        } else cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 判断是否是mobile移动网络连接
     *
     * @param context 上下文
     * @return 是否是mobile移动网络连接
     */
    fun isMobile(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false
        return cm.activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * 打开网络设置界面
     *
     * @param activity 上下文
     */
    fun openSetting(activity: Activity) {
        val intent = Intent("/")
        val cm = ComponentName("com.android.settings", "com.android.settings.WirelessSettings")
        intent.component = cm
        intent.action = "android.intent.action.VIEW"
        activity.startActivityForResult(intent, 0)
    }
}