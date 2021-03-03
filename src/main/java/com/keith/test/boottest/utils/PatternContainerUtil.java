package com.keith.test.boottest.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 正则表达式容器工具类
 * 使用频率高的正则表达式可以考虑放入容器中
 *
 * @author Luke
 * @date 2019-08-08 14:40
 */
public class PatternContainerUtil {
    private static Map<String, Pattern> PATTERN_CONTAINER;

    static {
        PATTERN_CONTAINER = new HashMap<>(16);
    }

    /**
     * 获取对应的pattern
     *
     * @param regex 表达式
     */
    public static Pattern getPattern(String regex) {
        Pattern pattern = PATTERN_CONTAINER.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            PATTERN_CONTAINER.put(regex, pattern);
        }
        return pattern;
    }
}
