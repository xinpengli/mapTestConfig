package com.geekplus.maptest.Controller;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.geekplus.maptest.Common.Dom4jUtil;
import com.geekplus.maptest.Common.HttpUtil;
import com.geekplus.maptest.entity.FloorsMapCell;
import com.geekplus.maptest.entity.LowMapCell;
import com.geekplus.maptest.entity.MapCell;
import com.geekplus.maptest.entity.TestJob;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/robot")
public class GetRobotConfig {
    private static final Logger logger = LoggerFactory.getLogger(GetRobotConfig.class);
   public   List<Map<String, String>> shelfList=new ArrayList<Map<String,String>>();

    @RequestMapping("/robotconfig")
    public String robotconfig() {
        return "robotconfig";
    }
    @RequestMapping("/Tomapmodify")
    public String Tomapmodify() {

        return "mapModify";
    }

    @RequestMapping("/config")
    @ResponseBody
    public Map<String, String> config(@RequestBody TestJob testJob) throws IOException, DocumentException {
        Document document=Dom4jUtil.readXML("/config/config.xml");
        int robotNum = testJob.getRobotNum();
        String server = testJob.getServer();
        String mode = testJob.getMode();
        String version = testJob.getVersion();

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

//        List<MapCell> list=null;
      //  List<FloorsMapCell> listHigh=null;

        /*获取整个whmapcells集合嵌套jsonarry*/

        if (version.equals("2.0")){
            JSONArray whMapCellsList = response.getJSONObject("response").getJSONObject("body").getJSONObject("map").getJSONArray("whMapCells");
            /*遍历whmapcells,放入每个新的jsonarry*/
//    whMapCellsList.getJSONArray(0).toJavaList(MapCell.class);

            for (int i = 0; i < whMapCellsList.size(); i++) {
//获取whMapCellsList.getJSONArray(i)又是一个数组;JSONObject.parseArray（array.tostring，mapcell.class）把数组转化成list<mapcell>,因为数组每个元素都是一个MapCell
                List<LowMapCell>     list = JSONObject.parseArray(whMapCellsList.getJSONArray(i).toJSONString(), LowMapCell.class);
                //
                //获取货架老家集合
                getShelfPlacements(list, mode);
            }
        }else if (version.equals("3.2")){
            JSONArray floorsArray= new JSONArray();
            floorsArray = response.getJSONObject("response").getJSONObject("body").getJSONObject("map").getJSONArray("floors");
            if (mode.equals("fusion")){
                List<FloorsMapCell>  fusion = JSONObject.parseArray(floorsArray.getJSONObject(0).getJSONArray("fusionMap").toJSONString(), FloorsMapCell.class);
                getShelfPlacements(fusion, mode);
            }else{
              JSONArray     whMapCellsList   = floorsArray.getJSONObject(0).getJSONArray("matrixMap");

                for (int i = 0; i < whMapCellsList.size(); i++) {
//获取whMapCellsList.getJSONArray(i)又是一个数组;JSONObject.parseArray（array.tostring，mapcell.class）把数组转化成list<mapcell>,因为数组每个元素都是一个MapCell
                    List<LowMapCell>     list = JSONObject.parseArray(whMapCellsList.getJSONArray(i).toJSONString(), LowMapCell.class);
                    //
                    //获取货架老家集合
                    getShelfPlacements(list, mode);
                }

            }


        }
       /* switch (version) {
            case "2.0":


                break;
            case "3.2" :

                if (mode.equals("database") || mode.equals("xml")){
                    List<MapCell>     list   = JSONObject.parseArray(floorsArray.getJSONObject(0).getJSONArray("fusionMap").toJSONString(), MapCell.class);
                    //
                    //获取货架老家集合
                    getShelfPlacements(list, mode);
                }else{
                    List<FloorsMapCell>  listHigh = JSONObject.parseArray(floorsArray.getJSONObject(0).getJSONArray("fusionMap").toJSONString(), FloorsMapCell.class);

                }



//floorsArray.getJSONObject(0)数组元素的第一个元素，

//                 shelfList = getShelfPlacements(listHigh, mode);
                break;
        }*/

        Map<String,String> map =new HashMap<>();
        if (robotNum <= shelfList.size()) {
            Dom4jUtil.addRobot(document, shelfList, robotNum);
            FileReader fileReader = null;
            fileReader = new FileReader(Dom4jUtil.class.getResource("/config/config.xml").getFile());

            shelfList.clear();
            map.put("code","0");
            map.put("msg","成功");
            map.put("data",fileReader.readString());


            return map;
        } else {
            map.put("code","100");
            map.put("msg","失败");
            map.put("data","输入数量过大");
            return map;

        }


        // JSONArray array = new JSONArray();


    }

    /*
    遍历获取查找货架老家单元格，
    * */
//    public <T extends MapCell> List<Map<String, String>> getShelfPlacements(List<T> list, String mode) {
    public <T extends MapCell> List<Map<String, String>> getShelfPlacements(List<T> list, String mode) {
//        List<Map<String, String>> shelfList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            // if (mode.equals("XML"))
            if (list.get(j).getCellType().equals("SHELF_CELL")) {
                if (mode.equals("xml")) {
                    //如果末尾数为0改成5
                    Map<String, String> index = list.get(j).getIndex();
                    Map<String, String> index1 = new HashMap<>();
                    System.out.println(index.get("x"));
                    if (isinteger(index.get("x").toString())) {


                        index1.put("x", index.get("x").toString() + ".5");

                    } else {

                        String x = index.get("x").toString().substring(0, index.get("x").length() - 1);

                        index1.put("x", x + "5");
                    }
                    //y
                    if (isinteger(index.get("y").toString())) {


                        index1.put("y", index.get("y").toString() + ".5");

                    } else {

                        String y = index.get("y").toString().substring(0, index.get("y").length() - 1);

                        index1.put("y", y + "5");
                    }


                    list.get(j).setIndex(index1);

                    shelfList.add(list.get(j).getIndex()) ;

                } else if (mode.equals("database") || mode.equals("fusion")) {
                    shelfList.add(list.get(j).getLocation());
                }
            }


        }

       return shelfList;
    }

    public boolean isinteger(String str) {
        if (str.split("\\.").length == 1) {

            return true;
        } else {
            return false;
        }
    }

}
