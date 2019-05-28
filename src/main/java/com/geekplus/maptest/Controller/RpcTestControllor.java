package com.geekplus.maptest.Controller;



import com.geekplus.maptest.Common.PropertiesUtils;
import com.geekplus.maptest.Common.YmlUtils;
import com.geekplus.maptest.Componet.FileOperation;
import org.slf4j.LoggerFactory;
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
    private  static  final org.slf4j.Logger logger= LoggerFactory.getLogger(RpcTestControllor.class);

    @Autowired
    public FileOperation fileOperation;
    @Autowired
    public PropertiesUtils propertiesUtils;



    @RequestMapping("/modify")
    public String modifySystmeConfig(@RequestParam("mapGe") String mapg,@RequestParam("mode") String mode, HttpServletResponse response) throws IOException, InterruptedException {
String sysFilepath="/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/config/system/sysconfig.prpperties";
String appliFilepath="/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/application.properties";
File testTollsFile=new  File("/home/test-tools/athena-test-3.1/resources/application.yml");




switch (mode){
    case  "DATABASE":
        propertiesUtils.replacePro(sysFilepath,"map.resolver","transportDatabseMapResolver");
        propertiesUtils.replacePro(appliFilepath,"spring.profiles.active","global,netty,db,transport");

        YmlUtils.addIntoYml(testTollsFile,"map.resolver","Database");



        if (mapg.equals("with_start_bound")){
            propertiesUtils.replacePro(sysFilepath,"map.resolving.mode","with_start_bound");

        }else  if(mapg.equals("no_start_bound")){
            propertiesUtils.replacePro(sysFilepath,"map.resolving.mode","no_start_bound");

        }
        break;

    case "XML" :
            propertiesUtils.replacePro(sysFilepath,"map.resolver","pickingXmlMapResolver");
            propertiesUtils.replacePro(appliFilepath,"spring.profiles.active","global,netty,db,picking");
        propertiesUtils.replacePro(sysFilepath,"map.resolving.mode","no_start_bound");
        YmlUtils.addIntoYml(testTollsFile,"map.resolver","Xml");
        break;
    case "NOShelfArea" :
        propertiesUtils.replacePro(sysFilepath,"map.resolver","transportDatabseMapResolver");
        propertiesUtils.replacePro(appliFilepath,"spring.profiles.active","global,netty,db,transport");
        YmlUtils.addIntoYml(testTollsFile,"map.resolver","NOShelfArea");




        if (mapg.equals("with_start_bound")){
            propertiesUtils.replacePro(sysFilepath,"map.resolving.mode","with_start_bound");

        }else  if(mapg.equals("no_start_bound")){
            propertiesUtils.replacePro(sysFilepath,"map.resolving.mode","no_start_bound");

        }
        break;
        default:
            logger.warn("传入值"+mode+"不存在");

}

        fileOperation.executXhshell("stop.sh");
        fileOperation.executXhshell("stop-tools.sh");

        response.setContentType("text/html;charset=utf-8");

      response.getWriter().write( "<script>alert('修改成功');</script>");
   //   response.getWriter().write( name);
        response.getWriter().flush();


        return "robot";


    }

@RequestMapping("/index")
public String login(){


    return "index";
}

    @RequestMapping("/robot")
    public String robot(){


        return "robot";
    }



    @RequestMapping(value="/replaceFile",method =RequestMethod.POST)
//    @ResponseBody
	//获取前端data传过来的控件name=file的filew文件，且定义为MultipartFile类型的file变量，MultipartFile为获取formdate类型的file类型
    public String testRobot(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws InterruptedException, IOException {

        /*关闭rms以及测试工具*/

 fileOperation.executXhshell("stop.sh");
 fileOperation.executXhshell("stop-tools.sh");


List<String> list=new ArrayList();
list.add("/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/config/system/");
list.add("/home/test-tools/athena-test-3.1/resources/config/");

        response.setContentType("text/html;charset=utf-8");
        for (String item : list) {
            String result= fileOperation.replacefile(file,item);
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
