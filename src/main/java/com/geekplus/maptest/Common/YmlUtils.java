package com.geekplus.maptest.Common;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class YmlUtils {

    private final static DumperOptions OPTIONS = new DumperOptions();

    static {
        //将默认读取的方式设置为块状读取
        OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    }


    /**
     * 在目标文件中添加新的配置信息
     *
     * @param  dest  需要添加信息的目标yml文件
     * @param key   添加的key值
     * @param value 添加的对象(如key下方还有链接则添加LinkedHashMap)
     * @author Relic
     * @title addIntoYml
     * @date 2019/1/29 20:52
     */
    public static void addIntoYml(File source, String key, Object value) throws IOException {
        Yaml yaml = new Yaml(OPTIONS);
        //载入当前yml文件
        Map  dataMap =    yaml.loadAs(new FileInputStream(source),Map.class);
        //如果yml内容为空,则会引发空指针异常,此处进行判断
        if (null == dataMap) {
            dataMap = new LinkedHashMap<>();
        }
        dataMap.put(key, value);
        //将数据重新写回文件
        yaml.dump(dataMap, new FileWriter(source));
    }

    /**
     * 从目标yml文件中读取出所指定key的值
     *
     * @param source 获取yml信息的文件
     * @param key    需要获取信息的key值
     * @return java.lang.Object
     * @author Relic
     * @title getFromYml
     * @date 2019/1/29 20:56
     */
    public static Object getFromYml(File source, String key) throws IOException {

        Yaml yaml = new Yaml();
        //载入文件
        Map  dataMap =    yaml.loadAs(new FileInputStream(source),Map.class);


        //获取当前key下的值(如果存在多个节点,则value可能为map,自行判断)
        return dataMap.get(key);
    }

    public static void main(String[] args) throws IOException {
       String p1=  YmlUtils.getFromYml(new File("C:\\Atoutest\\maptest\\src\\main\\resources\\application.yml"),"server.port").toString();
        System.out.println(p1);
    }

}
