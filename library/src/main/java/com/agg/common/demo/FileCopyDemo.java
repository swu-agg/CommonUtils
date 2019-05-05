package com.agg.common.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyDemo {

    public static void main(String[] args) {
        File srcFile = new File("e:\\temp\\java\\agg.txt");
        File desFile = new File("e:\\temp\\java\\copy_agg.txt");
        if (srcFile.exists()) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(desFile);
                int len;
                byte[] buf = new byte[1024];
                if ((len = fis.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
