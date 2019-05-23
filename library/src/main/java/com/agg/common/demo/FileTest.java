package com.agg.common.demo;

import java.text.DateFormat;
import java.util.Date;

/**
 * <pre>
 *     author    : Agg
 *     blog      : https://blog.csdn.net/Agg_bin
 *     time      : 2019/05/08
 *     desc      :
 *     reference :
 *     remark    :
 * </pre>
 */
public class FileTest {

    public static void main(String[] args){
        transTime(1540192619000L);
    }

    private static void transTime(long time) {
        Date data = new Date(time);
        String format1 = DateFormat.getDateInstance().format(data);
        System.out.println("transTime1: "+format1);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String format = sdf.format(data);
//        System.out.println("transTime: "+format);
    }

}
