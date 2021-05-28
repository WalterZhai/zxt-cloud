package com.zxt.zxtcloud.basedata.service;

import com.zxt.zxtcloud.basedata.Impl.EmployeeServiceImpl;
import com.zxt.zxtcloud.basedata.bo.SimpleEmployeeBo;
import com.zxt.zxtcloud.basedata.entity.Depart;
import com.zxt.zxtcloud.basedata.entity.Employee;
import com.zxt.zxtcloud.basedata.repository.DepartRepository;
import com.zxt.zxtcloud.basedata.repository.EmployeeRepository;
import com.zxt.zxtcloud.common.constant.SysConstant;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.utils.MathsUtils;
import com.zxt.zxtcloud.common.utils.StringUtils;
import com.zxt.zxtcloud.system.entity.User;
import com.zxt.zxtcloud.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Service
public class EmployeeService implements EmployeeServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartRepository departRepository;

    @Override
    public TableEntity employeeTableData(String code, String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Employee> pages = employeeRepository.findEmployeesByIsDeleteAndCodeLikeAndNameLike(0,StringUtils.string2LikeParam(code),StringUtils.string2LikeParam(name),pageable);
        List<Employee> list = pages.getContent();
        List<SimpleEmployeeBo> result = new ArrayList<>();
        for(Employee employee : list){
            result.add(new SimpleEmployeeBo(employee));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void genEmployees(List<SimpleEmployeeBo> list) {
        List<User> listU = new ArrayList<>();
        for(SimpleEmployeeBo emp : list){
            if(userRepository.findUserByLoginNameAndIsDelete(emp.getCode(),0)!=null){
                continue;
            }
            User u = new User();
            u.setName(emp.getName());
            u.setLoginName(emp.getCode());
            u.setPassword(SysConstant.PASSWORD);
            u.setIsLocked(0);
            u.setEmail(emp.getEmail());
            u.setMobile(emp.getMobile());
            listU.add(u);
        }
        userRepository.saveAll(listU);
    }

    @Override
    public TableEntity departEmpTableData(String id, Integer page, Integer limit) {
        Depart d = departRepository.findDepartById(id);
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Employee> pages = employeeRepository.findEmployeesByIsDeleteAndDepart(0,d,pageable);
        List<Employee> list = pages.getContent();
        List<SimpleEmployeeBo> result = new ArrayList<>();
        for(Employee employee : list){
            result.add(new SimpleEmployeeBo(employee));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }


}
