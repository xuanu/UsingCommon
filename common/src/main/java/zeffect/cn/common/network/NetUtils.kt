package zeffect.cn.common.network

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.NetworkInterface.getNetworkInterfaces
import java.net.SocketException
import java.util.*
import android.net.wifi.WifiInfo
import android.util.Log


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

    /***
     * 获取机本Mac地址
     * @param context 上下文
     * 需要权限
     * @see android.Manifest.permission.INTERNET
     */
    fun getMac(): String {
        try {
            val all = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0", ignoreCase = true)) continue
                val macBytes = nif.hardwareAddress ?: return ""
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }
                if (res1.length > 0) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
        }
        return "02:00:00:00:00:00"
    }


    /**
     * 获取 WIFI的IP地址
     *
     * @param pContext 上下文
     * @return IP_KEY
     * 需要权限
     * @see android.Manifest.permission.INTERNET
     */
    fun getWifiIp(): String {
        var ipaddress = ""
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                val nif = en.nextElement()// 得到每一个网络接口绑定的所有ip
                val inet = nif.inetAddresses
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    val ip = inet.nextElement()
                    if (!ip.isLoopbackAddress && ip is Inet4Address) {
                        return ip.getHostAddress()
                    }
                }
            }
        } catch (e: SocketException) {
            Log.e("feige", "获取本地ip地址失败")
            e.printStackTrace()
        }

        return ipaddress
    }
}