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
/**静态块设置yaml的格式
 * */
    static {
        //将默认读取的方式设置为块状读取
        OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
       // OPTIONS.setSplitLines(true);
    //内容的缩进，指的是换行普通的空格缩进
       // OPTIONS.setIndent(5);
       //OPTIONS.setExplicitStart(true);

    //指示标缩进比如list集合  的-
       //OPTIONS.setIndicatorIndent(4);
    //OPTIONS.setDefaultScalarStyle(DumperOptions.ScalarStyle.FOLDED);


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
        /*载入当前yml文件为map对象，也可以是其他自建bean,如果是在原文件的基础上更新文件，先loadas，然后，给对象更新赋值，
            Map  dataMap =    yaml.loadAs(new FileInputStream(source),Map.class);
              //如果yml内容为空,则会引发空指针异常,此处进行判断
              if (null == dataMap) {
            dataMap = new LinkedHashMap<>();
        }*/
       Map dataMap = new LinkedHashMap<>();
       dataMap.put("key",map);



        //将数据重新写回文件/可以是map对象，也可以是其他自建bean
        yaml.dump(dataMap, new FileWriter(source));
    }
/***
 * 通过bean整体添加
* */
    public static <T> void addIntoYml(String source,T entity  ) throws IOException {
        Yaml yaml = new Yaml(OPTIONS);
        /*载入当前yml文件为map对象，也可以是其他自建bean,如果是在原文件的基础上更新文件，先loadas，然后，给对象更新赋值，
            Map  dataMap =    yaml.loadAs(new FileInputStream(source),Map.class);
              //如果yml内容为空,则会引发空指针异常,此处进行判断
              if (null == dataMap) {
            dataMap = new LinkedHashMap<>();
        }*/
File  file=new File(YmlUtils.class.getResource(source).getFile());
        //将数据重新写到文件/可以是map对象，也可以是其他自建bean
        yaml.dump(entity, new FileWriter(file));
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
        //判断是否为空



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
