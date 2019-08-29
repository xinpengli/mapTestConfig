package com.geekplus.maptest.Common;

import cn.hutool.core.io.file.FileReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dom4jUtil {

public  static String filepath;

/**
 *返回的文档，可以直接操作
 * read 可以是url,pathname,inputsream
 * */
    public static Document readXML(String filePath) throws DocumentException {
        filepath=filePath;
        return new SAXReader().read(Dom4jUtil.class.getResource(filePath));



    }
   /**
        修改后保存
 * */
    public static void save(Document document) throws DocumentException {

        XMLWriter output;
        OutputFormat format;


        output = null;
        //createPrettyPrint,美化xml格式，setTrimText改为false,因为默认为true可能删除多余回车空格，导致结尾标签没有回车
        format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setTrimText(false);

//        format.setEncoding("UTF-8");
        try {

            output = new XMLWriter(new FileOutputStream(Dom4jUtil.class.getResource(filepath).getFile(), false), format); //这里的path是修改后需要保存的路径，建议和未修改前位置一样
            output.write(document);
            //output.flush();
            output.close();


        } catch (IOException e) {
            e.printStackTrace();

        }





    }


/**增加子节点并赋值(判断同一级子节点的g规定节点且规定节点text)
 *param document
 *param path 要在路径节点上加节点的路径
 *param newchildname 新加节点/节点值
 *param Filterchild   过滤同级节点/节点值
*/
    public static void addElementsByFilterChildText(Document document,String path, String newchildname,String Filterchild) throws DocumentException {
    List<Element> elements=   getElementsBychildElementText(document,path,Filterchild);
    String[] arry=newchildname.split("/");
//如果存在子节点，删除
        for (Element element :elements){
            if (!(element.element(arry[0]) == null)) {
                element.remove((element.element(arry[0])));
            }
            element.addElement(arry[0]).setText(arry[1]);
        }

                   save(document);

    }
    /**增加子节点并赋值(判断同一级子节点的g规定节点且规定节点text)
     *param document
     *param path 要在路径节点上加节点的路径
     *param newchildname 新加节点/节点值
     *param Filterchild   过滤同级节点/节点值
     */
    public static void addElements(Document document,String path, String newchildname) throws DocumentException {
        List<Element> elements=   getElements(document,path);
        String[] arry=newchildname.split("/");
//如果存在子节点，删除
        for (Element element :elements){
            if (!(element.element(arry[0]) == null)) {
                element.remove((element.element(arry[0])));
            }
            element.addElement(arry[0]).setText(arry[1]);
        }

        save(document);

    }

    /**
     * 获取路径下的元素
     *
     * @param document
     * @param path
     * @return
     */
    public static Element getElement(Document document, String path) {
        if (document != null && document.getRootElement() != null && path != null) {
            String[] arr = path.split("/");
            return getElement(document.getRootElement(), arr, arr.length);
        }

        return null;
    }
    /**
     * 获取路径下的第几层元素
     * stations/station找一层就是deepth=1就是找第一个节点未stations的元素
     * @param elem
     * @param path
     * @param deepth
     * @return
     */

    private static Element getElement(Element elem, String[] path, int deepth) {
        for (int i = 0; i < deepth && i < path.length - 1; ++i) {
            elem = elem.element(path[i]);
            if (elem == null) {
                return null;
            }
        }

        return elem;
    }
    /**
     * 获取路径下的元素集合
     *stations/station获取第一个stations节点下所有的子元素station集合
     * @param document
     * @param path
     * @return
     */
    public static List<Element> getElements(Document document, String path) {
        if (document != null && document.getRootElement() != null && path != null) {
            String[] arr = path.split("/");
            Element elem = getElement(document.getRootElement(), arr, arr.length - 1);

            if (elem != null) {
                List<?>	list = elem.elements(arr[arr.length - 1]);
                if (list != null) {
                    return (List<Element>) list;
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * 获取路径下的元素集合（满足当前路径最后的元素的text等于参数text）
     *获取最终集合未path的第一个stations下 的  station集合path=stations/station 且满足station子元素layout=1;注：child=latout/1
     * @param document
     * @param path 节点路径 station/latout
     * @param child 节点路径最后元素的子元素以及text组合 latout/1
     * @return 满足条件的集合
     */
    public static List<Element> getElementsBychildElementText(Document document, String path,String child) {
        if (document != null && document.getRootElement() != null && path != null) {
            String[] arr = path.split("/");
            String[] childArr = child.split("/");
            Element elem = getElement(document.getRootElement(), arr, arr.length - 1);

            if (elem != null) {
                List<Element>	list = elem.elements(arr[arr.length - 1]);
                List<Element>	listText =  new ArrayList<>();
                if (list != null) {
                    //element就是list里的元素对象类型
                    listText=list.stream().filter(Element-> childArr[1].equals(Element.element(childArr[0]).getText())).collect(Collectors.toList());
                    return (List<Element>) listText;
                }
            }
        }

        return Collections.emptyList();
    }

    //	获取xml
    public static void addRobot(Document document,List<Map<String, String>> robotNumlist, int num) throws DocumentException {
        //刪除节点
        Element robots = document.getRootElement().element("robots");
        List<Object> elements = robots.elements("robot");
        for (int i = 0; i < elements.size(); i++) {
            Element element = (Element) elements.get(i);

            element.getParent().remove(element);

        }
//增加

        for (int i = 0; i < num; i++) {
            String x = robotNumlist.get(i).get("x");
            String y = robotNumlist.get(i).get("y");

            robots.addElement("robot").addAttribute("x", x.toString()).addAttribute("y", y.toString());


        }
        save(document);

    }
	
	
	

	/*public Dom4jUtil() {
		String path = "mapcells";
		// 获取mapcells标签的内容

		// 获取标签指定属性length的值
		List<Element> list = document.getRootElement().elements(path);

		this.floorNum = list.size();
	}*/


    public static void main(String[] args) {
        // TODO Auto-generated method stub
		/*int[][] mapcell = new int[3][4];
		Dom4jUtil a =new Dom4jUtil();
		int[][] mapcell1=   (int[][])	a.getShelfCell().get(1);*/
//		a.getSliglStations("virtualstation1s/virtua1lstation");
//		System.out.println(a.getSliglStations("virtualstation1s/virtua1lstation"));
//		System.out.println(mapcell1[0][0]);
		/*Dom4jUtil.getDocment("13");
		Dom4jUtil.getSliglStations("123413");
		Dom4jUtil.getShelfCell("123");*/

//		System.out.println(Dom4jUtil.env.getProperty("aaa"));
        //Dom4jUtil.getdoc();


    }

}
