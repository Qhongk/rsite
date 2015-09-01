package com.kzaza.common.util;

import junit.framework.TestCase;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by rick01.kong on 2015/8/24.
 */
public class SignatureUtilsTest extends TestCase {

    public void testMakeMd5Hash() {

        String apiSecretTemp = "245018f783cbf861ace4a8e10f98f93a";
        SortedMap<String,String> map = new TreeMap<String,String>();
        map.put("aa", "ae48d1184376d28b66df3454b4f5b07eGDBcreatePaySn141009015930131000106quickpayAndroid12485894");
        String signTemp = SignatureUtils.makeMd5Hash(map, apiSecretTemp);
        System.out.println("signTemp:=" + signTemp);
    }
}
