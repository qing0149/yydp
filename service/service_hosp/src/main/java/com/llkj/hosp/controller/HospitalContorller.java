package com.llkj.hosp.controller;

import com.llkj.hosp.service.HospitalService;
import com.llkj.yygh.common.R;
import com.llkj.yygh.model.hosp.Hospital;
import com.llkj.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName HospitalContorller
 * @Description TODO
 * @Author qing
 * @Date 2023/3/1 9:22
 * @Version 1.0
 */
@Api(tags = "医院接口")
@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
public class HospitalContorller {
    @Autowired
    private HospitalService hospitalService;

    @ApiOperation("获取分页信息")
    @GetMapping("{page}/{limit}")
    public R index(@PathVariable Long page, @PathVariable Long limit, HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModell = hospitalService.selectPage(page, limit, hospitalQueryVo);
        return R.ok().data("pageModel", pageModell);
    }

    //    上线功能
    @ApiOperation(value = "更新上线状态")
    @GetMapping("updateStatus/{id}/{status}")
    public R updateStatus(
            @PathVariable("id") String id,
            @PathVariable("status") Integer status
    ) {
        hospitalService.updateStauts(id, status);
        return R.ok();
    }
    @ApiOperation(value = "获取医院详情")
    @GetMapping("show/{id}")
    public R show(
            @PathVariable String id) {
        Map<String,Object> map = hospitalService.show(id);
        return R.ok().data(map);
    }


}
