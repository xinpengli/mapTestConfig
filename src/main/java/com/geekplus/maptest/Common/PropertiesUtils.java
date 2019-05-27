package com.geekplus.maptest.Common;

import com.geekplus.maptest.Controller.file;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
public class PropertiesUtils {




    public  void replacePro(String filepath,
                            String key, String value) throws IOException {




        try {
            Properties props=new Properties();
            props.load(new FileInputStream(filepath));
            OutputStream fos = new FileOutputStream(filepath);

            if (props.containsKey(key)) {
                props.setProperty(key,value);
            }

            props.store(fos, "Update value");
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("属性文件更新错误");
        }



    }

}
