package com.zxt.zxtcloud.customconfig.entity;


import com.zxt.zxtcloud.common.entity.BaseEntity;

import javax.persistence.*;

/**
 * @comment 自定义档案明细
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */

@Entity
@Table(name="CCF_DEFINED_FILE_DETAIL")
public class DefinedFileDetail extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30) COMMENT '名称'")
    private String name;
    @Column(name = "CODE", columnDefinition = "VARCHAR(30) COMMENT '编码'")
    private String code;
    @Column(name = "SEQ",columnDefinition = "decimal(5) COMMENT '顺序'")
    private Integer seq;
    @Column(name = "IS_ACTIVE",columnDefinition = "decimal(1) COMMENT '是否激活：0-激活；1-未激活'")
    private Integer isActive;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID",columnDefinition = "VARCHAR(32) COMMENT '档案实体'")
    private DefinedFile parentDefinedFile;
    @Column(name = "VALUE",columnDefinition = "VARCHAR(500) COMMENT '档案值'")
    private String value;
    @Column(name = "REMARK",columnDefinition = "VARCHAR(200) COMMENT '备注'")
    private String remark;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public DefinedFile getParentDefinedFile() {
        return parentDefinedFile;
    }

    public void setParentDefinedFile(DefinedFile parentDefinedFile) {
        this.parentDefinedFile = parentDefinedFile;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
