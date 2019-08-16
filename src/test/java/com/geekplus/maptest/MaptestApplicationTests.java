package com.geekplus.maptest;

import com.geekplus.maptest.entity.FloorsMapCell;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


@SpringBootTest(classes = {MaptestApplicationTests.class })
public class MaptestApplicationTests extends AbstractTestNGSpringContextTests {



@Test
public void master() throws NoSuchMethodException {

    FloorsMapCell a = new FloorsMapCell();
    Assertions.assertThat(2).as("检验是否相等").isEqualTo(2);
//获取类的方法getMethod，第一个参数方法名，第二个参数，方法的参数列表类型数组
    Method method = MaptestApplicationTests.class.getMethod("contextLoads", new Class[]{String.class});
    System.out.println(method.getName());
}
}
