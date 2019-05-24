/*
package com.geekplus.maptest.Componet;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;

public class LogReader  {
    private File logFile = null;
    private long lastTimeFileSize = 0; // 上次文件大小
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public LogReader(File logFile) {
        this.logFile = logFile;
        lastTimeFileSize = logFile.length();
    }

    */
/**
     * 实时输出日志信息
     *//*

    public String gettext() {

            try {
                long len = logFile.length();
                String tmp = null;
                if (len < lastTimeFileSize) {
                    System.out.println("Log file was reset. Restarting logging from start of file.");
                    lastTimeFileSize = len;
                } else if(len > lastTimeFileSize) {
                    RandomAccessFile randomFile = new RandomAccessFile(logFile,"r");
                    randomFile.seek(lastTimeFileSize);

                    while ((tmp = randomFile.readLine()) != null) {
                        return tmp;
                     }
                    lastTimeFileSize = randomFile.length();
                    randomFile.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return tmp;

    }

}*/
