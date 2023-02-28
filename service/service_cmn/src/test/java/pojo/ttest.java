package pojo;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Method;

/**
 * @ClassName ttest
 * @Description TODO
 * @Author qing
 * @Date 2023/2/25 16:56
 * @Version 1.0
 */
public class ttest {
    @Value("#root.methodName")
    public static void Ss(String id){
        System.out.println("hello world");
    }

    public static void main(String[] args) {
//        Value annotation = ttest.class.getAnnotation(Value.class);
//        System.out.println(annotation.value());
//        T annotation = Method.getAnnotation(ttest.class);
    }

}
