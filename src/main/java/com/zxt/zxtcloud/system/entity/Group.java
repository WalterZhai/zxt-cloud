package com.zxt.zxtcloud.system.entity;


import com.zxt.zxtcloud.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Entity
@Table(name="SYS_GROUP")
public class Group extends BaseEntity {

    @Column(name = "CODE",columnDefinition = "VARCHAR(30) COMMENT '用户组编码'")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30) COMMENT '用户组名称'")
    private String name;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(200) COMMENT '用户组描述'")
    private String description;
    @ManyToMany(mappedBy = "groups",fetch= FetchType.EAGER)
    private Set<User> users = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
