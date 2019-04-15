package com.agg.common.utils;

public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    public static String changeTOLowerCase(String s) {
        if (isEmpty(s)) {
            return "";
        } else {
            StringBuilder stringBuffer = new StringBuilder();

            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                    stringBuffer.append(Character.toChars(s.charAt(i) + 32));
                } else {
                    stringBuffer.append(s.charAt(i));
                }
            }

            return stringBuffer.toString();
        }
    }

    /**
     * 剔除name前面的数字
     * 参考：https://www.cnblogs.com/heyuxiu/p/5972187.html
     *
     * @param name     名称
     * @param isDelNum 是否剔除数字
     * @return 剔除后的数字
     */
    public static String delFrontNum(String name, boolean isDelNum) {
        if (isDelNum) {
            for (int i = 0; i < name.length(); i++) {
                if (!Character.isDigit(name.charAt(i))) {
                    if (i == 0) return name;
                    else return name.substring(i, name.length());
                } else {
                    if (i == name.length() - 1) return name;
                }
            }
        }
        return name;
    }

}
