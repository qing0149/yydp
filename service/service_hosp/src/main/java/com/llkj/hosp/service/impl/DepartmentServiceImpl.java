package com.llkj.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import com.llkj.hosp.repository.DepartmentRepository;
import com.llkj.hosp.service.DepartmentService;
import com.llkj.yygh.model.hosp.Department;
import com.llkj.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName DepartmentServiceImpl
 * @Description TODO
 * @Author qing
 * @Date 2023/2/28 12:51
 * @Version 1.0
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void saveDepartment(Map<String, Object> paramMap) {
        String jsonString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(jsonString, Department.class);
//       根据hoscode,depcode铲鲟可是内容
        Department targetDepartment = departmentRepository
                .getByHoscodeAndDepcode(
                        department.getHoscode(),
                        department.getDepcode()
                );
        if (targetDepartment != null) {
            //3有，更新
            department.setId(targetDepartment.getId());
            department.setCreateTime(targetDepartment.getCreateTime());
            department.setUpdateTime(new Date());
            department.setIsDeleted(targetDepartment.getIsDeleted());
            departmentRepository.save(department);
        } else {
            //4没有，新增
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> selectPage(int page, int limit, DepartmentQueryVo departmentQueryVo) {
//        1.创建分页查询对象
        /*
         * 1,1创建排序对象
         * 1.2创建分页对象
         * 2.创建条件查询模板
         * 2.1封装查询查群条件到实体对象
         * 2.2创建薄板后娇妻
         * 2.3创建模板
         * */
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of((page - 1), limit, sort);
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
//        创建模板构造器
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase(true);
//        2.创建条件查询模板
        Example<Department> example = Example.of(department);
//        3.带条件带分页查询
        Page<Department> all = departmentRepository.findAll(example, pageable);

        return all;
    }

    //删除数据
    @Override
    public void remove(String hoscode, String depcode) {
        //1.先查询
        Department department = departmentRepository
                .getByHoscodeAndDepcode(
                        hoscode, depcode
                );
        //2.根据id删除
        if (department != null) {
            departmentRepository.deleteById(department.getId());
        }
    }
}
