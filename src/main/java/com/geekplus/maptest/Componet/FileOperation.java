package com.geekplus.maptest.Componet;

import com.geekplus.maptest.Common.PropertiesUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.text.normalizer.Replaceable;

import java.io.*;
@Component
public class FileOperation {

@Autowired
  public   PropertiesUtill propertiesUtil;

    public String replacefile(MultipartFile file,String filePath){


        if (!file.isEmpty()) {
            try {

                /*     * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到
                 * d:/files大家是否能实现呢？ 等等;
                 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；
                 * 3、文件格式; 4、文件大小的限制;*/
                File file1 = new File(filePath+file.getOriginalFilename());

                //BufferedOutputStream 写入file.getOriginalFilename()文件里，写文件
                BufferedOutputStream out = new BufferedOutputStream(
//                        new FileOutputStream(new File("/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/config/system/"+file.getOriginalFilename())));
                        new FileOutputStream(file1));


//                        new FileOutputStream(new File("C:\\Users\\lixinpeng"+file.getOriginalFilename())));
                System.out.println(file.getName());
                //file.getBytes(),返回文件内容的二进制数组数据byte[],BufferedOutputStream.write（byte[] b）写入将b.length个二进制数据写入文件流
                out.write(file.getBytes());

                out.flush();
                out.close();
//               String fileString= out.toString();
return "success";

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            /*new File(file.getOriginalFilename());*/


        } else {
            return "上传失败，因为文件是空的.";
        }
    }

    public void replaceProperties(MultipartFile file,String filePath){

        propertiesUtil.filepath="/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/config/system/sysconfig.properties";





    }

}
