package com.zxt.zxtcloud.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zxt.zxtcloud.common.constant.SysConstant;
import com.zxt.zxtcloud.common.distributedlock.CacheLock;
import com.zxt.zxtcloud.common.entity.JsonResult;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.exception.UnimaxException;
import com.zxt.zxtcloud.common.utils.StringUtils;
import com.zxt.zxtcloud.customconfig.Impl.DocuNumServiceImpl;
import com.zxt.zxtcloud.customconfig.entity.DocuNum;
import com.zxt.zxtcloud.system.Impl.RoleServiceImpl;
import com.zxt.zxtcloud.system.bo.SimpleRoleBo;
import com.zxt.zxtcloud.system.entity.Role;
import com.zxt.zxtcloud.system.entity.User;
import com.zxt.zxtcloud.system.repository.RoleRepository;
import com.zxt.zxtcloud.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class RoleController {

    static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocuNumServiceImpl docuNumServiceImpl;


    @GetMapping(value = "/role/roleTableData")
    public TableEntity roleTableData(HttpServletRequest request, String code, String name, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = roleServiceImpl.roleTableData(code,name,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @CacheLock(prefix = "/role/delRoles")
    @PostMapping(value = "/role/delRoles")
    public JsonResult delRoles(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<SimpleRoleBo> arr = JSON.parseArray(arrs,SimpleRoleBo.class);
        try{
            List<Role> list = new ArrayList<>();
            for(SimpleRoleBo bo : arr){
                if(SysConstant.SYSTEM_ROLE_NAME.equals(bo.getName())){
                    throw new UnimaxException("系统管理员角色无法删除!");
                }
                Role role = roleRepository.findRoleById(bo.getId());
                role.setMenus(new HashSet<>());
                //用户里面删除角色关系
                Set<User> users = role.getUsers();
                for(User user : users){
                    if(user.getRoles().contains(role)){
                        user.getRoles().remove(role);
                    }
                }
                userRepository.saveAll(users);
                role.setIsDelete(1);
                list.add(role);
            }
            roleRepository.saveAll(list);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/role/delRole")
    @PostMapping(value = "/role/delRole")
    public JsonResult delRole(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            Role role = roleRepository.findRoleById(id);
            if(SysConstant.SYSTEM_ROLE_NAME.equals(role.getName())){
                throw new UnimaxException("系统管理员角色无法删除!");
            }
            role.setMenus(new HashSet<>());
            //用户里面删除角色关系
            Set<User> users = role.getUsers();
            for(User user : users){
                if(user.getRoles().contains(role)){
                    user.getRoles().remove(role);
                }
            }
            userRepository.saveAll(users);
            role.setIsDelete(1);
            roleRepository.save(role);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException(ex.getMessage()));
        }
    }

    @CacheLock(prefix = "/role/addRole")
    @PostMapping(value = "/role/addRole")
    public JsonResult addRole(HttpServletRequest request,String name,String description) {
        try{
            Role role = new Role();
            DocuNum docuNum = docuNumServiceImpl.next(SysConstant.ROLE_SEQ);
            role.setCode(SysConstant.SYSTEM_ROLE_CODE_PREFIX + StringUtils.padLeft(docuNum.getCur().toString(),3,"0"));
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
            return new JsonResult("新增成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/role/editRole")
    @PostMapping(value = "/role/editRole")
    public JsonResult editRole(HttpServletRequest request,String id,String name,String description) {
        try{
            Role role = roleRepository.findRoleById(id);
            if(SysConstant.SYSTEM_ROLE_NAME.equals(role.getName())){
                throw new UnimaxException("系统管理员角色无法修改!");
            }
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/ajaxLoadMenuTreeByRole")
    public JsonResult ajaxLoadMenuTreeByRole(HttpServletRequest request,String roleid) {
        try{
            JSONArray array = roleServiceImpl.ajaxLoadMenuTreeByRole(roleid);
            return new JsonResult(array);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/saveMenuSelectByRole")
    public JsonResult saveMenuSelectByRole(HttpServletRequest request,String id,String treeData) {
        JSONArray trees = JSON.parseArray(treeData);
        try{
            roleServiceImpl.saveMenuSelectByRole(id,trees);
            return new JsonResult("保存成功!");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    @PostMapping(value = "/role/ajaxLoadTransferRoleRelUser")
    public JsonResult ajaxLoadTransferRoleRelUser(HttpServletRequest request,String roleid) {
        try{
            Map<String,Object> map = roleServiceImpl.ajaxLoadTransferRoleRelUser(roleid);
            return new JsonResult(map);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/addSelectUser")
    public JsonResult addSelectUser(HttpServletRequest request,String roleid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            Role role = roleRepository.findRoleById(roleid);
            for(int i=0;i<arr.size();i++){
                User user = userRepository.findUserById(arr.getJSONObject(i).getString("value"));
                user.getRoles().add(role);
                userRepository.save(user);
            }
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/delSelectUser")
    public JsonResult delSelectUser(HttpServletRequest request,String roleid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            Role role = roleRepository.findRoleById(roleid);
            for(int i=0;i<arr.size();i++){
                User user = userRepository.findUserById(arr.getJSONObject(i).getString("value"));
                user.getRoles().remove(role);
                userRepository.save(user);
            }
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

}
