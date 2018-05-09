package zeffect.cn.common.regex

import java.util.regex.Pattern

object RegexUtils {
    /**
     * 得到网页中图片的地址
     */
    fun getImgStr(htmlStr:String): Set<String> {
        val pics = HashSet<String>()
        val img = ""
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        val regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>"
        val p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE)
        val m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            val img = m_image.group()
            // 匹配<img>中的src数据
            val m = Pattern.compile ("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img)
            while (m.find()) {
                pics.add(m.group(1))
            }
        }
        return pics
    }
}