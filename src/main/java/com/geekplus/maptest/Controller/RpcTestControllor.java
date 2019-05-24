package com.geekplus.maptest.Controller;



import com.geekplus.maptest.Componet.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * athena测试平台，rpc任务下发接口
 * @author wanghao
 */
@Controller
@RequestMapping("/rpc")
public class RpcTestControllor {

    @Autowired
    public FileOperation fileOperation;


    @RequestMapping("/modify")
    public String modifySystmeConfig(@RequestParam("mapGe") String mapg,@RequestParam("mapGe") String mode, HttpServletResponse response) throws IOException {


        response.setContentType("text/html;charset=utf-8");

      response.getWriter().write( "<script>alert('');</script>");
   //   response.getWriter().write( name);
        response.getWriter().flush();


        return "robot";


    }

@RequestMapping("/index")
public String login(){

    return "index";
}




    @RequestMapping(value="/replaceFile",method =RequestMethod.POST)
//    @ResponseBody
	//获取前端data传过来的控件name=file的filew文件，且定义为MultipartFile类型的file变量，MultipartFile为获取formdate类型的file类型
    public String testRobot(@RequestParam("file") MultipartFile file,HttpServletResponse response){
List<String> list=new ArrayList();
list.add("/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/config/system/");
list.add("/home/test-tools/athena-test-3.1/resources/config/");
        response.setContentType("text/html;charset=utf-8");
        for (String item : list) {
            String result= fileOperation.replacefile(file,"/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/config/system/");
            if (result.equals("success")){
                try {
                    response.getWriter().write( "<script>alert('上传成功"+item+"');</script>");
                    response.getWriter().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {

                try {
                    response.getWriter().write( "<script>alert('上传失败');</script>");
                    response.getWriter().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }





            
        }

        //   response.getWriter().write( name);


return "robot";



    }

}
