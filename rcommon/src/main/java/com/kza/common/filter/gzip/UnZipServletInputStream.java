package com.kza.common.filter.gzip;

import javax.servlet.ServletInputStream;
import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * ServletInputStream wrapper
 * unZip data
 */
public class UnZipServletInputStream extends ServletInputStream {

    private ByteArrayInputStream input;
    private BufferedReader reader;
    private byte[] data;

    public UnZipServletInputStream(byte[] source, int length) throws IOException {
        InputStream is = new ByteArrayInputStream(source);
        GZIPInputStream gis = new GZIPInputStream(is);

        data = toBytes(gis);
        input = new ByteArrayInputStream(data);
    }

    /*
     *
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException {
        return input.read();
    }

    /*
     *
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
    public int read(byte[] buf, int off, int len) {
        return input.read(buf, off, len);
    }

    /*
     *
     * @see java.io.InputStream#read(byte[])
     */
    @Override
    public int read(byte[] buf) throws IOException {
        return input.read(buf);
    }

    /*
     *
     * @see java.io.InputStream#available()
     */
    @Override
    public int available() {
        return input.available();
    }

    public byte[] getData() {
        return data;
    }

    public int getContentLength() {
        return data.length;
    }

    public BufferedReader getReader() throws IOException {
        if (null == reader)
            reader = new BufferedReader(new InputStreamReader(input));
        return reader;
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
