package com.keith.test.boottest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mick
 * @date 2016/10/27
 */
public class HttpClientUtil {

    /**
     * httpsGet2Map 方法 返回map 对应key - data
     */
    public static final String GET_MAP_KEY_DATA = "data";
    /**
     * httpsGet2Map 方法 返回map 对应key - header
     */
    private static final String DEFAULT_CHARSET_UTF8 = "UTF-8";
    private static final String DEFAULT_CONTENT_TYPE_JSON = "application/json";

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static CookieStore sslcookies = new BasicCookieStore();
    private static CookieStore cookies = new BasicCookieStore();

    public static final String EMPTY_STR = "";

    public static CloseableHttpClient createSSLClientDefault(boolean isSSL) {
        if (isSSL) {
            SSLContext sslContext = null;
            try {
                sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    //信任所有
                    @Override
                    public boolean isTrusted(X509Certificate[] chain,
                                             String authType) throws CertificateException {
                        return true;
                    }
                }).build();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            if (null != sslContext) {
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
                return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(sslcookies).build();
            } else {
                return HttpClients.custom().setDefaultCookieStore(cookies).build();
            }
        } else {
            return HttpClients.custom().setDefaultCookieStore(cookies).build();
        }
    }


    /**
     * 发送https post请求
     *
     * @param action    url
     * @param bodyParam 参数
     * @return String
     * @throws Exception 异常
     */
    public static String httpsPost(String action, Object bodyParam) throws Exception {
        return httpsPost(action, null, bodyParam, null, null);
    }


    /**
     * 发送https post请求
     *
     * @param action    url
     * @param bodyParam 参数
     * @return String
     * @throws Exception 异常
     */
    public static String httpsPost(String action, Map<String, String> headerParam, Object bodyParam) throws Exception {
        return httpsPost(action, headerParam, bodyParam, null, null);
    }


    /**
     * 发送https post请求
     *
     * @param action 请求地址
     * @return String
     * @throws Exception 异常
     */
    public static String httpsPost(String action, Map<String, String> headerParam,
                                   Object bodyParam, String contentType, String charSet) throws Exception {

        String content_type = contentType;
        if (content_type == null || "".equals(content_type)) {
            content_type = DEFAULT_CONTENT_TYPE_JSON;
        }

        String char_set = charSet;
        if (char_set == null || "".equals(char_set)) {
            char_set = DEFAULT_CHARSET_UTF8;
        }

        String url = action;
        logger.info("Post request url: {}", url);

        HttpPost httpPost = new HttpPost(url);

        //header参数
        if (headerParam != null && headerParam.size() > 0) {
                logger.info("Post request header: {}", JSON.toJSONString(headerParam));
            for (String key : headerParam.keySet()) {
                httpPost.addHeader(key, headerParam.get(key));
            }
        }

        //entity数据
        if (bodyParam != null) {
            //x-www-form-urlencoded类型
            if (ContentType.APPLICATION_FORM_URLENCODED.getMimeType().equals(contentType)) {
                if (bodyParam instanceof Map) {

                    @SuppressWarnings("unchecked")
                    Map<Object, Object> params = (Map<Object, Object>) bodyParam;
                    if (!CollectionUtils.isEmpty(params)) {
                        List<NameValuePair> pairs = new ArrayList<>();
                        for (Map.Entry<Object, Object> entry : params.entrySet()) {
                            NameValuePair pair = new BasicNameValuePair(String.valueOf(entry.getKey()),
                                    String.valueOf(entry.getValue()));
                            pairs.add(pair);
                        }
                        HttpEntity httpEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
                        httpPost.setEntity(httpEntity);
                        logger.info("post request body: {}", httpEntity.toString());
                    }
                }
            } else {//json或其他类型
                String params;
                if (bodyParam instanceof String) {
                    params = (String) bodyParam;
                } else {
                    params = JSONArray.toJSONString(bodyParam);
                }

                logger.info("Post request body: {}", params);

                StringEntity entity = new StringEntity(params, char_set);
                entity.setContentEncoding(char_set);
                entity.setContentType(content_type);

                httpPost.setEntity(entity);
            }
        }

        // 发送http 请求
        String resultStr = httpRequest(httpPost);

        logger.debug("Post response: {}", resultStr);
        return resultStr;
    }

    /**
     * get请求
     * 支持http和https
     *
     * @param action
     * @param headerParam
     * @param params
     * @return
     * @throws Exception
     */
    public static String httpGet(String action, Map<String, String> headerParam,
                                 Map<String, String> params) throws Exception {
        // 参数判定
        if (action == null || action.trim().length() == 0) {
            throw new RuntimeException("HttpProtocolHandler false: url is blank.");
        }

        // 参数处理
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(action);
        if (params != null) {
            for (String key : params.keySet()) {
                uriBuilder.setParameter(key, params.get(key));
            }
        }
        // HttpGet对象设置
        String url = uriBuilder.build().toString();
        HttpGet httpGet = new HttpGet(url);
        logger.info("Get请求地址：" + url);
        // header参数处理
        if (headerParam != null && headerParam.size() > 0) {
            for (String key : headerParam.keySet()) {
                httpGet.addHeader(key, headerParam.get(key));
            }
        }
        // 发送http 请求
        String result = httpRequest(httpGet);
        logger.info("Get请求返回：" + result);

        return result;
    }

    /**
     * 发送http 请求
     *
     * @param mode 请求方式,包含地址
     * @return
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    public static String httpRequest(HttpRequestBase mode) {
        String resultStr = "";
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = createSSLClientDefault(true);
        try {
            response = httpClient.execute(mode);
            HttpEntity entity = response.getEntity();
            resultStr = EntityUtils.toString(entity, "utf-8");
            //关闭资源 实际关闭inputStream
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                mode.releaseConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultStr;
    }

}
