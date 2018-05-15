package zeffect.cn.usingcommon

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.jetbrains.anko.find
import zeffect.cn.common.app.AppUtils
import zeffect.cn.common.application.ExceptionHandler
import zeffect.cn.common.assets.AssetsUtils
import zeffect.cn.common.file.FileUtils
import zeffect.cn.common.gesture.OnGesture
import zeffect.cn.common.gesture.ZGesture
import zeffect.cn.common.intent.IntentUtils
import zeffect.cn.common.media.MediaUtils
import zeffect.cn.common.network.NetUtils
import java.io.File

class MainActivity : Activity(), View.OnClickListener {
    private var mZGesture: ZGesture? = null
    private var mView: View? = null
    private val mContext by lazy { this }
    private val showResult by lazy { find<TextView>(R.id.show_result) }
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
        find<View>(R.id.di4_getIp).setOnClickListener {
            showResult.text = "当前mac地址：${NetUtils.getMac()},ip${NetUtils.getWifiIp()}"
        }
        find<Button>(R.id.di5_getMainApp).setOnClickListener {
            showResult.text = ""
            AppUtils.getHasMainInfo(this).forEach {
                showResult.text = "${showResult.text}……包名${it.activityInfo.packageName},打开页面${if (!TextUtils.isEmpty(it.activityInfo.taskAffinity)) it.activityInfo.taskAffinity else it.activityInfo.name}"
            }
        }
        find<View>(R.id.di5_all).setOnClickListener {
            showResult.text = ""
            AppUtils.getApps(this, 2).forEach { showResult.text = "${showResult.text}......全部应用：${it.packageName}" }
        }
        find<View>(R.id.di5_user_install).setOnClickListener {
            showResult.text = ""
            AppUtils.getApps(this).forEach { showResult.text = "${showResult.text}......用户安装应用：${it.packageName}" }
        }
        find<View>(R.id.di5_system).setOnClickListener {
            showResult.text = ""
            AppUtils.getApps(this, 1).forEach { showResult.text = "${showResult.text}......系统应用：${it.packageName}" }
        }
        find<View>(R.id.di6_get_assets).setOnClickListener { showResult.text = AssetsUtils.fileString(this, "test") }

        find<View>(R.id.readAndWriteFiles).setOnClickListener {
            FileUtils.read(File(Environment.getExternalStorageDirectory(), "1.txt").absolutePath)
            FileUtils.write(File(Environment.getExternalStorageDirectory(), "2.txt").absolutePath, "写入内容${System.currentTimeMillis()}")
        }

        find<View>(R.id.testCatch).setOnClickListener {
            TestBtn!!.text = "123456"

        }

    }

    private val TestBtn: TextView? = null;

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