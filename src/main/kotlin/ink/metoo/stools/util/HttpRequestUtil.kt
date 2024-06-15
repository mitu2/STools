package ink.metoo.stools.util

import org.apache.commons.lang3.ArrayUtils
import javax.servlet.http.HttpServletRequest

/**
 *
 * @see run.halo.app.utils.ServletUtils
 */
object HttpRequestUtil {

    fun getClientIP(request: HttpServletRequest, vararg otherHeaderNames: String): String? {
        var headers = arrayOf(
            "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"
        )
        if (otherHeaderNames.isNotEmpty()) {
            headers = ArrayUtils.addAll(headers, *otherHeaderNames)
        }
        return getClientIPByHeader(request, *headers)
    }


    fun isUnknown(checkString: String?): Boolean {
        return checkString.isNullOrBlank() || "unknown".equals(checkString, ignoreCase = true)
    }

    fun getClientIPByHeader(request: HttpServletRequest, vararg headerNames: String?): String? {
        var ip: String?
        for (header in headerNames) {
            ip = request.getHeader(header)
            if (!isUnknown(ip)) {
                return getMultistageReverseProxyIp(ip)
            }
        }
        ip = request.remoteAddr
        return getMultistageReverseProxyIp(ip)
    }


    fun getMultistageReverseProxyIp(ip: String?): String? {
        // 多级反向代理检测
        var ip = ip
        if (ip != null && ip.indexOf(",") > 0) {
            val ips = ip.trim { it <= ' ' }
                .split(",".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
            for (subIp in ips) {
                if (!isUnknown(subIp)) {
                    ip = subIp
                    break
                }
            }
        }
        return ip
    }

}