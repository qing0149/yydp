package pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @ClassName Stu
 * @Description TODO
 * @Author qing
 * @Date 2023/2/25 10:29
 * @Version 1.0
 */
@Data
public class Stu {
    //    设置表头内容
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;

}
