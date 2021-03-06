package com.zxt.zxtcloud.basedata.controller;

import com.alibaba.fastjson.JSONArray;
import com.zxt.zxtcloud.basedata.Impl.DepartServiceImpl;
import com.zxt.zxtcloud.basedata.bo.SimpleDepartBo;
import com.zxt.zxtcloud.basedata.entity.Depart;
import com.zxt.zxtcloud.basedata.entity.Employee;
import com.zxt.zxtcloud.basedata.repository.DepartRepository;
import com.zxt.zxtcloud.basedata.repository.EmployeeRepository;
import com.zxt.zxtcloud.common.constant.SysConstant;
import com.zxt.zxtcloud.common.distributedlock.CacheLock;
import com.zxt.zxtcloud.common.entity.JsonResult;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.exception.UnimaxException;
import com.zxt.zxtcloud.common.utils.StringUtils;
import com.zxt.zxtcloud.customconfig.Impl.DocuNumServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@RestController
public class DepartController {

    static final Logger logger = LoggerFactory.getLogger(DepartController.class);

    @Autowired
    private DepartServiceImpl departServiceImpl;

    @Autowired
    private DepartRepository departRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DocuNumServiceImpl docuNumServiceImpl;


    @PostMapping(value = "/depart/departAjaxLoadTree")
    public JsonResult departAjaxLoadTree(HttpServletRequest request) {
        try{
            JSONArray array = departServiceImpl.departAjaxLoadTree();
            return new JsonResult(array);
        }catch (Exception e){
            return new JsonResult(new UnimaxException("加载树形菜单失败!"));
        }
    }

    @GetMapping(value = "/depart/departTableData")
    public TableEntity departTableData(HttpServletRequest request, String id, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = departServiceImpl.departTableData(id,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @GetMapping(value = "/topage/departEntity")
    public ModelAndView employeeEntity(HttpServletRequest request) {
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".", "/");
        String id = request.getParameter("id");
        Depart depart = departRepository.findDepartById(id);
        ModelAndView modelAndView= new ModelAndView(url).addObject("depart",new SimpleDepartBo(depart));
        return modelAndView;
    }

    @CacheLock(prefix = "/depart/addDeaprt")
    @PostMapping(value = "/depart/addDeaprt")
    public JsonResult addDeaprt(HttpServletRequest request) {
        String parentId = request.getParameter("parentId");
        String name = request.getParameter("name");
        String commuAddress = request.getParameter("commuAddress");
        String telephone = request.getParameter("telephone");
        String fax = request.getParameter("fax");
        String remark = request.getParameter("remark");
        String supervisorId = request.getParameter("supervisorId");

        try{
            Depart parentDepart = departRepository.findDepartById(parentId);
            Depart depart = new Depart();
            if(parentDepart!=null){
                depart.setParentDepart(parentDepart);
            }
            depart.setCode(StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.DEPART_SEQ).getCur().toString(),8,"0"));
            depart.setName(name);
            depart.setCommuAddress(commuAddress);
            depart.setTelephone(telephone);
            depart.setFax(fax);
            depart.setRemark(remark);
            Employee employee = employeeRepository.findEmployeeById(supervisorId);
            depart.setSupervisor(employee);
            departRepository.save(depart);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/depart/editDeaprt")
    @PostMapping(value = "/depart/editDeaprt")
    public JsonResult editDeaprt(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String commuAddress = request.getParameter("commuAddress");
        String telephone = request.getParameter("telephone");
        String fax = request.getParameter("fax");
        String remark = request.getParameter("remark");
        String supervisorId = request.getParameter("supervisorId");

        try{
            Depart depart = departRepository.findDepartById(id);
            depart.setName(name);
            depart.setCommuAddress(commuAddress);
            depart.setTelephone(telephone);
            depart.setFax(fax);
            depart.setRemark(remark);
            Employee employee = employeeRepository.findEmployeeById(supervisorId);
            depart.setSupervisor(employee);
            departRepository.save(depart);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/depart/delDepart")
    @PostMapping(value = "/depart/delDepart")
    public JsonResult delDepart(HttpServletRequest request) {
        try{
            String id = request.getParameter("id");
            Depart depart = departRepository.findDepartById(id);
            if(depart.getChildDeparts().size()>0){
                throw new UnimaxException("存在子部门，无法删除！");
            }
            depart.setParentDepart(null);
            depart.setIsDelete(1);
            departRepository.save(depart);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/depart/ajaxSetPdepart")
    public JsonResult ajaxSetPdepart(HttpServletRequest request,String id,String selectid) {
        try{
            Depart d = departRepository.findDepartById(id);
            Depart p = departRepository.findDepartById(selectid);
            d.setParentDepart(p);
            departRepository.save(d);
            return new JsonResult("更换成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

}
