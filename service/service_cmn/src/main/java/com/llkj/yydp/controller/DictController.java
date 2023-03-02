package com.llkj.yydp.controller;

import com.llkj.yydp.service.DictService;
import com.llkj.yygh.common.R;
import com.llkj.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName DictController
 * @Description TODO
 * @Author qing
 * @Date 2023/2/24 16:13
 * @Version 1.0
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public R findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChlidData(id);
        return R.ok().data("list", list);
    }

    @ApiOperation(value = "导出")
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        dictService.exportData(response);
    }

    @ApiOperation("导入")
    @PostMapping("importData")
    public R importData(MultipartFile file) {
        dictService.importData(file);
        return R.ok();

    }


    @ApiOperation(value = "获取国际数据字典")
    @GetMapping(value = "/getName/{parentDictCode}/{value}")
    public String getName(
            @ApiParam(name = "parentDictCode", value = "上级编码", required = true)
            @PathVariable("parentDictCode") String parentDictCode,
            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value) {
        return dictService.getNameByParentDictCodeAndValue(parentDictCode, value);
    }

    @ApiOperation(value = "获取自定义数据")
    @GetMapping(value = "/getName/{value}")
    public String getName(
            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value) {
        return dictService.getNameByParentDictCodeAndValue("", value);
    }

    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping(value = "/findByDictCode/{dictCode}")
    public R findByDictCode(@PathVariable(value = "dictCode") String dictCode) {
        List<Dict> dictList = dictService.findByDictCode(dictCode);
        return R.ok().data("list", dictList);
    }
}
