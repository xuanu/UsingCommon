package zeffect.cn.common.regex;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    /**
     * 用空格和标点分隔英文单词
     * @param inputStr
     * @return
     */
    public static Set<String> findEngWords(String inputStr) {
        return findEngWords(inputStr, 5);
    }


    /**
     * 用空格和标点分隔英文单词
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
