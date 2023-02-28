package pojo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName test
 * @Description TODO
 * @Author qing
 * @Date 2023/2/25 10:31
 * @Version 1.0
 */
public class WriteTest {
    private static List<Stu> data() {
        List<Stu> list = new ArrayList<Stu>();
        for (int i = 0; i < 10; i++) {
            Stu data = new Stu();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
    public static void main(String[] args) {
        String filename="E:\\a.xlsx";
        EasyExcel.write(filename, Stu.class).sheet("学生信息").doWrite(data());

    }
}
