package cn.zora.base.util;

import cn.zora.base.config.KeyConfig;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.base.util</h4>
 * <p>ip</p>
 *
 * @author zora
 * @since 2020.04.27
 */
public class IpUtils {
    public static boolean isKeyRight(String key, HttpServletRequest request) {
        if (!KeyConfig.SECRET_KEY.equals(key)) {
            String ip = IpUtils.getIpAddress(request);
            System.out.println(LocalDate.now().toString() + "\t[ERROR]\t来自[ " + ip + " ]的请求包含了错误的密钥信息：" + key);
            return false;
        } else {
            return true;
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException ignore) {
                    ip = "Unknown Ip Address";
                }
            }
        }
        return ip;
    }
}
