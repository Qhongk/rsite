package com.kzaza.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.util.Assert;

import java.util.SortedMap;

/**
 *
 */
public class SignatureUtils {

    /**
     *
     * @param paramMap a sorted map is used to ensure the returned signature is consistent between executions
     * @param secret
     * @return
     */
    public static String makeMd5Hash(SortedMap<String, String> paramMap, String secret) {
        Assert.notEmpty(paramMap, "paramMap must not be empty");
        Assert.hasText(secret, "secret must not be empty");

        StringBuilder sb = new StringBuilder(64);
        sb.append(secret);
        for (String key : paramMap.keySet()) {
            if (StringUtils.isNotBlank(paramMap.get(key))) {
                sb.append(paramMap.get(key).trim());
            }
        }

        return new Md5Hash(sb.toString()).toHex();
    }

    public static String makeSha1Hash(SortedMap<String, String> paramMap, String apiSecret, String tokenSecret) {
        return makeSha1Hash(paramMap, getSecret(apiSecret, tokenSecret));
    }

    /**
     *
     * @param paramMap
     * @param secret
     * @return
     */
    public static String makeSha1Hash(SortedMap<String, String> paramMap, String secret) {
        Assert.notEmpty(paramMap, "paramMap must not be empty");
        Assert.hasText(secret, "secret must not be empty");

        StringBuilder sb = new StringBuilder(64);
        for (String value : paramMap.values()) {
            sb.append(value);
        }

        return new Sha1Hash(sb.toString(), secret).toHex();
    }


    /**
     * Concatenate two secrets into a single secret.
     *
     * @param apiSecret
     * @param tokenSecret
     * @return
     */
    public static String getSecret(String apiSecret, String tokenSecret) {
        Assert.hasText(apiSecret, "apiSecret must not be empty");

        if (StringUtils.isBlank(tokenSecret)) {
            return apiSecret;
        }

        StringBuilder sb = new StringBuilder(64);
        sb.append(apiSecret);
        sb.append("&");
        sb.append(tokenSecret);
        return sb.toString();
    }
}
