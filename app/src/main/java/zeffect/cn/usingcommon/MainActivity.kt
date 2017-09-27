package zeffect.cn.usingcommon

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.qimon.commonlibrary.gesture.ZGesture
import zeffect.cn.common.gesture.OnGesture
import zeffect.cn.common.intent.IntentUtils

class MainActivity : Activity(), View.OnClickListener {
    private var mZGesture: ZGesture? = null
    private var mView: View? = null
    private val mContext by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mView = findViewById(R.id.root_layout)
        //
        findViewById<View>(R.id.di1_layout).setOnClickListener(this)
        mZGesture = ZGesture(this, mOnGesture)
        findViewById<View>(R.id.di2_layout).setOnTouchListener { _, pEvent -> mZGesture!!.onTouchEvent(pEvent) }
        findViewById<View>(R.id.di3_open_phone_no_number).setOnClickListener(this)
        findViewById<View>(R.id.di3_open_camera).setOnClickListener(this)
        findViewById<View>(R.id.di3_open_phone_with_number).setOnClickListener(this)
        findViewById<View>(R.id.di3_open_record_audio).setOnClickListener(this)
        findViewById<View>(R.id.di3_open_setting).setOnClickListener(this)
        findViewById<View>(R.id.di3_open_sms).setOnClickListener(this)
        findViewById<View>(R.id.di3_open_sms_with_number).setOnClickListener(this)
        findViewById<View>(R.id.di3_open_sms_with_number_with_content).setOnClickListener(this)
        findViewById<View>(R.id.di3_open_txl).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.di3_open_camera -> IntentUtils.openCarmae(this)
            R.id.di3_open_phone_no_number -> IntentUtils.openPhone(mContext)
            R.id.di3_open_phone_with_number -> IntentUtils.openPhone(mContext, 10086)
            R.id.di3_open_record_audio -> IntentUtils.openRecord(mContext)
            R.id.di3_open_setting -> IntentUtils.openSetting(mContext)
            R.id.di3_open_sms -> IntentUtils.openSms(mContext)
            R.id.di3_open_sms_with_number -> IntentUtils.openSms(mContext, 10086)
            R.id.di3_open_sms_with_number_with_content -> IntentUtils.openSms(mContext, 10086, "短信内容")
            R.id.di3_open_txl -> IntentUtils.openPeople(mContext)
        }
    }


    private val mOnGesture = object : OnGesture() {

        override fun onLeftUp() {
            super.onLeftUp()
            toast("单指左抬起")
        }

        override fun on2LeftUp(pFloat: Float?) {
            super.on2LeftUp(pFloat)
            toast("双指向左抬起")
        }

        override fun on2RightUp(pFloat: Float?) {
            super.on2RightUp(pFloat)
            toast("双指向右抬起")
        }
    }


    private fun toast(message: String?) {
        var message = message
        if (message == null) message = "传入参数为Null"
        Snackbar.make(mView!!, message, Snackbar.LENGTH_SHORT).show()
    }

}