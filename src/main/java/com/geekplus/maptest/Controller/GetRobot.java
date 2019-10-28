package com.geekplus.maptest.Controller;

import com.geekplus.maptest.entity.SystemAndRobotConfig;
import com.geekplus.maptest.map.GetRobotConfig;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/robot")
 public class GetRobot implements ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(GetRobot.class);
   public   List<Map<String, String>> shelfList=new ArrayList<Map<String,String>>();
   /**configHandler:获取机器人配置的处理器bean集合，old,new*/
   public   Map<String, GetRobotConfig> configHandler;



    @RequestMapping("/robotconfig")
    public String robotconfig() {
        return "robotconfig";
    }
    @RequestMapping("/Tomapmodify")
    public String Tomapmodify() {

        return "mapModify";
    }

   /**restful 返回model
     @RequestMapping("/config")

     public String config(@RequestParam Map<String, String> map, Model model) throws IOException, DocumentException {

     Optional<GetRobotConfig> RobotConfig = configHandler.entrySet().stream().filter(v -> map.get("Sversion").equals(v.getValue().Version().name())).map(e -> e.getValue()).findFirst();
     model.addAttribute("data", RobotConfig.get().GetRobots(map));
     return "robotconfig";
     }*/
     /**返回json
      * */
     @RequestMapping("/config")
     @ResponseBody
     public Map<String,String> config(@RequestParam Map<String, String> map) throws IOException, DocumentException {

         Optional<GetRobotConfig> RobotConfig = configHandler.entrySet().stream().filter(v -> map.get("Sversion").equals(v.getValue().Version().name())).map(e -> e.getValue()).findFirst();
         Map<String, String> mapResult = new HashMap<>();
         mapResult.put("data", RobotConfig.get().GetRobots(map));
         return mapResult;
     }

//实现装配接口后，实现方法，当初始化当前子类后调用改方法，把bean装载进来，每个接口的实现类功能不一样，用枚举属性区别，然后调用时过滤获取指定的bean
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //getBeansOfType(GetRobotConfgig.class);根据类名称获取容器中的bean
        configHandler=applicationContext.getBeansOfType(GetRobotConfig.class);
    }
}
