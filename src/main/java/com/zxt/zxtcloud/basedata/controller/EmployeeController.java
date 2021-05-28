package com.zxt.zxtcloud.basedata.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zxt.zxtcloud.basedata.Impl.EmployeeServiceImpl;
import com.zxt.zxtcloud.basedata.bo.SimpleEmployeeBo;
import com.zxt.zxtcloud.basedata.entity.Depart;
import com.zxt.zxtcloud.basedata.entity.Employee;
import com.zxt.zxtcloud.basedata.repository.EmployeeRepository;
import com.zxt.zxtcloud.common.constant.SysConstant;
import com.zxt.zxtcloud.common.entity.JsonResult;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.exception.UnimaxException;
import com.zxt.zxtcloud.common.utils.StringUtils;
import com.zxt.zxtcloud.common.utils.TimeUtils;
import com.zxt.zxtcloud.customconfig.Impl.DocuNumServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@RestController
public class EmployeeController {

    static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DocuNumServiceImpl docuNumServiceImpl;

    @GetMapping(value = "/employee/employeeTableData")
    public TableEntity employeeTableData(HttpServletRequest request, String code, String name, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = employeeServiceImpl.employeeTableData(code,name,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/employee/delEmployees")
    public JsonResult delEmployees(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<SimpleEmployeeBo> list = JSON.parseArray(arrs,SimpleEmployeeBo.class);
        try{
            List<Employee> employees = new ArrayList<>();
            for(SimpleEmployeeBo simpleEmployeeBo : list){
                Employee employee = employeeRepository.findEmployeeById(simpleEmployeeBo.getId());
                employee.setIsDelete(1);
                employees.add(employee);
            }
            employeeRepository.saveAll(employees);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }

    @PostMapping(value = "/employee/delEmployee")
    public JsonResult deleteEmployee(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            Employee employee = employeeRepository.findEmployeeById(id);
            employee.setIsDelete(1);
            employeeRepository.save(employee);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }

    @PostMapping(value = "/employee/genEmployees")
    public JsonResult genEmployees(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<SimpleEmployeeBo> list = JSON.parseArray(arrs,SimpleEmployeeBo.class);
        try{
            employeeServiceImpl.genEmployees(list);
            return new JsonResult("生成成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("生成失败"));
        }
    }

    @GetMapping(value = "/employee/departEmpTableData")
    public TableEntity departEmpTableData(HttpServletRequest request, String id, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = employeeServiceImpl.departEmpTableData(id,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }


    @GetMapping(value = "/topage/employeeEntity")
    public ModelAndView employeeEntity(HttpServletRequest request) {
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".", "/");
        String id = request.getParameter("id");
        Employee employee = employeeRepository.findEmployeeById(id);
        ModelAndView modelAndView= new ModelAndView(url).addObject("employee",new SimpleEmployeeBo(employee));
        return modelAndView;
    }

    @PostMapping(value = "/employee/addEmployee")
    public JsonResult addEmployee(HttpServletRequest request) {
        String name = request.getParameter("name");
        String usedName = request.getParameter("usedName");
        String sex = request.getParameter("sex");
        String idCardNum = request.getParameter("idCardNum");
        String socCardNum = request.getParameter("socCardNum");
        String birthday = request.getParameter("birthday");
        String address = request.getParameter("address");
        String zipcode = request.getParameter("zipcode");
        String officeTele = request.getParameter("officeTele");
        String homeTele = request.getParameter("homeTele");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String careerBeginDate = request.getParameter("careerBeginDate");
        String joinCompanyDate = request.getParameter("joinCompanyDate");
        String education = request.getParameter("education");
        String major = request.getParameter("major");
        String job = request.getParameter("job");
        String bankNumber = request.getParameter("bankNumber");
        String contractEndDate = request.getParameter("contractEndDate");
        String departId = request.getParameter("departId");
        String departName = request.getParameter("departName");
        String remark = request.getParameter("remark");
        try{
            Employee employee = new Employee();

            employee.setName(name);
            employee.setUsedName(usedName);
            employee.setSex(Integer.parseInt(sex));
            employee.setIdCardNum(idCardNum);
            employee.setSocCardNum(socCardNum);
            if(StringUtils.isNotEmpty(birthday)){
                employee.setBirthday(TimeUtils.convertShortStringToDate(birthday));
            }
            employee.setAddress(address);
            employee.setZipcode(zipcode);
            employee.setOfficeTele(officeTele);
            employee.setHomeTele(homeTele);
            employee.setMobile(mobile);
            employee.setEmail(email);
            if(StringUtils.isNotEmpty(careerBeginDate)){
                employee.setCareerBeginDate(TimeUtils.convertShortStringToDate(careerBeginDate));
            }
            if(StringUtils.isNotEmpty(joinCompanyDate)){
                employee.setJoinCompanyDate(TimeUtils.convertShortStringToDate(joinCompanyDate));
            }
            employee.setEducation(education);
            employee.setMajor(major);
            employee.setJob(job);
            employee.setBankNumber(bankNumber);
            if(StringUtils.isNotEmpty(contractEndDate)){
                employee.setContractEndDate(TimeUtils.convertShortStringToDate(contractEndDate));
            }
            employee.setRemark(remark);
            if(StrUtil.isNotEmpty(departId)){
                Depart d = new Depart();
                d.setId(departId);
                d.setName(departName);
                employee.setDepart(d);
            }

            employee.setCode(StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.EMPLOYEE_SEQ).getCur().toString(),5,"0"));
            employeeRepository.save(employee);
            return new JsonResult("添加成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("添加失败"));
        }
    }

    @PostMapping(value = "/employee/editEmployee")
    public JsonResult editEmployee(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String usedName = request.getParameter("usedName");
        String sex = request.getParameter("sex");
        String idCardNum = request.getParameter("idCardNum");
        String socCardNum = request.getParameter("socCardNum");
        String birthday = request.getParameter("birthday");
        String address = request.getParameter("address");
        String zipcode = request.getParameter("zipcode");
        String officeTele = request.getParameter("officeTele");
        String homeTele = request.getParameter("homeTele");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String careerBeginDate = request.getParameter("careerBeginDate");
        String joinCompanyDate = request.getParameter("joinCompanyDate");
        String education = request.getParameter("education");
        String major = request.getParameter("major");
        String job = request.getParameter("job");
        String bankNumber = request.getParameter("bankNumber");
        String contractEndDate = request.getParameter("contractEndDate");
        String departId = request.getParameter("departId");
        String departName = request.getParameter("departName");
        String remark = request.getParameter("remark");
        try{
            Employee employee = employeeRepository.findEmployeeById(id);

            employee.setName(name);
            employee.setUsedName(usedName);
            employee.setSex(Integer.parseInt(sex));
            employee.setIdCardNum(idCardNum);
            employee.setSocCardNum(socCardNum);
            if(StringUtils.isNotEmpty(birthday)){
                employee.setBirthday(TimeUtils.convertShortStringToDate(birthday));
            }
            employee.setAddress(address);
            employee.setZipcode(zipcode);
            employee.setOfficeTele(officeTele);
            employee.setHomeTele(homeTele);
            employee.setMobile(mobile);
            employee.setEmail(email);
            if(StringUtils.isNotEmpty(careerBeginDate)){
                employee.setCareerBeginDate(TimeUtils.convertShortStringToDate(careerBeginDate));
            }
            if(StringUtils.isNotEmpty(joinCompanyDate)){
                employee.setJoinCompanyDate(TimeUtils.convertShortStringToDate(joinCompanyDate));
            }
            employee.setEducation(education);
            employee.setMajor(major);
            employee.setJob(job);
            employee.setBankNumber(bankNumber);
            if(StringUtils.isNotEmpty(contractEndDate)){
                employee.setContractEndDate(TimeUtils.convertShortStringToDate(contractEndDate));
            }
            employee.setRemark(remark);
            if(StrUtil.isNotEmpty(departId)){
                Depart d = new Depart();
                d.setId(departId);
                d.setName(departName);
                employee.setDepart(d);
            }
            employeeRepository.save(employee);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("修改失败"));
        }
    }

}
