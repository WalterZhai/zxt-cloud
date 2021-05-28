package com.zxt.zxtcloud.system.Impl;

import com.zxt.zxtcloud.common.entity.TableEntity;

import java.util.Map;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface UserServiceImpl {

    TableEntity userTableData(String loginName, String name, Integer page, Integer limit);

    TableEntity onlineUserData(Integer page, Integer limit);

    void editPassword(String username,String pwd1,String pwd2,String pwd3);

    void updateIsLockedById(String id,Integer isLocked);

    Map<String,Object> ajaxLoadTransferUserRelRole(String userid);

}
