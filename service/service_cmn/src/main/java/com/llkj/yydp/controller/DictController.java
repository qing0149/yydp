package com.llkj.yydp.controller;

import com.llkj.yydp.service.DictService;
import com.llkj.yygh.common.R;
import com.llkj.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "导入")
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response){
        dictService.exportData(response);
    }
    @ApiOperation("导入")
    @PostMapping("importData")
    public R importData(MultipartFile file){
        dictService.importData(file);
        return R.ok();

    }
}
