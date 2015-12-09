package com.kza.common.filter.gzip;

import com.google.common.net.HttpHeaders;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GzipFilter implements Filter {

    public static final String MD5_KEY = "gzip_md5_key";

    private String headerName = HttpHeaders.ACCEPT_ENCODING;

    private static final ThreadLocal<Map<String, Object>> privateLocal = new ThreadLocal<Map<String, Object>>() {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    /*
     *
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        String tmp = filterConfig.getInitParameter("headerName");
        tmp = StringUtils.trimToNull(tmp);
        if (null != tmp)
            headerName = tmp;
    }

    /*
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if ((request instanceof HttpServletRequest)) {
            HttpServletRequest req = (HttpServletRequest) request;
            String accptEncoding = req.getHeader(headerName);
            if ((accptEncoding != null) && (accptEncoding.toLowerCase().indexOf("gzip") > -1)) {

                UnZipRequestWrapper gzipReq = new UnZipRequestWrapper((HttpServletRequest) request);
                String md5 = Md5Crypt.md5Crypt(gzipReq.getData());
                if (null != md5) {
                    privateLocal.get().put(MD5_KEY, md5);
                } else {
                    privateLocal.get().remove(MD5_KEY);
                }
                request = gzipReq;
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            privateLocal.get().clear();
            privateLocal.remove();
        }
    }

    /*
     *
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

}
