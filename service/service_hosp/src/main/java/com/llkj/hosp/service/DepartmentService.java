package com.llkj.hosp.service;

import org.springframework.data.domain.Page;
import com.llkj.yygh.model.hosp.Department;
import com.llkj.yygh.vo.hosp.DepartmentQueryVo;

import java.util.Map;

/**
 * @ClassName DepartService
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 12:51
 * @Version 1.0
 */
public interface DepartmentService {
    void saveDepartment(Map<String, Object> paramMap);

    Page<Department> selectPage(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);
}
