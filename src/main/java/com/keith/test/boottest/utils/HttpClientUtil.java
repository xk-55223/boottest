package com.keith.test.boottest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keith.test.boottest.dto.TaokeProxyDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
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
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mick on 2016/10/27.
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static CookieStore sslcookies = new BasicCookieStore();
    private static CookieStore cookies = new BasicCookieStore();

    private static final String DEFAULT_CHARSET_UTF8 = "UTF-8";

    private static final String DEFAULT_CONTENT_TYPE_JSON = "application/json";

    public static final String EMPTY_STR = "";

    public static List<TaokeProxyDTO> proxyList = new ArrayList<>();

    /*static {
        String proxyResult = null;
        try {
            proxyResult = HttpClientUtil.httpGet("http://api.web.21ds.cn/platform/getProxyIp?apkey=f2c87527-f931-18aa-0d88-53b56f814ae6&nums=10", null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject proxyJson = JSON.parseObject(proxyResult);
        JSONArray proxyList = proxyJson.getJSONArray("data");
        List<TaokeProxyDTO> proxyDTOList = new ArrayList<>();
        for (Object proxyObject : proxyList) {
            TaokeProxyDTO proxyDTO = JSON.parseObject(proxyObject.toString(), TaokeProxyDTO.class);
            proxyDTOList.add(proxyDTO);
        }
    }*/

    public static CloseableHttpClient createSSLClientDefault(boolean isSSL) {
        if (isSSL) {
            SSLContext sslContext = null;
            try {
                sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    //信任所有
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


    public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("参数设置错误");
            return EMPTY_STR;
        }
        return getResult(httpPost);
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }

        return pairs;
    }

    public static String httpGetRequest(String getUrl, Map<String, Object> headers, Map<String, Object> params) {
        HttpGet httpGet = new HttpGet(getUrl);

        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
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
        HttpGet httpGet = buildHttpGet(action, headerParam, params);
        // 发送http 请求
        String result = httpRequest(httpGet);
        logger.info("Get请求返回：" + result);

        return result;
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
    public static String httpGetProxy(String action, Map<String, String> headerParam,
                                 Map<String, String> params) throws Exception {
        HttpGet httpGet = buildHttpGet(action, headerParam, params);
        // 发送http 请求
        String result = httpRequestProxy(httpGet);
        logger.info("Get请求返回：" + result);

        return result;
    }

    private static HttpGet buildHttpGet(String action, Map<String, String> headerParam, Map<String, String> params) throws URISyntaxException {
        // 参数判定
        if (action == null || action.trim().length() == 0) {
            throw new RuntimeException("HttpProtocolHandler false: url is blank.");
        }

        URI uri = new URI(action);
        // 参数处理
        URIBuilder uriBuilder = new URIBuilder(uri);
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
        return httpGet;
    }

    /**
     * 发送http 请求
     *
     * @param mode 请求方式,包含地址
     * @return
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    public static String httpRequestProxy(HttpRequestBase mode) {
        TaokeProxyDTO proxyDTO = proxyList.get((int) (Math.random() * proxyList.size()));
        logger.info("本次http代理ip为：{}，端口为：{}", proxyDTO.getIp(), proxyDTO.getPort());
        HttpHost proxy = new HttpHost(proxyDTO.getIp(), proxyDTO.getPort());
        RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT).setProxy(proxy).build();
        mode.setConfig(requestConfig);
        return httpRequest(mode);
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
            HttpHost proxy = new HttpHost("161.35.5.51", 3128);
            RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT).setProxy(proxy).build();
            mode.setConfig(requestConfig);
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

    /**
     * 处理Http请求
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {
        CloseableHttpClient httpClient = createSSLClientDefault(true);
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                response.close();
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return EMPTY_STR;
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

    public static CookieStore getSSLCookieStore() {
        return sslcookies;
    }

    public static CookieStore getCookieStore() {
        return cookies;
    }
}
