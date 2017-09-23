package zeffect.cn.common.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.Button

/**
 * Created by Administrator on 2017/9/23.
 */
class DarkButton: Button {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)
    constructor(context: Context, attributes: AttributeSet, default: Int) : super(context, attributes, default)

    private var mColors: ColorStateList? = null
    private var mColor: Int? = null

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        if (mColors == null) mColors = textColors
        if (mColor == null) {
            mColor = mColors?.getColorForState(intArrayOf(), Color.GRAY)
            val alpha = (Color.alpha(mColor!!) * 0.5).toInt()
            mColor = Color.argb(alpha, Color.red(mColor!!), Color.green(mColor!!), Color.blue(mColor!!))
        }
        if (pressed) {
            setTextColor(if (mColor != null) mColor!! else Color.GRAY)
            compoundDrawables.forEach { it?.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY) }
            background?.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
        } else {
            setTextColor(mColors)
            compoundDrawables.forEach { it?.clearColorFilter() }
            background?.clearColorFilter()
        }
    }
}