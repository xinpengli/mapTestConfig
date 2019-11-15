package com.geekplus.maptest.RobotConfigHangdler;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geekplus.maptest.Common.HttpUtil;
import com.geekplus.maptest.Common.YmlUtils;
import com.geekplus.maptest.Enum.RobotVersion;
import com.geekplus.maptest.entity.FloorsMapCell;
import com.geekplus.maptest.entity.LowMapCell;
import com.geekplus.maptest.entity.RobotLocation;
import com.geekplus.maptest.entity.SystemAndRobotConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
@Setter
@Getter
public class NewRobotConfigHandlerImp extends AbstractGetRobotConfig implements  GetRobotConfig{

@Autowired
    SystemAndRobotConfig systemAndRobotConfig;
/*@Autowired
    RobotLocation robotLocation;*/

    @Override
    public String GetRobots(Map<String, String> map) throws  IOException {


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



        /**获取整个whmapcells集合嵌套jsonarry*/

        if (version.equals("2.0")) {
            JSONArray whMapCellsList = response.getJSONObject("response").getJSONObject("body").getJSONObject("map").getJSONArray("whMapCells");

            for (int i = 0; i < whMapCellsList.size(); i++) {
//获取whMapCellsList.getJSONArray(i)又是一个数组;JSONObject.parseArray（array.tostring，mapcell.class）把数组转化成list<mapcell>,因为数组每个元素都是一个MapCell
                List<LowMapCell> list = JSONObject.parseArray(whMapCellsList.getJSONArray(i).toJSONString(), LowMapCell.class);
                //
                //获取货架老家集合
                getShelfPlacements(list, "database");
            }
        } else if (version.equals("3.2")) {
            JSONArray floorsArray = new JSONArray();
            floorsArray = response.getJSONObject("response").getJSONObject("body").getJSONObject("map").getJSONArray("floors");
            if (mode.equals("fusion")) {
                List<FloorsMapCell> fusion = JSONObject.parseArray(floorsArray.getJSONObject(0).getJSONArray("fusionMap").toJSONString(), FloorsMapCell.class);
                getShelfPlacements(fusion, "database");
            } else {
                JSONArray whMapCellsList = floorsArray.getJSONObject(0).getJSONArray("matrixMap");

                for (int i = 0; i < whMapCellsList.size(); i++) {
//获取whMapCellsList.getJSONArray(i)又是一个数组;JSONObject.parseArray（array.tostring，mapcell.class）把数组转化成list<mapcell>,因为数组每个元素都是一个MapCell
                    List<LowMapCell> list = JSONObject.parseArray(whMapCellsList.getJSONArray(i).toJSONString(), LowMapCell.class);
                    //
                    //获取货架老家集合
                    getShelfPlacements(list, "database");
                }

            }


        }

List<RobotLocation> robotlist =new ArrayList<>();

        if (robotNum <= shelfList.size()) {

            for (int i=0 ;i< robotNum;i++){
                RobotLocation robotLocation= new RobotLocation();
                robotLocation.setX(Float.parseFloat(shelfList.get(i).get("x")));
                robotLocation.setY(Float.parseFloat(shelfList.get(i).get("y")));
                robotlist.add(robotLocation);
            }
        systemAndRobotConfig.setRobots(robotlist);

          YmlUtils.addIntoYml("/config/robotConfig.yaml",systemAndRobotConfig);
            FileReader fileReader = null;
            fileReader = new FileReader(YmlUtils.class.getResource("/config/robotConfig.yaml").getFile());
            String filestring=fileReader.readString();
            return filestring;

    }else {
            return "输入数量过大";
        }
    }

    @Override
    public RobotVersion Version() {
        return RobotVersion.NEW ;
    }
}
