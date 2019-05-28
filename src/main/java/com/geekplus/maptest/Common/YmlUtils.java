package com.geekplus.maptest.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
@Component
public class YmlUtils {
private  static  final Logger logger=LoggerFactory.getLogger(YmlUtils.class);
    private final static DumperOptions OPTIONS = new DumperOptions();

    static {
        //将默认读取的方式设置为块状读取
        OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    }


    /*
     * 在目标文件中添加新的配置信息
     *
     * @param  dest  需要添加信息的目标yml文件
     * @param key   添加的key值
     * @param value 添加的对象(如key下方还有链接则添加LinkedHashMap)
     * @author Relic
     * @title addIntoYml
     * @date 2019/1/29 20:52
     */
    public static <T> void addIntoYml(File source, String key, T map) throws IOException {
        Yaml yaml = new Yaml(OPTIONS);
        //载入当前yml文件
        Map  dataMap =    yaml.loadAs(new FileInputStream(source),Map.class);
        //如果yml内容为空,则会引发空指针异常,此处进行判断
        if (null == dataMap) {
            dataMap = new LinkedHashMap<>();
        }

        dataMap.put(key,map);
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
    public static <T> T getFromYml(Class<T> classname,File source, String key) throws IOException {

        Yaml yaml = new Yaml();
        //载入文件
        Map  dataMap =    yaml.loadAs(new FileInputStream(source),Map.class);


        //获取当前key下的值(如果存在多个节点,则value可能为map,自行判断)
       ;
        if ( dataMap.containsKey(key)){
            return (T) dataMap.get(key);
        }else {
            logger.error(key+"值不存在");
            return null;
        }

    }


}
