package com.agg.common.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCharCopyDemo {

    public static void main(String[] args) {
        File file = new File("e:\\temp\\java\\agg.txt");
        if (!file.exists()) {
            System.out.print("file not exists!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("e:\\temp\\java\\agg.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("e:\\temp\\java\\copy_agg.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
