package com.kza.common.filter.gzip;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GZIPRequestWrapper extends HttpServletRequestWrapper {

    private ServletInputStream inStream = null;
    private BufferedReader reader = null;

    public GZIPRequestWrapper(HttpServletRequest req) throws IOException {
        super(req);
        this.inStream = new GZIPRequestStream(req);
        this.reader = new BufferedReader(new InputStreamReader(this.inStream));
    }

    /*
     *
     * @see javax.servlet.ServletRequestWrapper#getInputStream()
     */
    public ServletInputStream getInputStream() throws IOException {
        return this.inStream;
    }

    /*
     *
     * @see javax.servlet.ServletRequestWrapper#getReader()
     */
    public BufferedReader getReader() throws IOException {
        return this.reader;
    }

}
