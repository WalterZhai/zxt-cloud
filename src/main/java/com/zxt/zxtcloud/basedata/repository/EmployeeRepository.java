package com.zxt.zxtcloud.basedata.repository;

import com.zxt.zxtcloud.basedata.entity.Depart;
import com.zxt.zxtcloud.basedata.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface EmployeeRepository extends JpaRepository<Employee,String> {

    Page<Employee> findEmployeesByIsDeleteAndCodeLikeAndNameLike(Integer isDelete, String code, String name, Pageable pageable);

    Page<Employee> findEmployeesByIsDeleteAndDepart(Integer isDelete, Depart d, Pageable pageable);

    List<Employee> findEmployeesByIsDelete(Integer isDelete);

    Employee findEmployeeById(String id);

    Employee findEmployeeByCodeAndIsDelete(String code, Integer isDelete);
}
