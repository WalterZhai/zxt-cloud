package com.zxt.zxtcloud.system.Impl;

import com.alibaba.fastjson.JSONArray;
import com.zxt.zxtcloud.common.entity.TableEntity;

import java.util.Map;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface RoleServiceImpl {

    TableEntity roleTableData(String code, String name, Integer page, Integer limit);

    JSONArray ajaxLoadMenuTreeByRole(String roleid);

    void saveMenuSelectByRole(String id,JSONArray trees);

    Map<String,Object> ajaxLoadTransferRoleRelUser(String roleid);

}
