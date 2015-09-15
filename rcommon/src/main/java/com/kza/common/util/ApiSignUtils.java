package com.kza.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 */
public abstract class ApiSignUtils {

	public static String getBaseUrl(String url) {
		Assert.hasText(url, "url must not be empty");

		int index = url.indexOf("?");
		if (index == -1 || url.endsWith("?")) {
			return url;
		}

		return url.substring(0, index);
	}

	/**
	 * Transform url parameters to key value map.
	 * 
	 * @param url
	 * @return
	 */
	private static SortedMap<String, String> toParamMap(String url) {
		Assert.hasText(url, "url must not be empty");

		int index = url.indexOf("?");
		if (index == -1 || url.endsWith("?")) {
			return null;
		}

		String query = url.substring(index + 1);
		if (StringUtils.isBlank(query)) {
			return null;
		}

		String[] tokens = query.split("&");
		if (tokens == null || tokens.length == 0) {
			return null;
		}

		SortedMap<String, String> paramMap = new TreeMap<String, String>();
		for (String token : tokens) {
			int eIndex = token.indexOf("=");
			if (eIndex == -1) {
				continue;
			}

			String name = token.substring(0, eIndex);
			String value = token.substring(eIndex + 1);
			try {
				String decodedValue = URLDecoder.decode(value, "UTF-8");
				paramMap.put(name, decodedValue);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		return paramMap;
	}
	
	/**
	 * Merge the parameters in URL query string and the input paramMap together into a
	 * single sorted parameter map.
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static SortedMap<String, String> mergeParamMap(String url, SortedMap<String, String> paramMap) {
		SortedMap<String, String> urlParamMap = toParamMap(url);
		if (urlParamMap == null || urlParamMap.size() == 0) {
			return paramMap;
		}

		if (paramMap == null || paramMap.size() == 0) {
			return urlParamMap;
		}

		SortedMap<String, String> resultMap = new TreeMap<>(urlParamMap);
		resultMap.putAll(paramMap);
		return resultMap;
	}
	
	public static String makeMd5Hash(String url, SortedMap<String, String> paramMap, String secret) {
		SortedMap<String, String> mergedMap = mergeParamMap(url, paramMap);
		return SignatureUtils.makeMd5Hash(mergedMap, secret);
	}
	
	public static String makeSha1Hash(String url, SortedMap<String, String> paramMap, String secret) {
		SortedMap<String, String> mergedMap = mergeParamMap(url, paramMap);
		return SignatureUtils.makeSha1Hash(mergedMap, secret);
	}
}
