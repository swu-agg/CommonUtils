package com.agg.common.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FOSDemo {
    private static String LINE = System.getProperty("line.separator");

    public static void main(String[] args) {
        File dir = new File("e:\\temp\\java");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "agg.txt");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            fos.write((LINE + "java").getBytes());
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
        }

    }

}
