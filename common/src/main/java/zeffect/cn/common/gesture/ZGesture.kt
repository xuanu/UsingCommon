package com.qimon.commonlibrary.gesture

import android.content.Context
import android.view.MotionEvent
import android.view.ViewConfiguration
import zeffect.cn.common.gesture.OnGesture
import zeffect.cn.common.weak.WeakHandler

/**
 * Created by Administrator on 2017/9/8.
 * 对手势事件进行封装，有上下左右，单击，双击，长按，move,pressed,up
 *
 * 如果发现onFling没有回调，请设置View.setLongClickable(true)
 */
class ZGesture(context: Context, gesture: OnGesture?) {
    private val TAG = ZGesture::class.java.name
    private val mGestureInterface by lazy { gesture }
    private val mContext by lazy { context }
    private val mTouchSlop by lazy { ViewConfiguration.get(mContext).scaledTouchSlop }
    /**
     * 通过这个接口，得到触摸事件
     * */
    fun onTouchEvent(event: MotionEvent): Boolean {
        return doGesture(event)
    }

    /**按下点的坐标**/
    private var mDownPoints = Points(0f, 0f)
    private val mDoubleRun by lazy { DoubleRunnable(mGestureInterface) }
    private val mLongPrRun by lazy { LongPreRunnable(mGestureInterface, mDoubleRun, this) }
    /**是否取消一个手指的后续事件**/
    private var cancel1Finger = false
    /**取消2指事件**/
    private var cancel2Finger = false

    /**移动的最后一个点**/
    private var mMoveEndPoints = Points(0f, 0f)

    private fun doGesture(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                cancel1Finger = false
                mDownPoints.x1 = event.x
                mDownPoints.y1 = event.y
                mMoveEndPoints.x1 = event.x
                mMoveEndPoints.y1 = event.y
                mGestureInterface?.onDown(event)
                mLongPrRun.cancel(true)
                WeakHandler(mContext.mainLooper).removeCallbacks(mLongPrRun)
                val doubleCount = mDoubleRun.getCount()
                if (doubleCount >= 2) {
                    mDoubleRun.clearCount()
                    mDoubleRun.isLong(false)
                    mDoubleRun.cancel(true)
                    WeakHandler(mContext.mainLooper).removeCallbacks(mDoubleRun)
                }
                if (doubleCount == 0) {
                    mDoubleRun.cancel(false)
                    mDoubleRun.isLong(false)
                    WeakHandler(mContext.mainLooper).postDelayed(mDoubleRun.apply { this.mEvent = event }, ViewConfiguration.getDoubleTapTimeout().toLong())
                }
                mLongPrRun.cancel(false)
                WeakHandler(mContext.mainLooper).postDelayed(mLongPrRun.apply { this.mEvent = event }, ViewConfiguration.getLongPressTimeout().toLong())
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                cancel2Finger = false
                mDoubleRun.cancel(true)
                WeakHandler(mContext.mainLooper).removeCallbacks(mDoubleRun)
                mLongPrRun.cancel(true)
                WeakHandler(mContext.mainLooper).removeCallbacks(mLongPrRun)
                val pointCount = event.pointerCount
                when (pointCount) {
                    2 -> {
                        cancel1Finger = true;mGestureInterface?.cancelFinger(1)
                    }
                    in 3..10 -> {
                        cancel2Finger = true;mGestureInterface?.cancelFinger(2)
                    }
                }
                //存下第二个点的位置
                mDownPoints.x2 = event.getX(1)
                mDownPoints.y2 = event.getY(1)
                mMoveEndPoints.x2 = event.getX(1)
                mMoveEndPoints.y2 = event.getY(1)
            }
            MotionEvent.ACTION_MOVE -> {
                mGestureInterface?.onMove(event)
                when (event.pointerCount) {
                    1 -> {//一指移动
                        if (Math.abs(event.x - mDownPoints.x1) > ViewConfiguration.get(mContext).scaledTouchSlop
                                || Math.abs(event.y - mDownPoints.y1) > ViewConfiguration.get(mContext).scaledTouchSlop) {
                            //移动距离大于感知距离
                            mLongPrRun.cancel(true)
                            WeakHandler(mContext.mainLooper).removeCallbacks(mLongPrRun)
                            mDoubleRun.cancel(true)
                            WeakHandler(mContext.mainLooper).removeCallbacks(mDoubleRun)
                            //加上一指的移动事件吧,直接用现在的点，减掉上一次的点
                            val x1s = event.x - mMoveEndPoints.x1
                            val y1s = event.y - mMoveEndPoints.y1
                            if (Math.abs(x1s) > Math.abs(y1s)) {
                                if (x1s > 0) mGestureInterface?.onRightMove(x1s) else mGestureInterface?.onLeftMove(x1s)
                            } else {
                                if (y1s > 0) mGestureInterface?.onBottomMove(y1s) else mGestureInterface?.onTopMove(y1s)
                            }

                        }
                    }
                    2 -> {//两指计算缩放
                        //应该是和上一次的点比较，而不是按下的点
                        //不同的方向，算缩放，相同的方向，算其它操作
                        val newPoints = Points(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
                        val direction = direction(newPoints, mMoveEndPoints)
                        when (direction.sameDirection) {
                            1 -> {//两指往相同方向移动
                                when (direction.direction) {
                                    1 -> {
                                        mGestureInterface?.on2TopMove(direction.space)
                                        mGestureInterface?.on2TopMove(mMoveEndPoints, newPoints, direction.space)
                                    }
                                    2 -> {
                                        mGestureInterface?.on2BottomMove(direction.space)
                                        mGestureInterface?.on2BottomMove(mMoveEndPoints, newPoints, direction.space)
                                    }
                                    3 -> {
                                        mGestureInterface?.on2LeftMove(direction.space)
                                        mGestureInterface?.on2LeftMove(mMoveEndPoints, newPoints, direction.space)
                                    }
                                    4 -> {
                                        mGestureInterface?.on2RightMove(direction.space)
                                        mGestureInterface?.on2RightMove(mMoveEndPoints, newPoints, direction.space)
                                    }
                                }
                            }
                            -1 -> {
                                val space = space(mMoveEndPoints, newPoints)
                                if (Math.abs(space) > ViewConfiguration.get(mContext).scaledTouchSlop) {
                                    if (space > 0) {
                                        mGestureInterface?.on2ZoomBigMove(space)
                                        mGestureInterface?.on2ZoomBigMove(mMoveEndPoints, newPoints, space)
                                    } else {
                                        mGestureInterface?.on2ZoomSmallMove(space)
                                        mGestureInterface?.on2ZoomSmallMove(mMoveEndPoints, newPoints, space)
                                    }
                                }
                            }
                        }
                        mMoveEndPoints.x2 = newPoints.x2;mMoveEndPoints.y2 = newPoints.y2
                    }
                }
                mMoveEndPoints.x1 = event.x;mMoveEndPoints.y1 = event.y;
            }
            MotionEvent.ACTION_POINTER_UP -> {
            }
            MotionEvent.ACTION_UP -> {
                mGestureInterface?.onUp(event)
                //自己抬起来，就不算长按了
                if (mDoubleRun.getIsLong()) {
                    mDoubleRun.clearCount()
                    mDoubleRun.cancel(true)
                    WeakHandler(mContext.mainLooper).removeCallbacks(mDoubleRun)
                }
                mLongPrRun.cancel(true)
                WeakHandler(mContext.mainLooper).removeCallbacks(mLongPrRun)
                if (Math.abs(event.x - mDownPoints.x1) < ViewConfiguration.get(mContext).scaledTouchSlop
                        && Math.abs(event.y - mDownPoints.y1) < ViewConfiguration.get(mContext).scaledTouchSlop) {
                    mDoubleRun.addCount()
                    if (mDoubleRun.getIsLong()) mDoubleRun.clearCount()
                } else {
                    if (!cancel1Finger) {
                        //手松开时，距离大一点
                        val tempX = event.x.minus(mDownPoints.x1)
                        val tempY = event.y.minus(mDownPoints.y1)
                        if (Math.abs(tempX) > Math.abs(tempY)) {
                            if (tempX > 0) mGestureInterface?.onRightUp()
                            else mGestureInterface?.onLeftUp()

                        } else {
                            if (tempY > 0) mGestureInterface?.onBottomUp()
                            else mGestureInterface?.onTopUp()

                        }
                    } else {
                        //取消了一级手势，没有取消2个手指
                        if (!cancel2Finger) {
                            if ((mMoveEndPoints.x2 != 0f || mMoveEndPoints.y2 != 0f) && (mDownPoints.x2 != 0f || mDownPoints.y2 != 0f)) {
                                val direction = direction(mMoveEndPoints, mDownPoints)
                                when (direction.sameDirection) {
                                    1 -> {//两指往相同方向移动
                                        when (direction.direction) {
                                            1 -> {
                                                mGestureInterface?.on2TopUp(direction.space)
                                                mGestureInterface?.on2TopUp(mDownPoints, mMoveEndPoints, direction.space)
                                            }
                                            2 -> {
                                                mGestureInterface?.on2BottomUp(direction.space)
                                                mGestureInterface?.on2BottomUp(mDownPoints, mMoveEndPoints, direction.space)
                                            }
                                            3 -> {
                                                mGestureInterface?.on2LeftUp(direction.space)
                                                mGestureInterface?.on2LeftUp(mDownPoints, mMoveEndPoints, direction.space)
                                            }
                                            4 -> {
                                                mGestureInterface?.on2RightUp(direction.space)
                                                mGestureInterface?.on2RightUp(mDownPoints, mMoveEndPoints, direction.space)
                                            }
                                        }
                                    }
                                    -1 -> {
                                        val space = space(mDownPoints, mMoveEndPoints)
                                        if (Math.abs(space) > ViewConfiguration.get(mContext).scaledTouchSlop) {
                                            if (space > 0) {
                                                mGestureInterface?.on2ZoomBigUp(space)
                                                mGestureInterface?.on2ZoomBigUp(mDownPoints, mMoveEndPoints, space)
                                            } else {
                                                mGestureInterface?.on2ZoomSmallUp(space)
                                                mGestureInterface?.on2ZoomSmallUp(mDownPoints, mMoveEndPoints, space)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //手指抬起来之后，一些状态恢复
                cancel2Finger = false
                cancel1Finger = false
                mDownPoints = Points(0f, 0f)
                mMoveEndPoints = Points(0f, 0f)
            }
        }
        return true
    }


    private class DoubleRunnable(gesture: OnGesture?) : Runnable {
        private var isLong = false
        private var cancel: Boolean = false
        private var clickCount = 0
        private val mDoubleGesture by lazy { gesture }
        var mEvent: MotionEvent? = null
        override fun run() {
            if (!cancel && !isLong) {
                when (clickCount) {
                    in 2..10 -> {
                        mDoubleGesture?.onDoubleUp()
                    }
                    1 -> {
                        mDoubleGesture?.onSingleUp()
                    }
                }
            }
            clickCount = 0
            mEvent = null
        }

        fun addCount() {
            clickCount++
        }

        fun clearCount() {
            clickCount = 0
        }

        fun getCount(): Int {
            return clickCount
        }

        fun cancel(cancel: Boolean) {
            this.cancel = cancel
            mEvent = null
        }

        fun isLong(tIsLong: Boolean) {
            this.isLong = tIsLong
            if (isLong) clearCount()
        }

        fun getIsLong(): Boolean {
            return isLong
        }
    }

    private class LongPreRunnable(gesture: OnGesture?, doubleRunnable: DoubleRunnable, zGesture: ZGesture) : Runnable {
        private var cancel: Boolean = false
        private val mLongGesture by lazy { gesture }
        private val mlDoubleRun by lazy { doubleRunnable }
        private val mlZesture by lazy { zGesture }
        var mEvent: MotionEvent? = null
        override fun run() {
            if (!cancel) {
                mLongGesture?.onLongClick()
            }
            mlDoubleRun.isLong(true)
            mlDoubleRun.cancel(true)
            mlZesture.cancel1Finger = true
            mEvent = null
        }

        fun cancel(cancel: Boolean) {
            this.cancel = cancel
            mEvent = null
        }
    }

    /**双指，两个坐标点**/
    data class Points(var x1: Float, var y1: Float, var x2: Float = 0f, var y2: Float = 0f)

    /**
     * 计算两坐标点间隔
     *
     * @param p0 旧的两点
     * @param p1 新的两点
     * ***/
    private fun space(p0: Points, p1: Points): Double {
        val p0x = Math.abs(p0.x1 - p0.x2)
        val p0y = Math.abs(p0.y1 - p0.y2)
        val p0sapce = Math.sqrt(p0x * p0x * 1.0 + p0y * p0y)
        val p1x = Math.abs(p1.x1 - p1.x2)
        val p1y = Math.abs(p1.y1 - p1.y2)
        val p1space = Math.sqrt(p1x * p1x * 1.0 + p1y * p1y)
        return p1space - p0sapce
    }

    /***
     * 计算两点间隔距离
     */
    private fun spaceTwoPoint(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val sp0x = Math.abs(x2 - x1)
        val sp0y = Math.abs(y2 - y1)
        return Math.sqrt(sp0x * sp0x * 1.0 + sp0y * sp0y).toFloat()
    }

    /**
     * 判断新旧两点的方向，相同还是相反
     *
     * @param points1 最新的点
     * @param points0 上一次一点
     * @return 0 代表无操作，1相同方向，-1不同方向
     */
    private fun direction(points1: Points, points0: Points): Zdirection {
        val direct = Zdirection()
        val x1s = (points1.x1 - points0.x1).toInt()
        val y1s = (points1.y1 - points0.y1).toInt()
        val x2s = (points1.x2 - points0.x2).toInt()
        val y2s = (points1.y2 - points0.y2).toInt()
        return when {
            x1s == 0 && x2s == 0 && y1s == 0 && y2s == 0 -> direct
            sameSign(x1s, x2s) && sameSign(y1s, y2s) -> {
                direct.sameDirection = 1
                //计算方向是上下左右和距离，只要其中一点的距离，大于感知距离就算移动了，并且使用距离较大的值
                val sp1 = spaceTwoPoint(points1.x1, points1.y1, points0.x1, points0.y1)
                val sp2 = spaceTwoPoint(points1.x2, points1.y2, points0.x2, points0.y2)
                if ((sp1 > mTouchSlop) || (sp2 > mTouchSlop)) {
                    if (sp1 > sp2) {
                        val sp1x = points1.x1 - points0.x1
                        val sp1y = points1.y1 - points0.y1
                        if (Math.abs(sp1x) > Math.abs(sp1y)) {
                            //在x上移动
                            direct.direction = if (sp1x < 0) 3 else 4
                            direct.space = Math.abs(sp1x)
                        } else {
                            //在Y上移动
                            direct.direction = if (sp1y < 0) 1 else 2
                            direct.space = Math.abs(sp1y)
                        }
                    } else {
                        val sp2x = points1.x2 - points0.x2
                        val sp2y = points1.y2 - points0.y2
                        if (Math.abs(sp2x) > Math.abs(sp2y)) {
                            //在x上移动
                            direct.direction = if (sp2x < 0) 3 else 4
                            direct.space = Math.abs(sp2x)
                        } else {
                            //在Y上移动
                            direct.direction = if (sp2y < 0) 1 else 2
                            direct.space = Math.abs(sp2y)
                        }
                    }
                }
                direct
            }
            !sameSign(x1s, x2s) && !sameSign(y1s, y2s) -> {
                direct.sameDirection = -1
                //方向相反，做缩放，这里就暂时不用
                direct
            }
            else -> direct
        }
    }

    /**是否为相同的符号**/
    private fun sameSign(a: Int, b: Int): Boolean {
        return a * b >= 0
    }

    /***
     * @param direction 1234代表上下左右
     */
    private data class Zdirection(var sameDirection: Int = 0, var direction: Int = 0, var space: Float = 0f)


}