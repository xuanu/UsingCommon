package zeffect.cn.common.intent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File


/**
 * Created by Administrator on 2017/9/16.
 */
object IntentUtils {
    /***
     * 打开相机，拍摄图片
     * @param activity 页面
     * @param file 图片保存路径
     * @param code 返回码
     */
    fun openCamera(activity: Activity, file: File = File(Environment.getExternalStorageDirectory(), "opencamera.png"), code: Int = 0x64) {
        val intentFromCapture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        activity.startActivityForResult(intentFromCapture, code)
    }

    /**
     * 从本地相册选取取图片
     *
     * @param activity    上下文
     * @param requestCode 请求码
     * @see ChoseImage.getGalleryPath
     */
    fun openGallery(activity: Activity, requestCode: Int) {
        val intentFromGallery = Intent()
        intentFromGallery.type = "image/*"
        intentFromGallery.action = Intent.ACTION_PICK
        activity.startActivityForResult(intentFromGallery, requestCode)
    }

    /***
     * 获取路径，通用
     *
     * @param pContext 上下文
     * @param pIntent  用choseImageFromGallery返回的intent
     * @return 绝对路径
     * @see ChoseImage.choseImageFromGallery
     */
    fun getGalleryPath(pContext: Context, pIntent: Intent?): String {
        var localPath = ""
        if (pIntent != null) {
            if (pIntent.data != null) {
                val cr = pContext.contentResolver.query(pIntent.data,
                        arrayOf(MediaStore.Images.Media.DATA), null, null, null) ?: return localPath
                if (cr.moveToFirst()) {
                    localPath = cr.getString(cr
                            .getColumnIndex(MediaStore.Images.Media.DATA))
                }
                cr.close()
            }
        }
        return localPath
    }


}