package com.llkj.yydp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llkj.yydp.listener.DictListenter;
import com.llkj.yydp.mapper.DictMapper;
import com.llkj.yydp.service.DictService;
import com.llkj.yygh.common.handler.LLKJException;
import com.llkj.yygh.model.cmn.Dict;
import com.llkj.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    //根据数据id查询子数据列表
    @Cacheable(value = "dict", key = "#root.methodName+#id")
    @Override
    public List<Dict> findChlidData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向list集合每个dict对象中设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
//        1.这只response的属性
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
//        2.调用方法查询所有字典数据
            String fileName = URLEncoder.encode("数据字典", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<Dict> dictList = baseMapper.selectList(null);
            List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
//        3.遍历集合，做类型转换
            dictList.stream().forEach((a) -> {
                DictEeVo dictEeVo = new DictEeVo();
                BeanUtil.copyProperties(a, dictEeVo);
                dictVoList.add(dictEeVo);
            });
//        4.调用工具到处文件
            ServletOutputStream outputStream = response.getOutputStream();
            EasyExcel.write(outputStream, DictEeVo.class).sheet("数据字典").doWrite(dictVoList);
        } catch (IOException e) {
            throw new LLKJException(20001, "到处文件失败");
        }
    }

    @Autowired
    private DictListenter dictListenter;

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, dictListenter).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new LLKJException(20001, "导入数据失败");
        }
    }

    //获取字典名称
    @Override
    public String getNameByParentDictCodeAndValue(String parentDictCode, String value) {
        //1判断是国标还是自定义
        if (StringUtils.isEmpty(parentDictCode)) {
//            获取国际数据
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict != null ? dict.getName() : "";
        } else {
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", parentDictCode);
            wrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict != null ? dict.getName() : "";
        }
    }


    @Override
    public List<Dict> findByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        dict = Optional.ofNullable(dict).get();
        wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", dict.getId());
        List<Dict> dicts = Optional.ofNullable(baseMapper.selectList(wrapper)).get();
        return dicts;
    }
    //根据dict_code查询
    private Dict getDictByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        return dict;
    }
//    根据dicCode查询字典数据

    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}