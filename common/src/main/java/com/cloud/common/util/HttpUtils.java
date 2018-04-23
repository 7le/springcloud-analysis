package com.cloud.common.util;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpUtils
 *
 * @author : 7le
 * @since v1.0.0
 */
public class HttpUtils {

    private final static Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

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
        for (String s : ipArray) {// 过滤掉局域网ip
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
        Map map = new HashMap();
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
