package com.kza.common.filter.gzip;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UnZipRequestWrapper extends HttpServletRequestWrapper {

    private UnZipServletInputStream compressServletInputStream;
    private byte[] data;

    public UnZipRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        int length = request.getContentLength();
        final ServletInputStream inputStream = request.getInputStream();

        data = toBytes(inputStream);
        compressServletInputStream = new UnZipServletInputStream(data, length);
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

}
