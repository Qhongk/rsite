package com.kzaza.common.util.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

public class FileUploadCommitUtils {

    private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

    private static final SecretKeySpec sigKey = new SecretKeySpec("XXES(S(*$&(*&#$(($%&(*%$(".getBytes(CHARSET_UTF_8),
            "HmacSHA1");

    private static final Logger logger = LoggerFactory.getLogger(FileUploadCommitUtils.class);

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static byte[] download(String url) throws IOException {
        return IOUtils.toByteArray(new URL(url));
    }

    private static byte[] getStringBytes(String format, Object... values) {
        return String.format(format, values).getBytes(CHARSET_UTF_8);
    }

    private static String getSigString(String sigBuf) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(sigKey);
        byte[] data = mac.doFinal(sigBuf.getBytes(CHARSET_UTF_8));
        return Base64.encodeBase64String(data);
    }

    @SuppressWarnings("resource")
    public static FileUploadResult upload(String url, String name, boolean isOverwrite, InputStream input) {
        HttpURLConnection conn = null;
        ByteArrayInputStream bais = null;
        try {
            long ts = System.currentTimeMillis() / 1000;
            String nonce = String.valueOf(new Random().nextInt());
            String overwrite = isOverwrite ? "yes" : "no";
            String sigbuf = String.format("name=%s&nonce=%s&overwrite=%s&ts=%d", name, nonce, overwrite, ts);
            String sig = getSigString(sigbuf);

            String boundary = "----UPLOADBOUNDARY" + new Random().nextLong();
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(8000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Encoding", "gzip");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();

            output.write(getStringBytes("--%s\r\n", boundary));
            output.write(getStringBytes("Content-Disposition: form-data; name=\"name\"\r\n\r\n"));
            output.write(getStringBytes("%s\r\n", name));

            output.write(getStringBytes("--%s\r\n", boundary));
            output.write(getStringBytes("Content-Disposition: form-data; name=\"nonce\"\r\n\r\n"));
            output.write(getStringBytes("%s\r\n", nonce));

            output.write(getStringBytes("--%s\r\n", boundary));
            output.write(getStringBytes("Content-Disposition: form-data; name=\"ts\"\r\n\r\n"));
            output.write(getStringBytes("%d\r\n", ts));

            output.write(getStringBytes("--%s\r\n", boundary));
            output.write(getStringBytes("Content-Disposition: form-data; name=\"sig\"\r\n\r\n"));
            output.write(getStringBytes("%s\r\n", sig));

            output.write(getStringBytes("--%s\r\n", boundary));
            output.write(getStringBytes("Content-Disposition: form-data; name=\"overwrite\"\r\n\r\n"));
            output.write(getStringBytes("%s\r\n", overwrite));

            output.write(getStringBytes("--%s\r\n", boundary));
            output.write(getStringBytes("Content-Disposition: form-data; name=\"file\"; filename=\"filename\"\r\n"));
            output.write(getStringBytes("Content-Type: application/octect-stream\r\n\r\n"));

            ByteArrayOutputStream fileOutput = new ByteArrayOutputStream();
            GZIPOutputStream gzipFileOutput = new GZIPOutputStream(fileOutput);
            int len = 0;
            try {
                len = IOUtils.copy(input, gzipFileOutput);
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(gzipFileOutput);
            } catch (Exception e) {
                logger.error("message : {}", e);
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(gzipFileOutput);
            }
            bais = new ByteArrayInputStream(fileOutput.toByteArray());
            int len1 = IOUtils.copy(bais, conn.getOutputStream());
            logger.debug("file {} stream zip size: {}, original size {}", name, len1, len);
            output.write(getStringBytes("\r\n--%s--", boundary));
            conn.getOutputStream().flush();
            int code = conn.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                FileUploadResult result = new FileUploadResult();
                result.setCode(-1);
                result.setMsg("http status: " + conn.getResponseCode());
                return result;
            }
            FileUploadResult msg = jsonMapper.readValue(conn.getInputStream(), FileUploadResult.class);
            logger.debug("file upload response msg: {}, {}", msg.getMsg(), msg.getUrl());
            return msg;
        } catch (Exception e) {
            logger.error("", e);
            FileUploadResult result = new FileUploadResult();
            result.setCode(-2);
            result.setMsg(e.getMessage());
            return result;
        }  finally{
            if(conn != null){
                conn.disconnect();
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class FileUploadResult {

        private int code;

        private String msg;

        private String url;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
