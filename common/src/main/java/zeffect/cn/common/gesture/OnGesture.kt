package zeffect.cn.common.gesture

import android.view.MotionEvent

/**
 * Created by Administrator on 2017/9/9.
 */

abstract class OnGesture {
    fun onTopUp() {}
    fun onBottomUp() {}
    open fun onLeftUp() {}
    fun onRightUp() {}
    fun onTopMove(pd: Float?) {}
    fun onBottomMove(pd: Float?) {}
    fun onLeftMove(pd: Float?) {}
    fun onRightMove(pd: Float?) {}
    fun onDown(pEvent: MotionEvent) {}
    fun onUp(pEvent: MotionEvent) {}
    fun onMove(event: MotionEvent) {}
    /**
     * 单击
     */
    fun onSingleUp() {}

    /**
     * 双击
     */
    fun onDoubleUp() {}

    /**
     * 长按
     */
    fun onLongClick() {}

    //**********************双指手势区
    fun on2ZoomBigMove(pDouble: Double?) {}

    fun on2ZoomBigUp(pDouble: Double?) {}
    fun on2ZoomSmallMove(pDouble: Double?) {}
    fun on2ZoomSmallUp(pDouble: Double?) {}
    fun on2TopMove(pFloat: Float?) {}
    fun on2BottomMove(pFloat: Float?) {}
    fun on2LeftMove(pFloat: Float?) {}
    fun on2RightMove(pFloat: Float?) {}
    fun on2TopUp(pFloat: Float?) {}
    fun on2BottomUp(pFloat: Float?) {}
    open fun on2LeftUp(pFloat: Float?) {}
    open fun on2RightUp(pFloat: Float?) {}

    /***
     * 双指放大
     * @param pDouble 两次间隔
     * @param pPoints0 上一次坐标
     * @param pPoints1 新的坐标
     */
    fun on2ZoomBigMove(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pDouble: Double?) {

    }

    /***
     * 放大，抬起时
     * @param pPoints0 按下时坐标
     * @param pPoints1 抬起时坐标
     * @param pDouble 间隔
     */
    fun on2ZoomBigUp(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pDouble: Double?) {}

    fun on2ZoomSmallMove(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pDouble: Double?) {

    }

    fun on2ZoomSmallUp(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pDouble: Double?) {

    }

    fun on2TopMove(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pFloat: Float?) {}

    fun on2BottomMove(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pFloat: Float?) {}

    fun on2LeftMove(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pFloat: Float?) {}

    fun on2RightMove(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pFloat: Float?) {}

    fun on2TopUp(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pFloat: Float?) {}

    fun on2BottomUp(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pFloat: Float?) {}

    fun on2LeftUp(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pFloat: Float?) {}

    fun on2RightUp(pPoints0: ZGesture.Points, pPoints1: ZGesture.Points, pFloat: Float?) {}

    //***************************取消事件

    /***
     * 取消手势，这个等我先想一下。
     * 移动的时候可以得到当前手指，但抬起就得不到，所以，取消了就取消了，没有了。
     * 按下两个手指，取消一个手指事件，同理3取2
     * 应该没有人要取消长按和单双击的回调吧。
     * @param finger  取消了几个手指的抬起事件
     */
    fun cancelFinger(finger: Int) {

    }

}
