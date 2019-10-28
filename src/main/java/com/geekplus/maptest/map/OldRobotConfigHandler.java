package com.geekplus.maptest.map;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geekplus.maptest.Common.Dom4jUtil;
import com.geekplus.maptest.Common.HttpUtil;
import com.geekplus.maptest.Enum.RobotVersion;
import com.geekplus.maptest.entity.FloorsMapCell;
import com.geekplus.maptest.entity.LowMapCell;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@Component
public class OldRobotConfigHandler extends AbstractGetRobotConfig implements GetRobotConfig {

    @Override
    public String GetRobots(Map<String, String> map) throws DocumentException, IOException {

        Document document = Dom4jUtil.readXML("/config/config.xml");
        int robotNum = Integer.parseInt(map.get("robotNum"));
        String server = map.get("server");
        String mode = map.get("mode");
        String version = map.get("version");

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

        if (version.equals("2.0")) {
            JSONArray whMapCellsList = response.getJSONObject("response").getJSONObject("body").getJSONObject("map").getJSONArray("whMapCells");
            /*遍历whmapcells,放入每个新的jsonarry*/
//    whMapCellsList.getJSONArray(0).toJavaList(MapCell.class);

            for (int i = 0; i < whMapCellsList.size(); i++) {
//获取whMapCellsList.getJSONArray(i)又是一个数组;JSONObject.parseArray（array.tostring，mapcell.class）把数组转化成list<mapcell>,因为数组每个元素都是一个MapCell
                List<LowMapCell> list = JSONObject.parseArray(whMapCellsList.getJSONArray(i).toJSONString(), LowMapCell.class);
                //
                //获取货架老家集合
                getShelfPlacements(list, mode);
            }
        } else if (version.equals("3.2")) {
            JSONArray floorsArray = new JSONArray();
            floorsArray = response.getJSONObject("response").getJSONObject("body").getJSONObject("map").getJSONArray("floors");
            if (mode.equals("fusion")) {
                List<FloorsMapCell> fusion = JSONObject.parseArray(floorsArray.getJSONObject(0).getJSONArray("fusionMap").toJSONString(), FloorsMapCell.class);
                getShelfPlacements(fusion, mode);
            } else {
                JSONArray whMapCellsList = floorsArray.getJSONObject(0).getJSONArray("matrixMap");

                for (int i = 0; i < whMapCellsList.size(); i++) {
//获取whMapCellsList.getJSONArray(i)又是一个数组;JSONObject.parseArray（array.tostring，mapcell.class）把数组转化成list<mapcell>,因为数组每个元素都是一个MapCell
                    List<LowMapCell> list = JSONObject.parseArray(whMapCellsList.getJSONArray(i).toJSONString(), LowMapCell.class);
                    //
                    //获取货架老家集合
                    getShelfPlacements(list, mode);
                }

            }


        }

        Dom4jUtil.addRobot(document, shelfList, robotNum);
        if (robotNum <= shelfList.size()) {
            FileReader fileReader = null;
            fileReader = new FileReader(Dom4jUtil.class.getResource("/config/config.xml").getFile());
            String filestring=fileReader.readString();
            return filestring;
        }else {
            return "输入数量过大";
        }
    }

    @Override
    public RobotVersion Version() {
      return   RobotVersion.OLD;
    }
}
