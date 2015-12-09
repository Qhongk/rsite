package com.kza.common.filter.gzip;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZIPRequestStream extends ServletInputStream {

    private ServletInputStream inStream = null;
    private GZIPInputStream in = null;

    public GZIPRequestStream(HttpServletRequest request) throws IOException {
        this.inStream = request.getInputStream();
        this.in = new GZIPInputStream(this.inStream);
    }

    /*
     *
     * @see java.io.InputStream#read()
     */
    public int read() throws IOException {
        return this.in.read();
    }

    /*
     *
     * @see java.io.InputStream#read(byte[])
     */
    public int read(byte[] b) throws IOException {
        return this.in.read(b);
    }

    /*
     *
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public int read(byte[] b, int off, int len) throws IOException {
        return this.in.read(b, off, len);
    }

    /*
     *
     * @see java.io.InputStream#close()
     */
    public void close() throws IOException {
        this.in.close();
    }

}
