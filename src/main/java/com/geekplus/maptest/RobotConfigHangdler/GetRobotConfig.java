package com.geekplus.maptest.RobotConfigHangdler;

import com.geekplus.maptest.Enum.RobotVersion;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.Map;

public interface GetRobotConfig  {

     public  String GetRobots(Map<String,String> map) throws DocumentException, IOException;
     public RobotVersion Version();
}
