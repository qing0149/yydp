package com.llkj.yydp.listener;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.llkj.yydp.mapper.DictMapper;
import com.llkj.yygh.model.cmn.Dict;
import com.llkj.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName DictLintenter
 * @Description TODO
 * @Author qing
 * @Date 2023/2/25 14:09
 * @Version 1.0
 */
@Component
public class DictListenter extends AnalysisEventListener<DictEeVo> {
    @Resource
    private DictMapper dictMapper;

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtil.copyProperties(dictEeVo, dict);
        dict.setIsDeleted(0);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
