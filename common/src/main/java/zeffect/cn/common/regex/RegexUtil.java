package zeffect.cn.common.regex;

import android.text.TextUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {


    /**
     * 给html图片添加默认头
     *
     * @param htmlStr html内容
     * @param imgHead 默认头
     * @return
     */
    public static String appendImgHead(String htmlStr, String imgHead) {
        if (TextUtils.isEmpty(imgHead)) imgHead = "http://";
        StringBuilder builder = new StringBuilder();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        int start = 0;
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            builder.append(htmlStr.substring(start, m_image.start()));
            int secondStart = 0;
            while (m.find()) {
                String imgUrl = m.group(1);
                builder.append(img.substring(secondStart, m.start(1)));
                if (imgUrl.startsWith("http://")
                        || imgUrl.startsWith("https://")
                        || imgUrl.startsWith("assets://")
                        || imgUrl.startsWith("file://")
                        || imgUrl.startsWith("drawable://")
                        || imgUrl.startsWith("content://")) {
                } else {
                    imgUrl = imgHead + imgUrl;
                }
                builder.append(imgUrl);
                secondStart = m.end(1);
            }
            if (secondStart < img.length()) builder.append(img.substring(secondStart));
            start = m_image.end();
        }
        if (start < htmlStr.length()) builder.append(htmlStr.substring(start));
        return builder.toString();
    }

    /**
     * 用空格和标点分隔英文单词
     *
     * @param inputStr
     * @return
     */
    public static Set<String> findEngWords(String inputStr) {
        return findEngWords(inputStr, 5);
    }


    /**
     * 用空格和标点分隔英文单词
     *
     * @param inputStr
     * @param minLength
     * @return
     */
    public static Set<String> findEngWords(String inputStr, int minLength) {
        if (inputStr == null || inputStr.trim().length() <= 0) {
            return Collections.emptySet();
        }
        Set<String> results = new HashSet<String>();
        Pattern pattern = Pattern.compile("\\p{P}");
        String[] datas = inputStr.split("[\\s+]|[,|\\.]");
        for (int i = 0; i < datas.length; i++) {
            String tempStr = datas[i];
            if (tempStr == null) {
                continue;
            }
            if (minLength <= 0) {
                minLength = 5;
            }
            int lenght = tempStr.trim().length();
            if (lenght < minLength) {
                continue;
            }
            //不是英文的不要。
            if (!tempStr.matches("[a-zA-Z]+")) {
                continue;
            }
            // 包含标点不要
            Matcher matcher = pattern.matcher(tempStr);
            if (matcher.find()) {
                continue;
            }
            results.add(tempStr);
        }
        return results;
    }
}
