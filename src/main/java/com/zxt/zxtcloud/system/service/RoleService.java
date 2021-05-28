package com.zxt.zxtcloud.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.utils.MathsUtils;
import com.zxt.zxtcloud.common.utils.StringUtils;
import com.zxt.zxtcloud.system.Impl.RoleServiceImpl;
import com.zxt.zxtcloud.system.bo.SimpleRoleBo;
import com.zxt.zxtcloud.system.entity.Menu;
import com.zxt.zxtcloud.system.entity.Role;
import com.zxt.zxtcloud.system.entity.User;
import com.zxt.zxtcloud.system.repository.MenuRepository;
import com.zxt.zxtcloud.system.repository.RoleRepository;
import com.zxt.zxtcloud.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
public class RoleService implements RoleServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;


    @Override
    public TableEntity roleTableData(String code, String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Role> pages = roleRepository.findRolesByIsDeleteAndCodeLikeAndNameLikeOrderByCode(0, StringUtils.string2LikeParam(code),StringUtils.string2LikeParam(name),pageable);
        List<Role> list = pages.getContent();
        List<SimpleRoleBo> result = new ArrayList<>();
        for(Role role : list){
            result.add(new SimpleRoleBo(role));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public JSONArray ajaxLoadMenuTreeByRole(String roleid) {
        Role role = roleRepository.findRoleById(roleid);
        //角色下已有的菜单
        Set<Menu> menuExist = role.getMenus();
        //所有顶层菜单
        List<Menu> menus =  menuRepository.findMenusByIsDeleteAndParentMenuOrderBySeq(0,null);

        //最终返回
        JSONArray result = new JSONArray();
        //虚拟顶层
        JSONObject virtualTop = new JSONObject();
        virtualTop.put("title","<h3><i class='layui-icon layui-icon-align-left'>&nbsp;</i>菜单树</h3>");
        virtualTop.put("id","");
        virtualTop.put("spread",true);
        //实际菜单层级
        JSONArray arr = new JSONArray();
        for(Menu menu : menus){
            JSONObject object = recursiveModel(menu,menuExist);
            arr.add(object);
        }
        virtualTop.put("children",arr);
        result.add(virtualTop);
        return result;
    }

    //递归建模
    public JSONObject recursiveModel(Menu menu,Set<Menu> menuExist){
        JSONObject object = new JSONObject();
        object.put("title",menu.getName());
        object.put("id",menu.getId());
        object.put("spread",true);
        if(menu.getChildMenus().size()>0){
            JSONArray children = new JSONArray();
            for(Menu child : menu.getChildMenus()){
                JSONObject obj = recursiveModel(child,menuExist);
                children.add(obj);
            }
            object.put("children",children);
        }else{
            if(menuExist.contains(menu)){
                object.put("checked",true);
            }
        }
        return object;
    }

    @Override
    public void saveMenuSelectByRole(String id, JSONArray trees) {
        Role role = roleRepository.findRoleById(id);
        List<String> listids = new ArrayList<>();
        listids = getIdIntoListMenuids(trees, listids);
        Set<Menu> menus = menuRepository.findMenusByIsDeleteAndIdIn(0,listids);
        role.setMenus(menus);
        roleRepository.save(role);
    }

    public List<String> getIdIntoListMenuids(JSONArray data,List<String> listids){
        for(int i=0;i<data.size();i++) {
            JSONObject object = data.getJSONObject(i);
            //有id加入list
            if(!StringUtils.isEmpty(object.getString("id"))){
                listids.add(object.getString("id"));
            }
            //有子菜单递归
            JSONArray children = (JSONArray) data.getJSONObject(i).get("children");
            if(children!=null && children.size()>0){
                getIdIntoListMenuids(children,listids);
            }
        }
        return listids;
    }

    @Override
    public Map<String, Object> ajaxLoadTransferRoleRelUser(String roleid) {
        Role role = roleRepository.findRoleById(roleid);
        //结果
        Map<String, Object> result = new HashMap<>();
        //角色下用户
        Set<User> userExist = role.getUsers();
        List<User> userAll = userRepository.findUsersByIsDelete(0);
        //data值
        JSONArray arr = new JSONArray();
        for(User r : userAll){
            JSONObject o = new JSONObject();
            o.put("value",r.getId());
            o.put("title",r.getName());
            o.put("disabled","");
            o.put("checked","");
            arr.add(o);
        }
        result.put("data",arr);
        //value值
        List<String> listids = new ArrayList<String>();
        for(User r : userExist){
            listids.add(r.getId());
        }
        result.put("value",listids);
        return result;
    }


}
