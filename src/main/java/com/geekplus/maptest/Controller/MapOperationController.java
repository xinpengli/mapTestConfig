package com.geekplus.maptest.Controller;

import cn.hutool.core.io.file.FileReader;
import com.geekplus.maptest.Common.Dom4jUtil;
import com.geekplus.maptest.Componet.FileOperation;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/map")
public class MapOperationController {

    @Autowired
    public FileOperation fileOperation;



    @ResponseBody
    @RequestMapping(value = "/upfile")
    public Map<String, String> addQueueSize(@RequestParam("file") MultipartFile file) {

        System.out.println(MapOperationController.class.getResource("/config").toString());

        String message = fileOperation.replacefile(file, MapOperationController.class.getResource("/config/").getFile());
        System.out.println(file);
//        System.out.println(queueSize);

        Map<String, String> map = new HashMap<>();

        map.put("msg", message);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/QueueSize")
    public Map<String, String> addQueueSize(@RequestParam("QueueSize") String QueueSize ) throws DocumentException {

        org.dom4j.Document document =Dom4jUtil.readXML("/config/map.xml");
        Dom4jUtil.addElements(document,"virtualstations/virtualstation","queuesize/"+QueueSize);

        Dom4jUtil.addElementsByFilterChildText(document,"stations/station","queuesize/"+QueueSize,"layout/6");

        FileReader fileReader= new FileReader(MapOperationController.class.getResource("/config/map.xml").getFile());
       String mapText= fileReader.readString();
       Map<String,String> map= new HashMap<>();
       map.put("mapText",mapText);
       return map;

    }

}
