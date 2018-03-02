package zeffect.cn.common.file

import android.text.TextUtils
import java.io.*
import java.nio.channels.FileChannel
import java.security.MessageDigest


/**
 * Created by Administrator on 2017/10/14.
 */
object FileUtils {


    /***
     * 拷贝文件，根据输入和输入自动选择。
     */
    fun copy(input: String, output: String): Boolean {
        if (TextUtils.isEmpty(input) || TextUtils.isEmpty(output)) return false
        val inputFile = File(input)
        val outputFile = File(output)
        if (!inputFile.exists()) return false//输入文件不存在，更不对了。
        if (!outputFile.exists()) {
            return when {
                inputFile.isFile -> copyFile(input, output)
                inputFile.isDirectory -> copyFolder(input, output)
                else -> false
            }
        } else {
            return when {
                inputFile.isFile && outputFile.isFile -> copyFile(input, output)
                inputFile.isDirectory && outputFile.isDirectory -> copyFolder(input, output)
                else -> false
            }
        }
    }

    /***
     * 复制文件，输入和输入都是文件，否则返回false
     */
    fun copyFile(input: String, output: String): Boolean {
        if (TextUtils.isEmpty(input) || TextUtils.isEmpty(output)) return false
        val inputFile = File(input)
        val outputFile = File(output)
        if (!inputFile.exists()) return false//输入文件不存在，更不对了。
        if (inputFile.isDirectory) return false
        if (!outputFile.exists()) {
            outputFile.parentFile.mkdirs()
            try {
                outputFile.createNewFile()
            } catch (e: IOException) {
            }
        }
        if (outputFile.isDirectory) return false
        var inputChannel: FileChannel? = null
        var outputChannel: FileChannel? = null
        var success = false
        try {
            inputChannel = FileInputStream(inputFile).channel
            outputChannel = FileOutputStream(outputFile).channel
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size())
            success = true
        } catch (e: java.lang.Exception) {
            success = false
        } finally {
            inputChannel?.close()
            outputChannel?.close()
            return success
        }
    }

    /***
     * 拷贝文件夹
     */
    fun copyFolder(input: String, output: String): Boolean {
        if (TextUtils.isEmpty(input) || TextUtils.isEmpty(output)) return false
        val inputFile = File(input)
        val outputFile = File(output)
        if (!inputFile.exists()) return false//输入文件不存在，更不对了。
        if (!inputFile.isDirectory) return false
        if (!outputFile.exists()) outputFile.mkdirs()
        if (!outputFile.isDirectory) return false
        var copySuccess = true
        inputFile.listFiles().forEach {
            if (it.isFile) {
                if (!copyFile(it.absolutePath, File(outputFile, it.name).absolutePath)) {
                    copySuccess = false
                    return@forEach
                }
            } else if (it.isDirectory) {
                if (!copyFolder(it.absolutePath, File(outputFile, it.name).absolutePath)) {
                    copySuccess = false
                    return@forEach
                }
            }
        }
        return copySuccess
    }

    /****
     * 读取文件内容
     * @param filePath 路径
     */
    fun read(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) return ""
        val tempFile = File(filePath)
        if (!tempFile.exists() || tempFile.isDirectory) return ""
        val fileInput = FileInputStream(tempFile)
        return inputStream2String(fileInput)
    }

    /***
     * 写文件内容
     * @param filePath 路径
     * @param content 内容
     * @param append 是否追加
     */
    fun write(filePath: String, content: String, append: Boolean = false, charset: String = "UTF-8"): Boolean {
        try {
            if (TextUtils.isEmpty(filePath)) return false
            val tempFile = File(filePath)
            if (!tempFile.exists() || tempFile.isDirectory) return false
            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(filePath, append), charset))
            writer.write(content)
            writer.flush()
            writer.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }


    /**
     * 删除文件，或文件夹
     *
     * @param file 文件
     * @return 成功／失败
     */
    fun deleteFiles(file: File?): Boolean {
        if (file != null) {
            if (!file.exists()) {
                return true
            }
            if (file.isFile) {
                return file.delete()
            }
            if (!file.isDirectory) {
                return false
            }
            val listFiles = file.listFiles()
            if (listFiles != null) {
                for (f in listFiles) {
                    if (f.isFile) {
                        f.delete()
                    } else if (f.isDirectory) {
                        deleteFiles(f)
                    }
                }
                return file.delete()
            }
        }
        return false
    }


    /**
     * 计算文件的 MD5 值，用于比较两个文件是否相同。
     *
     * @param file
     * @return
     */
    fun getFileMD5(file: File): String? {
        if (file == null) return ""
        if (!file.isFile) {
            return ""
        }
        var `in`: FileInputStream? = null
        try {
            `in` = FileInputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return ""
        }

        return getFileMD5(`in`)
    }

    /**
     * 计算文件的 MD5 值
     *
     * @param file
     * @return
     */
    fun getFileMD5(`in`: InputStream?): String? {
        if (`in` == null) {
            return ""
        }
        val digest = MessageDigest.getInstance("MD5")
        val buffer = ByteArray(8192)
        try {
            var len = `in`.read(buffer)
            while (len != -1) {
                digest!!.update(buffer, 0, len)
                len = `in`.read(buffer)
            }
            return byteArrayToHexString(digest!!.digest())
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        } finally {
            try {
                `in`.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuffer()
        for (i in b.indices)
            resultSb.append(byteToHexString(b[i]))

        return resultSb.toString()
    }

    private val hexDigits = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0)
            n += 256
        val d1 = n / 16
        val d2 = n % 16
        return hexDigits[d1] + hexDigits[d2]
    }

    private fun inputStream2String(inputs: InputStream): String {
        val baos = ByteArrayOutputStream()
        try {
            var i = inputs.read()
            while (i != -1) {
                baos.write(i)
                i = inputs.read()
            }
        } catch (e: IOException) {
        } finally {
            return baos.toString()
        }
    }


}