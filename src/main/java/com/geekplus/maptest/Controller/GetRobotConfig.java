package com.geekplus.maptest.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.geekplus.maptest.Common.Dom4jUtil;
import com.geekplus.maptest.Common.HttpUtil;
import com.geekplus.maptest.entity.MapCell;
import com.geekplus.maptest.entity.TestJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/robot")
public class GetRobotConfig {
    private static final Logger logger = LoggerFactory.getLogger(GetRobotConfig.class);






    @RequestMapping("/robotconfig")
public String robotconfig()   {
return "robotconfig";
}
    @RequestMapping("/config")
    @ResponseBody
    public String config(@RequestBody TestJob testJob) throws IOException {
        int robotNum = testJob.getRobotNum();
        String server = testJob.getServer();
        String mode = testJob.getMode();
        String version=testJob.getVersion();

        JSONObject request = new JSONObject();
        JSONObject requestJson = new JSONObject();
        JSONObject response = new JSONObject();
        JSONObject body = new JSONObject();
        body.put("instruction", "MAP");
        requestJson.put("body", body);
        requestJson.put("header", HttpUtil.getRequestHeder(version));
        request.put("request", requestJson);
        request.put("id", "clientid");
        request.put("msgType", "com.geekplus.athena.api.msg.req.QueryInstructionRequestMsg");
        response = HttpUtil.postJsonData("http://" + server + ":8895/", request);



        /*获取整个whmapcells集合嵌套jsonarry*/
        JSONArray whMapCellsList = response.getJSONObject("response").getJSONObject("body").getJSONObject("map").getJSONArray("whMapCells");


        /*遍历whmapcells,放入每个新的jsonarry*/
//    whMapCellsList.getJSONArray(0).toJavaList(MapCell.class);
        List<Map<String, String>> shelfList = new ArrayList<>();
        for (int i = 0; i < whMapCellsList.size(); i++) {

            List<MapCell> list = JSONObject.parseArray(whMapCellsList.getJSONArray(i).toJSONString(), MapCell.class);
            for (int j = 0; j < list.size(); j++) {
                // if (mode.equals("XML"))
                if (list.get(j).getCellType().equals("SHELF_CELL")) {
                    if (mode.equals("xml")) {
                        //如果末尾数为0改成5
                   Map<String,String> index=list.get(j).getIndex()    ;
                        Map<String,String> index1=new HashMap<>();
                        System.out.println(index.get("x"));
                   if (isinteger(index.get("x").toString())){



                       index1.put("x",index.get("x").toString()+".5");

                   }else{

                       String x=index.get("x").toString().substring(0,index.get("x").length()-1);

                       index1.put("x",x+"5");
                   }
                   //y
                        if (isinteger(index.get("y").toString())){



                            index1.put("y",index.get("y").toString()+".5");

                        }else{

                            String y=index.get("y").toString().substring(0,index.get("y").length()-1);

                            index1.put("y",y+"5");
                        }



                        list.get(j).setIndex(index1);

                        shelfList.add(list.get(j).getIndex());

                    } else if (mode.equals("database")||mode.equals("fusion")) {
                        shelfList.add(list.get(j).getLocation());
                    }
                }


            }

        }



        if (robotNum <= shelfList.size()) {
            String str = Dom4jUtil.addRobot(shelfList, robotNum);

            return str;
        } else {
            return "输入数量过大！";
        }


        // JSONArray array = new JSONArray();


    }

public boolean isinteger(String str){
        if (str.split("\\.").length==1){

        return true;
         }else {
        return false;
        }
}

}
