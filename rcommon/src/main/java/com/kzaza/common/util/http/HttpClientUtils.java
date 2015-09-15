package com.kzaza.common.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * @Title: HttpClientUtils.java
 * @Description: httpclient tool
 *
 * @author kza
 * @date 2015年3月17日 下午3:43:30
 * @version V1.0
 */
public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 * http get request.
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static final String executeHttpGet(String urlStr) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = null;
		try {
			URL url = new URL(urlStr);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			HttpGet httpget = new HttpGet(uri);

			logger.debug("Executing request {}", httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			responseBody = httpclient.execute(httpget, responseHandler);
		} finally {
			httpclient.close();
		}
		return responseBody;
	}

	/**
	 * http post request.
	 * @param urlStr
	 * @param keyValueMap
	 * @return
	 * @throws Exception
	 */
	public static final String executeHttpPost(String urlStr, Map<String, String> keyValueMap) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = null;
		try {
			URL url = new URL(urlStr);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			HttpPost httppost = new HttpPost(uri);

			logger.debug("Executing request {}" + httppost.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			responseBody = httpclient.execute(httppost, responseHandler);
		} finally {
			httpclient.close();
		}
		return responseBody;
	}

}
