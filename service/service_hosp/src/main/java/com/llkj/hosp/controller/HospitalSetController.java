package com.llkj.hosp.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llkj.hosp.service.HospitalSetService;
import com.llkj.yygh.common.R;
import com.llkj.yygh.model.hosp.HospitalSet;
import com.llkj.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HospitalSetController
 * @Description TODO
 * @Author qing
 * @Date 2023/2/20 10:44
 * @Version 1.0
 */
//医院设置接口
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
@Api(tags = "医院设置接口")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation("模拟登录")
    @PostMapping("login")
    public R login() {
//        返回报文
        return R.ok().data("token", "admin-token");
    }
    @ApiOperation("获取医院设置")
    @GetMapping("{id}")
    public R getHostSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return R.ok().data("item",hospitalSet);

    }

    @ApiOperation(value = "模拟用户获取信息")
    @GetMapping("info")
    public R info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "admin");
        map.put("introduction", "I am a super administrator");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        return R.ok().data(map);
    }

    //查询所有医院设置
    @GetMapping("findAll")
    @ApiOperation("医院设置列表")
    public R findAll() {
        List<HospitalSet> list = hospitalSetService.list();
        return R.ok().data("list", list);
    }

    @DeleteMapping("{id}")
    @ApiOperation("医院设置删除")
    public R removeById(@PathVariable String id) {
        boolean b = hospitalSetService.removeById(id);
        return b?R.ok():R.error();
    }

    /*
     * 分页查询医院设置
     * */
    @ApiOperation("分页查询医院设置")
    @GetMapping("{page}/{limit}")
    public R pageQuery(@PathVariable Long page, @PathVariable Long limit) {
        Page<HospitalSet> pageParam = new Page<>(page, limit);

        hospitalSetService.page(pageParam, null);
        List<HospitalSet> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "分页条件医院设置列表")
    @PostMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "hospitalSetQueryVo", value = "查询对象", required = false)
            @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> pageParam = new Page<>(page, limit);

        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();

        if (hospitalSetQueryVo == null) {
            hospitalSetService.page(pageParam, queryWrapper);
        } else {
            String hosname = hospitalSetQueryVo.getHosname();
            String hoscode = hospitalSetQueryVo.getHoscode();

            if (!StringUtils.isEmpty(hosname)) {
                queryWrapper.like("hosname", hosname);
            }

            if (!StringUtils.isEmpty(hoscode)) {
                queryWrapper.eq("hoscode", hoscode);
            }
            hospitalSetService.page(pageParam, queryWrapper);
        }
        List<HospitalSet> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增医院设置")
    @PostMapping("save")
    public R save(@RequestBody HospitalSet hospitalSet) {
        return hospitalSetService.save(hospitalSet) ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID修改医院设置")
    @PostMapping("{id}")
    public R updateById(@ApiParam(name = "hospitalSet", value = "医院设置对象", required = true)
                        @RequestBody HospitalSet hospitalSet) {
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    @ApiOperation(value = "根据ID批量删除")
    @PostMapping("batchRemove")
    public R batchRemove(@RequestBody List<Integer> ids) {
        hospitalSetService.removeByIds(ids);
        return R.ok();
    }

    @ApiOperation(value = "医院设置和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public R lockHosiatSet(@PathVariable Long id, @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }


}