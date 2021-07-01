package com.cither.reptile.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author raincither
 * @date 2021/1/11 17:22
 */
@Slf4j
public class HttpClientUtil {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";

    /**
     * httpclient基础配置信息
     */
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            // 设置连接超时时间(单位毫秒)
            .setConnectTimeout(2000)
            // 设置请求超时时间(单位毫秒)
            .setConnectionRequestTimeout(2000)
            // socket读写超时时间(单位毫秒)
            .setSocketTimeout(1000)
            // 设置是否允许重定向(默认为true)
            .setRedirectsEnabled(true)
            //是否启用内容压缩，默认true
            .setContentCompressionEnabled(true)
            .build();

    public static CookieStore httpCookieStore = new BasicCookieStore();

    /**
     * 获得Http客户端
     */
    private static final CloseableHttpClient HTTP_CLIENT = HttpClientBuilder.create()
            //失败重试，默认3次
            .setRetryHandler(new DefaultHttpRequestRetryHandler())
            .setDefaultCookieStore(httpCookieStore)
            .build();


    /**
     * @desc get请求
     * @param url url
     * @return org.jsoup.nodes.Document
     */
    public static Document getParse(String url){
        return Jsoup.parse(get(url));
    }
    /**
     * @desc get请求
     * @param url url
     * @return org.jsoup.nodes.Document
     */
    public static Document getParse(String url, Map<String, Object> params){
        return Jsoup.parse(get(url, params));
    }


    /**
     * @desc get请求
     * @param url url
     * @return String
     */
    public static String get(String url) {
        return get(url, new HashMap<>(0));
    }

    /**
     * @desc get请求
     * @param url, params
     * @return String
     * @author mal
     */
    public static String get(String url, Map<String, Object> params) {
        HttpGet httpGet = null;

        //添加参数
        List<NameValuePair> list = new ArrayList<>();
        for (String key : params.keySet()) {
            list.add(new BasicNameValuePair(key, params.get(key).toString()));
        }

        // 由客户端执行(发送)Get请求
        try {
            URI uri = new URIBuilder(url).addParameters(list).build();
            // 创建Get请求
            httpGet = new HttpGet(uri);


        } catch (Exception e) {
            log.error("Exception responseResult：{}", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return execute(httpGet);
    }


    /**
     * @desc post请求
     * @param url url
     * @return String
     */
    public static String post(String url) {
        return post(url, new HashMap<>(0));
    }
    /**
     * @desc post请求
     * @param url, params
     * @return String
     */
    public static String post(String url, Map<String, Object> params) {
        HttpPost httpPost = null;

        //添加参数
        List<NameValuePair> list = new ArrayList<>();
        for (String key : params.keySet()) {
            list.add(new BasicNameValuePair(key, params.get(key).toString()));
        }

        try {
            URI uri = new URIBuilder(url).addParameters(list).build();
            httpPost = new HttpPost(uri);
        } catch (Exception e) {
            log.error("Exception responseResult：{}", e.getLocalizedMessage());
            e.printStackTrace();
        }

        return execute(httpPost);
    }

    /**
     * @desc String请求
     * @param httpRequestBase base
     * @return String
     */
    private static String execute(HttpRequestBase httpRequestBase) {
        if (httpRequestBase == null) {
            return null;
        }

        log.info("请求地址: {},请求类型: {},请求参数: {}",
                httpRequestBase.getURI().toString(),
                httpRequestBase.getMethod(),
                httpRequestBase.getURI().getQuery());

        String responseResult = null;
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 将上面的配置信息 运用到这个Get请求里
            httpRequestBase.setConfig(REQUEST_CONFIG);
            // 设置请求头 User-Agent
            httpRequestBase.setHeader("User-Agent", USER_AGENT);
            //请求发起的时间
            long t1 = System.nanoTime();
            response = HTTP_CLIENT.execute(httpRequestBase);
            //收到响应的时间
            long t2 = System.nanoTime();
            log.info("响应状态:{} 执行时间: {} ns",response.getStatusLine(),(t2 - t1));

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                responseResult = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

            }


        } catch (Exception e) {
            log.error("Exception responseResult：{}", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("Exception responseResult：{}", e.getLocalizedMessage());
                e.printStackTrace();
            }

        }
        return responseResult;

    }

}
