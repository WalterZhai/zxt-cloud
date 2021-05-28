package com.zxt.zxtcloud.system.entity;


import com.zxt.zxtcloud.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Entity
@Table(name="SYS_MENU")
public class Menu extends BaseEntity {


    @Column(name = "CODE",columnDefinition = "VARCHAR(30) COMMENT '菜单编码' ")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30) COMMENT '菜单名称' ")
    private String name;
    @Column(name = "HREF",columnDefinition = "VARCHAR(300) COMMENT '菜单链接' ")
    private String href;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID",columnDefinition = "VARCHAR(32) COMMENT '父菜单' ")
    private Menu parentMenu;
    @Column(name = "SEQ",columnDefinition = "decimal(3) COMMENT '同父菜单顺序' ")
    private Integer seq;
    @Column(name = "TYPE",columnDefinition = "decimal(1) COMMENT '菜单类别：0-web；1-app' ")
    private Integer type;
    @Column(name = "ICON",columnDefinition = "VARCHAR(30) COMMENT '菜单图标' ")
    private String icon;
    @ManyToMany(mappedBy = "menus",fetch=FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "parentMenu", fetch = FetchType.EAGER)
    @OrderBy("seq ASC")
    private List<Menu> childMenus = new ArrayList<>();
    @ManyToMany(mappedBy = "collects",fetch=FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
