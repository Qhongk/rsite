package com.kza.common.filter.gzip;

import com.google.common.collect.Iterators;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class UnZipRequestWrapper extends HttpServletRequestWrapper {

    private static final String PARAM_SEPARATOR = "&";
    private static final String KEY_SEPARATOR = "=";
    private static final String ENC = "UTF-8";

    private UnZipServletInputStream compressServletInputStream;
    private byte[] data;
    private Map<String, String> parameter = new HashMap<>();
    private Enumeration<String> paramNames;

    public UnZipRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        int length = request.getContentLength();
        ServletInputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            data = toBytes(inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        compressServletInputStream = new UnZipServletInputStream(data, length);
        if (data != null && data.length > 0) {
            String[] params = new String(compressServletInputStream.getData()).split(PARAM_SEPARATOR);
            for (String p : params) {
                String[] ps = p.split(KEY_SEPARATOR);
                if (null != ps && ps.length == 2) {
                    parameter.put(ps[0], URLDecoder.decode(ps[1], ENC));
                }
            }
        }
        paramNames = Iterators.asEnumeration(parameter.keySet().iterator());
    }

    /*
     *
     * @see javax.servlet.ServletRequestWrapper#getContentLength()
     */
    @Override
    public int getContentLength() {
        return compressServletInputStream.getContentLength();
    }

    /*
     *
     * @see javax.servlet.ServletRequestWrapper#getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return compressServletInputStream;
    }

    /*
     *
     * @see javax.servlet.ServletRequestWrapper#getReader()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return compressServletInputStream.getReader();
    }

    public byte[] getData() {
        return data;
    }

    private byte[] toBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int read;
        byte[] buffer = new byte[2048];
        while (-1 != (read = is.read(buffer)))
            bos.write(buffer, 0, read);

        return bos.toByteArray();
    }

    @Override
    public String getParameter(String name) {
        String s = super.getParameter(name);
        if (null != s) {
            return s;
        }
        return parameter.get(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return paramNames;
    }
}
