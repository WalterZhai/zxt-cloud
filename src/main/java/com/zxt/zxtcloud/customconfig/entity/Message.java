package com.zxt.zxtcloud.customconfig.entity;

import com.zxt.zxtcloud.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Entity
@Table(name="CCF_MESSAGE")
public class Message extends BaseEntity {

    @Column(name = "CODE",columnDefinition = "VARCHAR(30)  COMMENT '发送人编码'")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30)  COMMENT '发送人名称'")
    private String name;
    @Column(name = "TITLE",columnDefinition = "VARCHAR(100)  COMMENT '标题'")
    private String title;
    @Column(name = "CONTENT",columnDefinition = "VARCHAR(1000)  COMMENT '内容'")
    private String content;
    @Column(name = "IS_TYPE",columnDefinition = "decimal(1)  COMMENT '发送类型 0-所有用户；1-角色；2-指定用户；3-用户组'")
    private Integer isType;
    @Column(name = "IS_SEND",columnDefinition = "decimal(1)  COMMENT '是否已发送 0-未发送；1-已发送'")
    private Integer isSend;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsType() {
        return isType;
    }

    public void setIsType(Integer isType) {
        this.isType = isType;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }
}
