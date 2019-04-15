package com.agg.common.utils;

public class ConvertUtils {
    static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String bytes2HexString(byte[] bytes) {
        char[] ret = new char[bytes.length << 1];
        int i = 0;

        for(int var3 = 0; i < bytes.length; ++i) {
            ret[var3++] = hexDigits[bytes[i] >>> 4 & 15];
            ret[var3++] = hexDigits[bytes[i] & 15];
        }

        return new String(ret);
    }

}
