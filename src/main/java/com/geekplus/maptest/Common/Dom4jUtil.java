package com.geekplus.maptest.Common;

import cn.hutool.core.io.file.FileReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Dom4jUtil {


    //@Autowired

    public static int floorNum;
    public static SAXReader saxReader = new SAXReader();
    public static URL url = Dom4jUtil.class.getResource("/config/config.xml");
    public static String pathname = url.getFile();
    public static File inputXml = new File(pathname);
    public static Document document;

    static {
        try {
            document = saxReader.read(inputXml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    public static String addRobot(List<Map<String, String>> robotNumlist, int num) {
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

        XMLWriter output;
        OutputFormat format;


        output = null;
        format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
//        format.setEncoding("UTF-8");
        try {

            output = new XMLWriter(new FileOutputStream(inputXml, false), format); //这里的path是修改后需要保存的路径，建议和未修改前位置一样
            output.write(document);
            output.flush();
            output.close();


        } catch (IOException e) {
            e.printStackTrace();

        }
        FileReader fileReader = null;
        fileReader = new FileReader(inputXml);
        System.out.println(fileReader.readString());

        return fileReader.readString();
    }
    //	获取xml

	
	
	

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
