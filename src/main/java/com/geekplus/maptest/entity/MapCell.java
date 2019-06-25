package com.geekplus.maptest.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter

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


















}
