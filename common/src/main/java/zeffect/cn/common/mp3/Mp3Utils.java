package zeffect.cn.common.mp3;

import android.annotation.TargetApi;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * http://blog.csdn.net/hunanqi/article/details/52384892
 * Created by Administrator on 2018/1/23.
 */

public class Mp3Utils {
    /***
     * mp3合并
     * @param savePath
     * @param paths
     * @return
     * @throws IOException
     */
    public static String heBingMp3(File savePath, String... paths) throws IOException {
        //生成处理后的文件
        if (savePath == null) return "";
        if (!savePath.exists()) savePath.getParentFile().mkdirs();
        if (savePath.isDirectory()) savePath.delete();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(savePath, true));
        BufferedInputStream bufferedInputStream = null;
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            if (TextUtils.isEmpty(path)) continue;
            String fenLiData = fenLiData(path);
            if (TextUtils.isEmpty(fenLiData)) continue;
            File file = new File(fenLiData);
            if (file == null) continue;
            if (!file.exists()) continue;
            if (file.isDirectory()) continue;
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            int read = 0;
            while ((read = bufferedInputStream.read()) > -1) {
                bufferedOutputStream.write(read);
            }
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
        return savePath.getAbsolutePath();
    }

    private static String fenLiData(String path) throws IOException {
        if (TextUtils.isEmpty(path)) return "";
        File file = new File(path);// 原文件
        if (!file.exists()) return "";
        if (!file.isFile()) return "";
        File file1 = new File(path + "01");// 分离ID3V2后的文件,这是个中间文件，最后要被删除
        File file2 = new File(path + "001");// 分离id3v1后的文件
        RandomAccessFile rf = new RandomAccessFile(file, "rw");// 随机读取文件
        FileOutputStream fos = new FileOutputStream(file1);
        byte ID3[] = new byte[3];
        rf.read(ID3);
        String ID3str = new String(ID3);
        // 分离ID3v2
        if (ID3str.equals("ID3")) {
            rf.seek(6);
            byte[] ID3size = new byte[4];
            rf.read(ID3size);
            int size1 = (ID3size[0] & 0x7f) << 21;
            int size2 = (ID3size[1] & 0x7f) << 14;
            int size3 = (ID3size[2] & 0x7f) << 7;
            int size4 = (ID3size[3] & 0x7f);
            int size = size1 + size2 + size3 + size4 + 10;
            rf.seek(size);
            int lens = 0;
            byte[] bs = new byte[1024 * 4];
            while ((lens = rf.read(bs)) != -1) {
                fos.write(bs, 0, lens);
            }
            fos.close();
            rf.close();
        } else {// 否则完全复制文件
            int lens = 0;
            rf.seek(0);
            byte[] bs = new byte[1024 * 4];
            while ((lens = rf.read(bs)) != -1) {
                fos.write(bs, 0, lens);
            }
            fos.close();
            rf.close();
        }
        RandomAccessFile raf = new RandomAccessFile(file1, "rw");
        byte TAG[] = new byte[3];
        raf.seek(raf.length() - 128);
        raf.read(TAG);
        String tagstr = new String(TAG);
        if (tagstr.equals("TAG")) {
            FileOutputStream fs = new FileOutputStream(file2);
            raf.seek(0);
            byte[] bs = new byte[(int) (raf.length() - 128)];
            raf.read(bs);
            fs.write(bs);
            raf.close();
            fs.close();
        } else {// 否则完全复制内容至file2
            FileOutputStream fs = new FileOutputStream(file2);
            raf.seek(0);
            byte[] bs = new byte[1024 * 4];
            int len = 0;
            while ((len = raf.read(bs)) != -1) {
                fs.write(bs, 0, len);
            }
            raf.close();
            fs.close();
        }
        if (file1.exists())// 删除中间文件
        {
            file1.delete();

        }
        return file2.getAbsolutePath();
    }

    private static final int SAMPLE_SIZE = 1024 * 200;

    /***
     * mp3 裁剪
     * @param inputPath
     * @param outputPath
     * @param start
     * @param end
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean clip(String inputPath, String outputPath, int start, int end) {
        if (TextUtils.isEmpty(inputPath) || TextUtils.isEmpty(outputPath)) return false;
        if (start < 0 || end < start) return false;
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) return false;
        if (!inputFile.isFile()) return false;
        File outFile = new File(outputPath);
        if (!outFile.exists()) outFile.getParentFile().mkdir();
        if (outFile.isDirectory()) outFile.delete();
        MediaExtractor extractor = null;
        BufferedOutputStream outputStream = null;
        try {
            extractor = new MediaExtractor();
            extractor.setDataSource(inputPath);
            int track = getAudioTrack(extractor);
            if (track < 0) {
                return false;
            }
            //选择音频轨道
            extractor.selectTrack(track);
            outputStream = new BufferedOutputStream(
                    new FileOutputStream(outputPath), SAMPLE_SIZE);
            start = start * 1000;
            end = end * 1000;
            //跳至开始裁剪位置
            extractor.seekTo(start, MediaExtractor.SEEK_TO_PREVIOUS_SYNC);
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(SAMPLE_SIZE);
                int sampleSize = extractor.readSampleData(buffer, 0);
                long timeStamp = extractor.getSampleTime();
                // >= 1000000是要裁剪停止和指定的裁剪结尾不小于1秒，否则可能产生需要9秒音频
                //裁剪到只有8.6秒，大多数音乐播放器是向下取整，这样对于播放器变成了8秒，
                // 所以要裁剪比9秒多一秒的边界
                if (timeStamp > end) {//&& timeStamp - end >= 1000000
                    break;
                }
                if (sampleSize <= 0) {
                    break;
                }
                byte[] buf = new byte[sampleSize];
                buffer.get(buf, 0, sampleSize);
                //写入文件
                outputStream.write(buf);
                //音轨数据往前读
                extractor.advance();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (extractor != null) {
                extractor.release();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 获取音频数据轨道
     *
     * @param extractor
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static int getAudioTrack(MediaExtractor extractor) {
        for (int i = 0; i < extractor.getTrackCount(); i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith("audio")) {
                return i;
            }
        }
        return -1;
    }
}
