package com.geekplus.maptest.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.EAN;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@Getter
@Setter
public class SystemAndRobotConfig {
    public  List<RobotLocation> robots;

    /* public Map<String,Integer> server;
     public Map<String,Map<String,String>> spring=new HashMap<>();
    public Map<String,Object> simulation=new HashMap<>();


    public void setDefault(String type) {
        this.server = new HashMap<>();
         Map<String,String> application=new HashMap<>();


        application.put("name","athena-simulation");
        server.put("port",8501);
        spring.put("application", application);
        simulation.put("serverIp","127.0.0.1");
        simulation.put("port",8899);
        simulation.put("startId",1000);
        simulation.put("power",90);
        simulation.put("angle",90);

        simulation.put("type", type);





    }*/
}
