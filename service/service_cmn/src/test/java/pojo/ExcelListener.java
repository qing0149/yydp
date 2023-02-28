package pojo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.Map;

/**
 * @ClassName ExcelListener
 * @Description TODO
 * @Author qing
 * @Date 2023/2/25 10:45
 * @Version 1.0
 */
//分析事件监听器
public class ExcelListener extends AnalysisEventListener<Stu> {

    @Override
    public void invoke(Stu stu, AnalysisContext analysisContext) {
        System.out.println("stu:" + stu);

    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("表头信息" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
