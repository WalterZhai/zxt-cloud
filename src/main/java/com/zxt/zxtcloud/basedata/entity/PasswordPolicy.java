package com.zxt.zxtcloud.basedata.entity;


import com.zxt.zxtcloud.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @comment 密码策略
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */
@Entity
@Table(name="BD_PASSWORD_POLICY")
public class PasswordPolicy extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30) COMMENT '策略名称'")
    private String name;
    @Column(name = "VALUE",columnDefinition = "VARCHAR(200) COMMENT '正则值'")
    private String value;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(200) COMMENT '策略说明'")
    private String description;
    @Column(name = "IS_USED",columnDefinition = "decimal(1)  COMMENT '0-正在使用；1-未使用'")
    private Integer isUsed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
}
