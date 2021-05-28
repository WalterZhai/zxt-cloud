package com.zxt.zxtcloud.customconfig.Impl;

import com.alibaba.fastjson.JSONArray;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.customconfig.entity.Message;

import java.util.List;
import java.util.Map;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface MessageServiceImpl {

    TableEntity messageTableData(String title, Integer type, Integer isSend, Integer page, Integer limit);

    Map<String,Object> laodTrans();

    void addMessageInfo(String title, String content, Integer type, JSONArray arr, String username);

    void editMessage(String id, String title, String content);

    void delMessages(List<Message> list);

    void delMessage(Message message);

    void sendMessage(List<Message> list);

    void cancelMessage(List<Message> list);

    Integer inspectMessage(String username);

    TableEntity tableDataMessageInfo(String username, Integer page, Integer limit);

    void messageRead(String id);

    void messageReadAll(String username);

    void messageFeedback(String username, String feedback);

    TableEntity messageUserTableData(String id, String code, Integer page, Integer limit);

}
