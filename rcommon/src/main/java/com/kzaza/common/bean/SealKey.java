package com.kzaza.common.bean;

/**
 * @author rick01.kong
 * @date 2015年3月17日 下午6:29:36
 * @version V1.0
 */
public class SealKey {
	private String apiKey;
	private String apiSecret;

	public SealKey() {
	}

	public SealKey(String apiKey, String apiSecret) {
		super();
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
}
