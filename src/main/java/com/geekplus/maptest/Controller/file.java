package com.geekplus.maptest.Controller;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class file {

    public static void main(String[] args) throws IOException {

       /* File file1= new File("123.txt");
        String d1="";
        File file2= new File("D:","123.txt");

        file2.createNewFile();
        URL classLoadre = file.class.getResource("/");
        System.out.println(classLoadre.getPath());
        System.out.println(classLoadre);
        System.out.println(classLoadre.getFile());*/
        File file= new File("123.txt");
        String profilepath = file.class.getResource("/").getPath()+"start.sh";//我的配置文件在src根目录下
        System.out.println(profilepath);
        try {
            Properties props=new Properties();
            props.load(new FileInputStream(profilepath));
            OutputStream fos = new FileOutputStream(profilepath);

          if (props.containsKey("server.uiserverport")) {
              props.setProperty("server.uiserverport", "123");
          }
            System.out.println(props.getProperty("server.uiserverport"));
            props.store(fos, "Update value");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("属性文件更新错误");
        }



    }
}
