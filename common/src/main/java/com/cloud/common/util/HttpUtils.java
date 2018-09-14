package com.cloud.common.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * HttpUtils
 *
 * @author heqian7
 */
@Slf4j
public class HttpUtils {

    private final static Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
    private static final int MAX_TIMEOUT = 100000;
    private static final int SUCCESS = 200;
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static CookieStore cookieStore;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        requestConfig = RequestConfig.custom()
                // 设置连接超时
                .setConnectTimeout(MAX_TIMEOUT)
                // 设置读取超时
                .setSocketTimeout(MAX_TIMEOUT)
                // 设置从连接池获取连接实例的超时
                .setConnectionRequestTimeout(MAX_TIMEOUT).build();
        cookieStore = new BasicCookieStore();
    }

    private static CloseableHttpClient getHttpClient() {
        //NoopHostnameVerifier.INSTANCE该主机名验证器本质上会关闭主机名验证。它接受任何有效的和符合目标主机的SSL会话
        return HttpClientBuilder.create().setMaxConnTotal(200).setMaxConnPerRoute(100)
                .setSSLHostnameVerifier((String host, final SSLSession session) -> true)
                .setSSLSocketFactory(new SSLConnectionSocketFactory(Objects.requireNonNull(createSSLSocketFactory()),
                        NoopHostnameVerifier.INSTANCE))
                .build();
    }

    private static CloseableHttpClient getHttpClient(ClientCookie[] cookies) {
        // 携带cookie进行HTTP访问
        if (cookies != null && cookies.length != 0) {
            for (ClientCookie cookie : cookies) {
                cookieStore.addCookie(cookie);
            }
        }
        return HttpClientBuilder.create().setMaxConnTotal(200).setMaxConnPerRoute(100)
                .setSSLHostnameVerifier((String host, final SSLSession session) -> true)
                .setSSLSocketFactory(new SSLConnectionSocketFactory(Objects.requireNonNull(createSSLSocketFactory()),
                        NoopHostnameVerifier.INSTANCE))
                .setDefaultCookieStore(cookieStore).build();
    }

    private static SSLContext createSSLSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            return sc;
        } catch (Exception e) {
            log.warn("Exception:{}", e);
        }
        return null;
    }

    /**
     * 不带参数的get请求
     */
    public static String get(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try (CloseableHttpClient httpClient = getHttpClient();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        return null;
    }

    /**
     * 带参数的get请求
     */
    public static String get(String url, Map<String, String> params) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return get(uriBuilder.build().toString());
    }

    /**
     * 不带参数的get请求(带cookies)
     */
    public static String get(String url, ClientCookie[] cookies) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try (CloseableHttpClient httpClient = getHttpClient(cookies);
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        return null;
    }

    /**
     * 带参数的get请求(带cookies)
     */
    public static String get(String url, Map<String, String> params, ClientCookie[] cookies) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return get(uriBuilder.build().toString(), cookies);
    }

    /**
     * 带参数的post请求
     */
    public static String post(String url, Map<String, String> params) throws Exception {
        log.debug("request url: {}", url);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            httpPost.setEntity(formEntity);
        }
        try (CloseableHttpClient httpClient = getHttpClient();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        return null;
    }

    /**
     * 带参数的post请求(带cookies)
     */
    public static String post(String url, Map<String, String> params, ClientCookie[] cookies) throws Exception {
        log.debug("request url: {}", url);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setEntity(formEntity);
        }
        try (CloseableHttpClient httpClient = getHttpClient(cookies);
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        return null;
    }

    /**
     * 带参数(MultiValueMap)的post请求(带cookies)
     */
    public static String postMulti(String url, MultiValueMap<String, String> params, ClientCookie[] cookies)
            throws Exception {
        log.debug("request url: {}", url);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<>(0);
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                List<String> values = params.get(key);
                for (String value : values) {
                    parameters.add(new BasicNameValuePair(key, value));
                }
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            httpPost.setEntity(formEntity);
        }
        try (CloseableHttpClient httpClient = getHttpClient(cookies);
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == SUCCESS) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
        return null;
    }

    /**
     * 不带参数的post请求(带cookies)
     */
    public static String post(String url, ClientCookie[] cookies) throws Exception {
        return post(url, null, cookies);
    }

    /**
     * 本机获取ip地址
     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取客户端的真实ip
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (LOG.isDebugEnabled()) {
            LOG.debug("x-forwarded-for = {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (LOG.isDebugEnabled()) {
                LOG.debug("Proxy-Client-IP = {}", ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (LOG.isDebugEnabled()) {
                LOG.debug("WL-Proxy-Client-IP = {}", ip);
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (LOG.isDebugEnabled()) {
                LOG.debug("RemoteAddr-IP = {}", ip);
            }
        }
        if (!Strings.isNullOrEmpty(ip)) {
            ip = ip.split(":")[0];
        }
        String[] ipArray = ip.split(",");
        // 过滤掉局域网ip
        for (String s : ipArray) {
            s = s.trim();
            if (!(s.startsWith("10.") || s.startsWith("192."))) {
                return s;
            }
        }
        if (!Strings.isNullOrEmpty(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static Map getParams(HttpServletRequest request) {
        Map map = new HashMap(16);
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }
}
