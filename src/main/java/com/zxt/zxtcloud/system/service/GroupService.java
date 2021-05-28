package com.zxt.zxtcloud.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.utils.MathsUtils;
import com.zxt.zxtcloud.common.utils.StringUtils;
import com.zxt.zxtcloud.system.Impl.GroupServiceImpl;
import com.zxt.zxtcloud.system.bo.SimpleGroupBo;
import com.zxt.zxtcloud.system.bo.SimpleUserBo;
import com.zxt.zxtcloud.system.entity.Group;
import com.zxt.zxtcloud.system.entity.User;
import com.zxt.zxtcloud.system.repository.GroupRepository;
import com.zxt.zxtcloud.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
public class GroupService implements GroupServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TableEntity roleTableData(String code, String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Group> pages = groupRepository.findGroupsByIsDeleteAndCodeLikeAndNameLikeOrderByCode(0, StringUtils.string2LikeParam(code),StringUtils.string2LikeParam(name),pageable);
        List<Group> list = pages.getContent();
        List<SimpleGroupBo> result = new ArrayList<>();
        for(Group group : list){
            result.add(new SimpleGroupBo(group));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public TableEntity groupDetailTableData(String selectid, Integer page, Integer limit) {
        Group group = groupRepository.findGroupById(selectid);
        if(group!=null){
            Set<User> users = group.getUsers();
            List<SimpleUserBo> result = new ArrayList<>();
            Integer begin = (page-1)*limit;
            Integer end = page*limit;
            if(end>users.size()){
                end = users.size();
            }
            int i = 0;
            for (User user : users) {
                if (i >= begin && i < end) {
                    result.add(new SimpleUserBo(user));
                }
                i++;
            }
            return new TableEntity(result, MathsUtils.convertInteger2BigDecimal(users.size()));
        }else{
            return new TableEntity(new ArrayList<>(), BigDecimal.ZERO);
        }
    }

    @Override
    public Map<String, Object> ajaxLoadTransferGroupRelUser(String groupid) {
        Group group = groupRepository.findGroupById(groupid);
        Map<String, Object> result = new HashMap<>();

        //已属于用户组的用户
        Set<User> userExist = group.getUsers();
        List<User> userAll = userRepository.findUsersByIsDelete(0);
        //data值
        JSONArray arr = new JSONArray();
        for(User user : userAll){
            JSONObject o = new JSONObject();
            o.put("value",user.getId());
            o.put("title",user.getName());
            o.put("disabled","");
            o.put("checked","");
            arr.add(o);
        }
        result.put("data",arr);
        //value值
        List<String> listids = new ArrayList<>();
        for(User user : userExist){
            listids.add(user.getId());
        }
        result.put("value",listids);
        return result;
    }
}
