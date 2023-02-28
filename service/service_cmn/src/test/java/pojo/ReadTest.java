package pojo;

import com.alibaba.excel.EasyExcel;

/**
 * @ClassName ReadTest
 * @Description TODO
 * @Author qing
 * @Date 2023/2/25 10:43
 * @Version 1.0
 */
public class ReadTest {
    public static void main(String[] args) {
        String filename="E:\\a.xlsx";
        EasyExcel.read(filename,Stu.class,new ExcelListener()).sheet().doRead();

    }
}
