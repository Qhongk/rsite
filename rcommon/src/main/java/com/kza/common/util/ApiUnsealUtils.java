package com.kza.common.util;

import com.kza.common.bean.SealKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.SortedMap;

/**
 * @author kza
 */
public class ApiUnsealUtils {

	/**
	 * searchAPI
	 * @param key
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public static final String searchAPI(SealKey key, String url, SortedMap<String, String> param) throws Exception {
		url = urlFormat(url);

		param.put("apiKey", key.getApiKey());
		// Generate signature.
		String hash = ApiSignUtils.makeMd5Hash(url, param, key.getApiSecret());
		param.put("apiSign", hash);
		StringBuilder urlSb = new StringBuilder(url);
		for (String mkey : param.keySet()) {
			if (StringUtils.isNotBlank(param.get(mkey))) {
				urlSb.append(mkey).append("=").append(param.get(mkey).trim().replace("+", "%2B")).append("&");// 替换掉参数中的空格replace("+", "%2B")
			}
		}
		return urlSb.deleteCharAt(urlSb.length() - 1).toString();
	}


	private static final String urlFormat(String url) {
		Assert.hasText(url, "url must not be empty");

		int index = url.indexOf("?");
		if (index == -1) {
			return url + "?";
		}
		return url;
	}
}
