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
@Table(name="SYS_ROLE")
public class Role extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30) COMMENT '角色名称' ")
    private String name;
    @Column(name = "CODE",columnDefinition = "VARCHAR(30) COMMENT '角色编码，必须以ROLE_开头' ")
    private String code;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(200) COMMENT '角色用途说明' ")
    private String description;
    @ManyToMany(mappedBy = "roles",fetch=FetchType.EAGER)
    private Set<User> users = new HashSet<>();
    @ManyToMany(targetEntity = Menu.class, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "SYS_ROLE_MENU",joinColumns = @JoinColumn(name = "ROLE_ID"),inverseJoinColumns = @JoinColumn(name = "MENU_ID"))
    private Set<Menu> menus = new HashSet<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }
}
