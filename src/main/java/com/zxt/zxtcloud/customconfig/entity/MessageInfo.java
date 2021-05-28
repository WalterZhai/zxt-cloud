package com.zxt.zxtcloud.customconfig.entity;

import com.zxt.zxtcloud.common.entity.BaseEntity;

import javax.persistence.*;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Entity
@Table(name="CCF_MESSAGE_INFO")
public class MessageInfo extends BaseEntity {

    @Column(name = "USER_CODE",columnDefinition = "VARCHAR(20)  COMMENT '用户编码'")
    private String userCode;
    @Column(name = "IS_READ",columnDefinition = "decimal(1)  COMMENT '是否已读 0-未读；1-已读'")
    private Integer isRead;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID",columnDefinition = "VARCHAR(32)  COMMENT '信息实体'")
    private Message message;
    @Column(name = "IS_SEND",columnDefinition = "decimal(1)  COMMENT '是否已发送 0-未发送；1-已发送'")
    private Integer isSend;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }
}
