package com.zxt.zxtcloud.common.entity;

import com.zxt.zxtcloud.common.jpa.DataBaseAuditListener;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@MappedSuperclass
@EntityListeners(DataBaseAuditListener.class)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8685228744146236922L;
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "gid",columnDefinition = "VARCHAR(32) COMMENT '主键' ")
    protected String id;
    @Column(
            name = "CREATE_DATE",
            updatable = false,
            nullable = false,
            columnDefinition = "datetime COMMENT '创建时间' "
    )
    protected Date createDate;
    @Column(
            name = "CREATE_ID",
            updatable = false,
            nullable = false,
            columnDefinition = "VARCHAR(32) COMMENT '创建人' "
    )
    protected String createId;
    @Column(
            name = "MODIFY_DATE",
            columnDefinition = "datetime COMMENT '修改时间' "
    )
    protected Date modifyDate;
    @Column(
            name = "MODIFY_ID",
            columnDefinition = "VARCHAR(32) COMMENT '修改人' "
    )
    protected String modifyId;
    @Column(
            name = "IS_DELETE",
            nullable = false,
            columnDefinition = "decimal(1) default 0 COMMENT '删除标识：0-未删除；1-删除' "
    )
    protected int isDelete;
    @Column(name = "UDA1",columnDefinition = "VARCHAR(100) COMMENT '备用1' ")
    protected String uda1;
    @Column(name = "UDA2",columnDefinition = "VARCHAR(100) COMMENT '备用2' ")
    protected String uda2;
    @Column(name = "UDA3",columnDefinition = "VARCHAR(100) COMMENT '备用3' ")
    protected String uda3;
    @Column(name = "UDA4",columnDefinition = "VARCHAR(100) COMMENT '备用4' ")
    protected String uda4;
    @Column(name = "UDA5",columnDefinition = "VARCHAR(100) COMMENT '备用5' ")
    protected String uda5;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getUda1() {
        return uda1;
    }

    public void setUda1(String uda1) {
        this.uda1 = uda1;
    }

    public String getUda2() {
        return uda2;
    }

    public void setUda2(String uda2) {
        this.uda2 = uda2;
    }

    public String getUda3() {
        return uda3;
    }

    public void setUda3(String uda3) {
        this.uda3 = uda3;
    }

    public String getUda4() {
        return uda4;
    }

    public void setUda4(String uda4) {
        this.uda4 = uda4;
    }

    public String getUda5() {
        return uda5;
    }

    public void setUda5(String uda5) {
        this.uda5 = uda5;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
