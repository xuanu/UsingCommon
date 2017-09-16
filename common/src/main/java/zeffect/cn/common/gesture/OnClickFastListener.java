package zeffect.cn.common.gesture;

import android.view.View;

/**
 * 方法按钮重复点击的监听类源码
 * <p>
 * 指定时间内的点击操作不可用。
 */
public abstract class OnClickFastListener implements View.OnClickListener {

    public OnClickFastListener() {
    }

    public OnClickFastListener(long time) {
        if (time < 0) {
            time = 0;
        }
        this.DELAY_TIME = time;
    }

    private long DELAY_TIME = 900;
    private static long lastClickTime;

    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < DELAY_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public final void onClick(View v) {
        if (isFastDoubleClick()) {
            return;
        }
        onFastClick(v);
    }

    /**
     * 快速点击事件回调方法
     *
     * @param v
     */
    public abstract void onFastClick(View v);
}