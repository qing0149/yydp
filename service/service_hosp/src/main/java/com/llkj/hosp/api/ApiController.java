package com.llkj.hosp.api;


import com.llkj.hosp.service.DepartmentService;
import com.llkj.hosp.service.HospitalService;
import com.llkj.hosp.service.HospitalSetService;
import com.llkj.hosp.service.ScheduleService;
import com.llkj.hosp.utils.HttpRequestHelper;
import com.llkj.hosp.utils.MD5;
import com.llkj.yygh.common.Result;
import com.llkj.yygh.common.handler.LLKJException;
import com.llkj.yygh.model.hosp.Department;
import com.llkj.yygh.model.hosp.Hospital;
import com.llkj.yygh.model.hosp.Schedule;
import com.llkj.yygh.vo.hosp.DepartmentQueryVo;
import com.llkj.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName ApiContriller
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 9:37
 * @Version 1.0
 */
@Api(tags = "医院管理Api接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "上传医院")
    @PostMapping("saveHospital")
    public Result saveHospital(HttpServletRequest request) {
        //1从request里取出参数、转型
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        //2签名校验
        //2.1获取相关参数
        String hoscode = (String) paramMap.get("hoscode");
        String sign = (String) paramMap.get("sign");
        //2.2根据hoscode查询签名、MD5加密
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMd5 = MD5.encrypt(signKey);
        System.out.println("sign = " + sign);
        System.out.println("signKeyMd5 = " + signKeyMd5);
        //2.3校验签名
        if (!sign.equals(signKeyMd5)) {
            throw new LLKJException(20001, "签名校验失败");
        }

        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);

        //3调用方法保存数据
        hospitalService.saveHospital(paramMap);
        //4反馈结果
        return Result.ok();
    }

    @ApiOperation(value = "获取医院信息")
    @PostMapping("hospital/show")
    public Result hospital(HttpServletRequest request) {
        //1从request里取出参数、转型
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        //2取出相关参数
        String hoscode = (String) paramMap.get("hoscode");
        String sign = (String) paramMap.get("sign");
        //3签名校验（省略）
        //4调用方法查询医院信息
        Hospital hospital = hospitalService.getHosByHoscode(hoscode);
        //5返回查询结果
        return Result.ok(hospital);
    }


    @ApiOperation(value = "上传科室")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //1从request里取出参数、转型
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        //2签名校验（省略）
        //3调用方法上传科室
        departmentService.saveDepartment(paramMap);
        //4返回结果
        return Result.ok();
    }

    @ApiOperation(value = "获取分页列表")
    @PostMapping("department/list")
    public Result department(HttpServletRequest request) {
        //1从request里取出参数、转型
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        //2签名校验
        //2.1获取相关参数
        String hoscode = (String) paramMap.get("hoscode");
        String sign = (String) paramMap.get("sign");
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String) paramMap.get("limit"));
        Page<Department> pagModel = departmentService.selectPage(page, limit, departmentQueryVo);
        return Result.ok(pagModel);
    }

    @ApiOperation(value = "删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

    @ApiOperation(value = "上传排班")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        scheduleService.save(paramMap);
        return Result.ok();
    }

    //    查询排版信息
    @ApiOperation(value = "获取排班分页列表")
    @PostMapping("schedule/list")
    public Result schedule(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode((String) paramMap.get("hoscode"));
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));
        Page<Schedule> pageModel = scheduleService.selectPage(page, limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }

    //    删除排班信息
    @ApiOperation(value = "删除排班")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        //必填
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }


}
