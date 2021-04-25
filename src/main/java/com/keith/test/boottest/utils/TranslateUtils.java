package com.keith.test.boottest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 翻译工具类
 *
 * @author keith
 * @since 2021-03-26
 */
@Slf4j
public class TranslateUtils {
    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";

    private static final String APP_KEY = "42e1b3ddf0a7383b";

    private static final String APP_SECRET = "4Da3KiKIuTAOxIZsJLYyUcDn3dKF6grg";

    public static String translateSingle(String text, String sourceLang, String targetLang) {
        Map<String, Object> params = new HashMap<>(16);
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", sourceLang);
        params.put("to", targetLang);
        params.put("signType", "v3");
        String currTime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", currTime);
        String signStr = APP_KEY + truncate(text) + salt + currTime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", text);
        params.put("salt", salt);
        params.put("sign", sign);
        // 处理结果
        String result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = HttpClientUtil.httpsPost(YOUDAO_URL, null, params, ContentType.APPLICATION_FORM_URLENCODED.getMimeType(), null);
        } catch (Exception e) {
            log.error("有道翻译调用失败，params:{}", JSON.toJSONString(params), e);
        }
        log.info("有道翻译,text:{},result,{},cost:{}", text, result, System.currentTimeMillis() - startTime);
        JSONObject resultJson = JSON.parseObject(result);

        String successCode = "0";
        if (resultJson == null || !successCode.equals(resultJson.getString("errorCode"))) {
            return null;
        }
        return resultJson.getJSONArray("translation").get(0).toString();
    }

    /**
     * 生成加密字段
     */
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     *
     * @param result 音频字节流
     * @param file 存储路径
     */
    private static void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        }catch (Exception e){
            log.error("", e);
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}
