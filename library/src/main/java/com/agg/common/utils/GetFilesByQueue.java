package com.agg.common.utils;

import com.agg.common.template.Queue;

import java.io.File;

/**
 * 遍历目录下的所有文件及其目录下的文件
 */
public class GetFilesByQueue {

    public static void main(String[] args) {
        File dir = new File("F:\\JAVASE\\day01\\day01");
        File[] listFiles = dir.listFiles();
        Queue<File> queue = new Queue<>();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                System.out.println("dir:" + file.getName());
                queue.add(file);
            } else {
                System.out.println("file:" + file.getName());
            }
        }

        while(!queue.isNull()){
            File subDir = queue.get();
            File[] subFiles = subDir.listFiles();
            for(File file : subFiles){
                if(file.isDirectory()){
                    System.out.println("dir:" + file.getName());
                    queue.add(file);
                }else{
                    System.out.println("file:" + file.getName());
                }
            }
        }
    }

}
