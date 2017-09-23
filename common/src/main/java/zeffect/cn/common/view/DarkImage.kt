package zeffect.cn.common.view

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.ImageView
import java.util.jar.Attributes

/**
 * Created by Administrator on 2017/9/23.
 */
class DarkImage : ImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        if (pressed) {
            drawable?.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
            background?.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
        } else {
            drawable?.clearColorFilter()
            background?.clearColorFilter()
        }
    }
}