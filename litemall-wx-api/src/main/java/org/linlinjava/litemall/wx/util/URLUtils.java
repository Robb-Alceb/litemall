package org.linlinjava.litemall.wx.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：stephen
 * @date ：Created in 11/19/2019 2:20 PM
 * @description：TODO
 */

public class URLUtils {
    public static String getBaseURl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuffer url =  new StringBuffer();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if(url.toString().endsWith("/")){
            url.append("/");
        }
        return url.toString();
    }

}
