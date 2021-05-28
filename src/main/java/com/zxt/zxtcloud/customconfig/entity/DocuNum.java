package com.zxt.zxtcloud.customconfig.entity;


import com.zxt.zxtcloud.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @comment 单据号管理
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */

@Entity
@Table(name="CCF_DOCU_NUM")
public class DocuNum extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30) COMMENT '名称'")
    private String name;
    @Column(name = "CODE", columnDefinition = "VARCHAR(30) COMMENT '编码'")
    private String code;
    @Column(name = "MIN",columnDefinition = "decimal(20) COMMENT '最小值'")
    private BigDecimal min;
    @Column(name = "MAX",columnDefinition = "decimal(20) COMMENT '最大值'")
    private BigDecimal max;
    @Column(name = "STEP",columnDefinition = "decimal(5) COMMENT '步长'")
    private BigDecimal step;
    @Column(name = "CUR",columnDefinition = "decimal(20) COMMENT '当前值'")
    private BigDecimal cur;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(200) COMMENT '说明'")
    private String description;

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

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getStep() {
        return step;
    }

    public void setStep(BigDecimal step) {
        this.step = step;
    }

    public BigDecimal getCur() {
        return cur;
    }

    public void setCur(BigDecimal cur) {
        this.cur = cur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
