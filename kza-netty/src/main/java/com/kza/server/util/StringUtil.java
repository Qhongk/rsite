package com.kza.server.util;

/**
 * Created by kza on 2015/10/15.
 */
public class StringUtil {

    private static final String baseChar = "0123456789abcdef";

    public static String str2HexStr(String str) {
        char[] chars = baseChar.toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }


    /**
     * @param hex
     * @return byte[]
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) baseChar.indexOf(c);
        return b;
    }

    /**
     * @param bArray
     * @return HexString
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp);
        }
        return sb.toString();
    }


    /**
     * @param bArray
     * @return HexString
     */
    public static final String bytesToHexStringFormat(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (i > 0) {
                sb.append(" ");
            }
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp);
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转换字符串
     *
     * @param s
     * @return String
     */
    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static int toInt(byte[] bRefArr) {
        int iOutcome = 0;
        byte bLoop;
        for (int i = 0, len = bRefArr.length; i < len; i++) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << (8 * (len - 1 - i));
        }
        return iOutcome;
    }
}
