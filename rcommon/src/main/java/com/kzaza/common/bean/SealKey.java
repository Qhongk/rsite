package com.kzaza.common.bean;

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
