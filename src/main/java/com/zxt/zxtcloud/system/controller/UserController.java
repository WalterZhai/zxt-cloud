package com.zxt.zxtcloud.system.controller;

import com.alibaba.druid.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zxt.zxtcloud.basedata.bo.SimpleUserInfoBo;
import com.zxt.zxtcloud.basedata.entity.Employee;
import com.zxt.zxtcloud.basedata.repository.EmployeeRepository;
import com.zxt.zxtcloud.common.constant.SysConstant;
import com.zxt.zxtcloud.common.entity.JsonResult;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.exception.UnimaxException;
import com.zxt.zxtcloud.system.Impl.UserServiceImpl;
import com.zxt.zxtcloud.system.bo.SimpleUserBo;
import com.zxt.zxtcloud.system.entity.Role;
import com.zxt.zxtcloud.system.entity.User;
import com.zxt.zxtcloud.system.repository.RoleRepository;
import com.zxt.zxtcloud.system.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class UserController {

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping(value = "/user/onlineUserData")
    public TableEntity onlineUserData(HttpServletRequest request,String page,String limit) {
        TableEntity table;
        try{
            table = userServiceImpl.onlineUserData(Integer.parseInt(page),Integer.parseInt(limit));
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }


    @GetMapping(value = "/topage/user/loadUserInfo")
    public ModelAndView topageUserLoadUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".", "/");
        User user = userRepository.findUserByLoginNameAndIsDelete(username,0);
        ModelAndView modelAndView= new ModelAndView(url).addObject("user",user);
        return modelAndView;
    }

    @PostMapping(value = "/user/editBaseInfo")
    public JsonResult editBaseInfo(HttpServletRequest request) {
        String id = request.getParameter("id");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        try{
            User user = userRepository.findUserById(id);
            user.setEmail(email);
            user.setMobile(mobile);
            userRepository.save(user);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("修改失败"));
        }
    }

    @PostMapping(value = "/user/viewBaseInfo")
    public JsonResult viewBaseInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        try{
            Employee employee = employeeRepository.findEmployeeByCodeAndIsDelete(username,0);
            if(employee!=null){
                return new JsonResult(new SimpleUserInfoBo(employee));
            }else{
                return new JsonResult(new UnimaxException());
            }
        }catch (Exception e){
            return new JsonResult(new UnimaxException());
        }
    }

    @PostMapping(value = "/user/editPassword")
    public JsonResult editPassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");
        String pwd3 = request.getParameter("pwd3");
        try{
            userServiceImpl.editPassword(username,pwd1,pwd2,pwd3);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @GetMapping(value = "/user/userTableData")
    public TableEntity userTableData(HttpServletRequest request,String loginName,String name,Integer page,Integer limit) {
        TableEntity table;
        try{
            table = userServiceImpl.userTableData(loginName,name,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/user/lockUser")
    public JsonResult lockUser(HttpServletRequest request,String id,Integer isLocked) {
        try{
            userServiceImpl.updateIsLockedById(id,isLocked);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/addUser")
    public JsonResult addUser(HttpServletRequest request) {
        String name = request.getParameter("name");
        String loginName = request.getParameter("loginName");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        try{
            User user = new User();
            user.setLoginName(loginName);
            user.setPassword(SysConstant.PASSWORD);
            user.setName(name);
            user.setEmail(email);
            user.setMobile(mobile);
            user.setIsLocked(SysConstant.CONSTANT_ZERO);
            userRepository.save(user);
            return new JsonResult("添加成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/delUsers")
    public JsonResult delUsers(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<SimpleUserBo> list = JSON.parseArray(arrs,SimpleUserBo.class);
        try{
            List<User> users = new ArrayList<>();
            for(SimpleUserBo bo : list){
                if(SysConstant.ADMIN.equals(bo.getLoginName())){
                    throw new UnimaxException("admin用户无法删除！");
                }
                User user = userRepository.findUserById(bo.getId());
                user.setRoles(new HashSet<>());
                user.setGroups(new HashSet<>());
                user.setCollects(new HashSet<>());
                user.setIsDelete(1);
                users.add(user);
            }
            userRepository.saveAll(users);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    @PostMapping(value = "/user/editUser")
    public JsonResult editUser(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        try{
            User user = userRepository.findUserById(id);
            if(SysConstant.ADMIN.equals(user.getLoginName())){
                throw new UnimaxException("admin用户无法修改！");
            }
            user.setName(name);
            user.setEmail(email);
            user.setMobile(mobile);
            userRepository.save(user);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/delUser")
    public JsonResult delUser(HttpServletRequest request) {
        String obj = request.getParameter("obj");
        SimpleUserBo bo = JSON.parseObject(obj,SimpleUserBo.class);
        try{
            User user = userRepository.findUserById(bo.getId());
            if(SysConstant.ADMIN.equals(user.getLoginName())){
                throw new UnimaxException("admin用户无法删除 ！");
            }
            user.setRoles(new HashSet<>());
            user.setGroups(new HashSet<>());
            user.setCollects(new HashSet<>());
            user.setIsDelete(1);
            userRepository.save(user);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/ajaxLoadTransferUserRelRole")
    public JsonResult ajaxLoadTransferUserRelRole(HttpServletRequest request,String userid) {
        try{
            Map<String,Object> map = userServiceImpl.ajaxLoadTransferUserRelRole(userid);
            return new JsonResult(map);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/addSelectRole")
    public JsonResult addSelectRole(HttpServletRequest request,String userid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            User user = userRepository.findUserById(userid);
            for(int i=0;i<arr.size();i++){
                Role role = roleRepository.findRoleById(arr.getJSONObject(i).getString("value"));
                user.getRoles().add(role);
            }
            userRepository.save(user);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/delSelectRole")
    public JsonResult delSelectRole(HttpServletRequest request,String userid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            User user = userRepository.findUserById(userid);
            for(int i=0;i<arr.size();i++){
                Role role = roleRepository.findRoleById(arr.getJSONObject(i).getString("value"));
                user.getRoles().remove(role);
            }
            userRepository.save(user);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/uploadAvatar")
    public JsonResult uploadAvatar(HttpServletRequest request,@RequestParam("file") MultipartFile file) {
        String id = request.getParameter("id");
        Employee employee = employeeRepository.findEmployeeById(id);
        try{
            InputStream input = null;
            try {
                input = file.getInputStream();
                String path = SysConstant.ATTACHMENT_BASE_PATH + "avatar/" + employee.getCode();
                File filedir =new File(path);
                if  (!filedir .exists()  && !filedir .isDirectory()){
                    filedir .mkdirs();
                }
                path = path + "/avatar.jpg";
                File targetFile =  new File(path);
                input = file.getInputStream();
                FileUtils.copyInputStreamToFile(input, targetFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //释放
            file=null;
            return new JsonResult("上传成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    @PostMapping(value = "/user/loadAvatar")
    public JsonResult loadAvatar(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Employee employee = employeeRepository.findEmployeeById(id);
        FileInputStream in = null;

        try{
            String path = SysConstant.ATTACHMENT_BASE_PATH + "avatar/" + employee.getCode() + "/avatar.jpg";
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] b = new byte[in.available()];
            in.read(b);
            String base64Result = Base64.byteArrayToBase64(b);
            StringBuilder before = new StringBuilder("data:image/").append(file.getName()).append(";base64,");
            String img = before.append(base64Result).toString();
            return new JsonResult(img);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }finally {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
