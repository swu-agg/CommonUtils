package com.agg.common.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FISDemo {

    public static void main(String[] args) {
        File file = new File("e:\\temp\\java\\agg.txt");
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                int len;
                byte[] buf = new byte[2];
                while ((len = fis.read(buf)) != -1) {
                    System.out.println(len + ":" + new String(buf, 0, len));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
