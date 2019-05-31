package com.geekplus.maptest.Controller;


import com.geekplus.maptest.Common.PropertiesUtils;
import com.geekplus.maptest.Componet.FileOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * athena测试平台，rpc任务下发接口
 *
 * @author wanghao
 */
@Controller
@RequestMapping("/rpc")
public class RpcTestControllor {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RpcTestControllor.class);

    @Autowired
    public FileOperation fileOperation;
    @Autowired
    public PropertiesUtils propertiesUtils;

@ResponseBody
    @RequestMapping(value = "/modify")
    public Map<String, Object> modifySystmeConfig(@RequestParam("mapGe") String mapg, @RequestParam("mode") String mode, HttpServletResponse response) throws IOException, InterruptedException {
//    public Map<String, Object> modifySystmeConfig( @RequestParam Map<String, Object> changeData) throws IOException, InterruptedException {
       /* mapg=mapg.toLowerCase();
        mode=mode.toUpperCase();
String sysFilepath="/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/config/system/sysconfig.properties";
String appliFilepath="/usr/local/geekplus/tomcat-rms/webapps/athena/WEB-INF/classes/application.properties";
File testTollsFile=new  File("/home/test-tools/athena-test-3.1/resources/application.yml");







switch (mode.toUpperCase()){
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
    case "NOSHELFAREA" :
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

}*/
       /* response.setContentType("text/html;charset=utf-8");

        response.getWriter().write( "<script>alert('修改成功');</script>");*/
        /*   response.getWriter().flush();*/
      /*  fileOperation.executXhshell("stop.sh");
        fileOperation.executXhshell("stop-tools.sh");*/
         /*response.setContentType("text/html;charset=utf-8");

        response.getWriter().write( "<script>alert('修改成功');</script>");
           response.getWriter().flush();*/

    Map<String, Object> map=new HashMap<>();
        map.put("msg", "修改成功");

//        return "redirect:/rpc/robot";


        //   response.getWriter().write( name);
//        response.getWriter().flush();

//重定向防止重复提交
return map;

    }

    @RequestMapping("/index")
    public String login() {


        return "athena";
    }

    @RequestMapping("/robot")
    public String robot() {


        return "robot";
    }

    @RequestMapping(value = "/startservice", method = RequestMethod.GET)
    public String startService(@RequestParam("way") String way, HttpServletResponse response) throws IOException, InterruptedException {

        if (way.equals("athena")) {
            fileOperation.executXhshell("start.sh");

        } else if (way.equals("athenatest")) {
            fileOperation.executXhshell("start-tools.sh");
        }


        return "redirect:/rpc/" + way;


    }

    @RequestMapping(value = "/athena", method = RequestMethod.GET)
    public String athena(HttpServletResponse response) throws IOException, InterruptedException {

        /*if (way.equals("athena")){
            fileOperation.executXhshell("start.sh");

        }else if (way.equals("athenatest")) {
            fileOperation.executXhshell("start-tools.sh");
        }*/
        /*if (way.equals("athena")){
//            fileOperation.executXhshell("start.sh");
            return "athena";

        }else if (way.equals("athenatest")) {
//            fileOperation.executXhshell("start-tools.sh");
            return "athenatest";
        }*/
      /*  response.setContentType("text/html;charset=utf-8");

        response.getWriter().write( "script>alert('不存在的系统类型');</script>");
        //   response.getWriter().write( name);
        response.getWriter().flush();*/

//        return    "redirect:/startService";

        return "athena";


    }

    @RequestMapping(value = "/athenatest", method = RequestMethod.GET)
    public String athenatst(HttpServletResponse response) throws IOException, InterruptedException {

        /*if (way.equals("athena")){
            fileOperation.executXhshell("start.sh");

        }else if (way.equals("athenatest")) {
            fileOperation.executXhshell("start-tools.sh");
        }*/
        /*if (way.equals("athena")){
//            fileOperation.executXhshell("start.sh");
            return "athena";

        }else if (way.equals("athenatest")) {
//            fileOperation.executXhshell("start-tools.sh");
            return "athenatest";
        }*/
      /*  response.setContentType("text/html;charset=utf-8");

        response.getWriter().write( "script>alert('不存在的系统类型');</script>");
        //   response.getWriter().write( name);
        response.getWriter().flush();*/

//        return    "redirect:/startService";

        return "athenatest";


    }


    @RequestMapping(value = "/replaceFile", method = RequestMethod.POST)
     @ResponseBody
    //获取前端data传过来的控件name=file的filew文件，且定义为MultipartFile类型的file变量，MultipartFile为获取formdate类型的file类型
    public Map<String,Object> testRobot(@RequestParam("file") MultipartFile file) throws InterruptedException, IOException {

        /*关闭rms以及测试工具*/

/* fileOperation.executXhshell("stop.sh");
 fileOperation.executXhshell("stop-tools.sh");


List<String> list=new ArrayList();
list.add("/usr/local/geekplus/tomcat-rms//athena/WEB-INF/classes/config/system/");
list.add("/home/test-tools/athena-test-3.1/resources/config/");*/

     /*   response.setContentType("text/html;charset=utf-8");
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






        }*/
       /* response.setContentType("text/html;charset=utf-8");
        response.getWriter().write( "<script>alert('上传成功"+file+"');</script>");
        response.getWriter().flush();*/
        //   response.getWriter().write( name);
      /*  Map<String,Object> map= new HashMap<>();
        map.put("msg","替换成功");
        return map;*/
//return "robot";
        //重定向url会变成/rpc/robot,以防刷新时post,get重复提交
        Map<String,Object> map= new HashMap<>();
        map.put("msg","上传成功");
        return map;


    }

}
