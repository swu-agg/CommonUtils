package com.agg.common.utils;

import java.text.DecimalFormat;

public class ConvertUtils {
    static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String bytes2HexString(byte[] bytes) {
        char[] ret = new char[bytes.length << 1];
        int i = 0;

        for (int var3 = 0; i < bytes.length; ++i) {
            ret[var3++] = hexDigits[bytes[i] >>> 4 & 15];
            ret[var3++] = hexDigits[bytes[i] & 15];
        }

        return new String(ret);
    }

    /**
     * 将浮点数转为百分比
     *
     * @param value 例如0.52
     * @return 例如52%
     */
    public static String float2String(float value) {
        return new DecimalFormat("0%").format(value);
    }

    /**
     * 将浮点数转为百分比，带小数点
     *
     * @param value 例如0.5234
     * @return 例如52.34%
     */
    public static String float2String(float value, int dot) {
        String pattern = "0%";
        if (dot == 1) {
            pattern = "0.0%";
        } else if (dot == 2) {
            pattern = "0.00%";
        } else if (dot == 3) {
            pattern = "0.000%";
        } else if (dot == 4) {
            pattern = "0.0000%";
        }
        return new DecimalFormat(pattern).format(value);
    }

}
