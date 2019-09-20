package com.geekplus.maptest.entity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public  class MapCell {
//   Map<String,Object> mapCelllist;

        Map<String,String> index;
        Map<String,String> location;
        String cellType;
        String cellStatus;
        Object direction;
        Object dirUnload;
        Object allocatedRobotId;
        Object occupyRobotId;
        Object occupiedShelfCode;
        boolean a;
        int b;


        public static void main(String[] args) {
                MapCell  mapCell=new MapCell();
            MapCell  json2mapCell=new MapCell();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("cellType","shelf_cell");
             Map<String, Object> map = new HashMap<>();
             map.put("x",1);
             map.put("y",3);
            jsonObject1.put("index",map);
            json2mapCell= jsonObject1.toJavaObject(MapCell.class);
                mapCell.setCellStatus("new");


              JSONObject jsonObject = (JSONObject)JSONObject.toJSON(mapCell);

JSONObject jsonObject2=JSON.parseObject(jsonObject1.toJSONString(),JSONObject.class);




            System.out.println(jsonObject);
            System.out.println(json2mapCell);

        }




}
