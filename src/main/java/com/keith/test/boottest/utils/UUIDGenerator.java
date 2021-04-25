package com.keith.test.boottest.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * uuid生成器
 *
 * @author keith
 * @since 2021-03-26
 */
public class UUIDGenerator {
    public UUIDGenerator() {
    }

    public static String getUUID() {
        String strUUID = UUID.randomUUID().toString().replaceAll("\\-", "");
        StringBuffer sbReturn = new StringBuffer();
        sbReturn.append(strUUID).append(System.nanoTime());
        return DigestUtils.md5Hex(sbReturn.toString()).toLowerCase();
    }

    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        } else {
            String[] ss = new String[number];

            for(int i = 0; i < number; ++i) {
                ss[i] = getUUID();
            }

            return ss;
        }
    }
}
