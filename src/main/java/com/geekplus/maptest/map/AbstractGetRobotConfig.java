package com.geekplus.maptest.map;

import com.geekplus.maptest.entity.MapCell;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**在面向对象的概念中，所有的对象都是通过类来描绘的，但是反过来，并不是所有的类都是用来描绘对象的，如果一个类中没有包含足够的信息来描绘一个具体的对象，这样的类就是抽象类。
 *抽象类除了不能实例化对象之外，类的其它功能依然存在，成员变量、成员方法和构造方法的访问方式和普通类一样。
 *由于抽象类不能实例化对象，所以抽象类必须被继承，才能被使用。也是因为这个原因，通常在设计阶段决定要不要设计抽象类。
 父类包含了子类集合的常见的方法，但是由于父类本身是抽象的，所以不能使用这些方法。
 *在Java中抽象类表示的是一种继承关系，一个类只能继承一个抽象类，而一个类却可以实现多个接口。
 * 当不确定抽象类具体什么动作时，定义抽象方法（不用谢函数体），抽象类不能实现抽象方法，但子类必须实现抽象方法，如果有抽象方法，那么改类必须是抽象类
 *
 * 与接口区别
 * 1抽象类要被子类继承，接口要被类实现
 * 2接口只能做方法声明，抽象类中可以作方法声明，也可以做方法实现
 *3 接口里定义的变量只能是公共的静态的常量，抽象类中的变量是普通变量
 * 4抽象类和接口都是用来抽象具体对象的，但是接口的抽象级别最高
 * 5抽象类可以有具体的方法和属性，接口只能有抽象方法和不可变常量
 * 6抽象类主要用来抽象类别，接口主要用来抽象功能
 */
@Component
@Getter
@Setter
public abstract class AbstractGetRobotConfig {
   public List<Map<String, String>> shelfList = new ArrayList<>();
    public <T extends MapCell> List<Map<String, String>> getShelfPlacements(List<T> list, String mode) {

        for (int j = 0; j < list.size(); j++) {
            // if (mode.equals("XML"))
            if (list.get(j).getCellType().equals("SHELF_CELL")) {
                if (mode.equals("xml")) {
                    //如果末尾数为0改成5
                    Map<String, String> index = list.get(j).getIndex();
                    Map<String, String> index1 = new HashMap<>();
                    System.out.println(index.get("x"));
                    if (isinteger(index.get("x").toString())) {


                        index1.put("x", index.get("x").toString() + ".5");

                    } else {

                        String x = index.get("x").toString().substring(0, index.get("x").length() - 1);

                        index1.put("x", x + "5");
                    }
                    //y
                    if (isinteger(index.get("y").toString())) {


                        index1.put("y", index.get("y").toString() + ".5");

                    } else {

                        String y = index.get("y").toString().substring(0, index.get("y").length() - 1);

                        index1.put("y", y + "5");
                    }


                    list.get(j).setIndex(index1);

                    shelfList.add(list.get(j).getIndex()) ;

                } else if (mode.equals("database") || mode.equals("fusion")) {
                    shelfList.add(list.get(j).getLocation());
                }
            }


        }

        return shelfList;
    }
//判断改字段是否是整数
    public boolean isinteger(String str) {
        //转义字符"\\."代表一个.
        if (str.split("\\.").length == 1) {

            return true;
        } else {
            return false;
        }
    }

}
