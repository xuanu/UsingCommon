package zeffect.cn.common.activity

import android.app.Activity
import android.view.WindowManager


/**
 * Created by Administrator on 2017/9/16.
 */
object ActivityUtils {
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param activity 页面
     * @param alpha  透明度 popwindow.show的时候backgroundAlpha(0.5f);popwindow.setOnDismissListener的时候backgroundAlpha(1f);
     * @return true修改成功false修改失败
     */
    fun bgAlpha(activity: Activity, alpha: Float = 1f) {
        val lp = activity.window.attributes
        lp.alpha = alpha //0.0-1.0
        activity.window.attributes = lp
    }
}