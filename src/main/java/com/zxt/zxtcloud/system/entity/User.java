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
@Table(name="SYS_USER")
public class User extends BaseEntity {


    @Column(name = "LOGIN_NAME",columnDefinition = "VARCHAR(30) COMMENT '登录名' ")
    private String loginName;
    @Column(name = "PASSWORD",columnDefinition = "VARCHAR(30) COMMENT '密码' ")
    private String password;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30) COMMENT '名称' ")
    private String name;
    @Column(name = "IS_LOCKED",columnDefinition = "decimal(1) default 0 COMMENT '锁定状态：0-未锁定；1-已锁定' ")
    private Integer isLocked;
    @Column(name = "EMAIL",columnDefinition = "VARCHAR(30) COMMENT '电子邮箱' ")
    private String email;
    @Column(name = "MOBILE",columnDefinition = "VARCHAR(20) COMMENT '手机登录' ")
    private String mobile;
    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "SYS_USER_ROLE",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<>();
    @ManyToMany(targetEntity = Group.class, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "SYS_USER_GROUP",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    private Set<Group> groups = new HashSet<>();
    @ManyToMany(targetEntity = Menu.class, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "SYS_USER_MENU",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "MENU_ID"))
    private Set<Menu> collects = new HashSet<>();

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Menu> getCollects() {
        return collects;
    }

    public void setCollects(Set<Menu> collects) {
        this.collects = collects;
    }
}
