package com.zxt.zxtcloud.basedata.entity;


import com.zxt.zxtcloud.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="BD_EMPLOYEE")
public class Employee extends BaseEntity {

    @Column(name = "CODE",columnDefinition = "VARCHAR(30) COMMENT '人员编码'")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30) COMMENT '姓名'")
    private String name;
    @Column(name = "USED_NAME",columnDefinition = "VARCHAR(30) COMMENT '曾用名'")
    private String usedName;
    @Column(name = "SEX",columnDefinition = "decimal(1) COMMENT '性别 0:男, 1:女'")
    private Integer sex;
    @Column(name = "ID_CARD_NUM",columnDefinition = "VARCHAR(30) COMMENT '身份证号'")
    private String idCardNum;
    @Column(name = "SOC_CARD_NUM",columnDefinition = "VARCHAR(30) COMMENT '社会保障号'")
    private String socCardNum;
    @Column(name = "BIRTHDAY",columnDefinition = "datetime COMMENT '出生日期'")
    private Date birthday;
    @Column(name = "ADDRESS",columnDefinition = "VARCHAR(300) COMMENT '家庭地址'")
    private String address;
    @Column(name = "ZIPCODE",columnDefinition = "VARCHAR(30) COMMENT '邮政编码'")
    private String zipcode;
    @Column(name = "OFFICE_TELE",columnDefinition = "VARCHAR(30) COMMENT '办公电话'")
    private String officeTele;
    @Column(name = "HOME_TELE",columnDefinition = "VARCHAR(30) COMMENT '家庭电话'")
    private String homeTele;
    @Column(name = "MOBILE",columnDefinition = "VARCHAR(30) COMMENT '手机号码'")
    private String mobile;
    @Column(name = "EMAIL",columnDefinition = "VARCHAR(100) COMMENT 'email'")
    private String email;
    @Column(name = "CAREER_BEGIN_DATE",columnDefinition = "datetime COMMENT '参加工作日期'")
    private Date careerBeginDate;
    @Column(name = "JOIN_COMPANY_DATE",columnDefinition = "datetime COMMENT '入职日期'")
    private Date joinCompanyDate;
    @Column(name = "REMARK",columnDefinition = "VARCHAR(150) COMMENT '备注'")
    private String remark;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPART_ID",columnDefinition = "VARCHAR(32) COMMENT '部门实体'")
    private Depart depart;
    @Column(name = "EDUCATION",columnDefinition = "VARCHAR(30) COMMENT '学历'")
    private String education;
    @Column(name = "MAJOR",columnDefinition = "VARCHAR(30) COMMENT '专业'")
    private String major;
    @Column(name = "JOB",columnDefinition = "VARCHAR(30) COMMENT '职务'")
    private String job;
    @Column(name = "BANK_NUMBER",columnDefinition = "VARCHAR(50) COMMENT '银行卡号'")
    private String bankNumber;
    @Column(name = "CONTRACT_END_DATE",columnDefinition = "datetime COMMENT '合同截止日期'")
    private Date contractEndDate;

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

    public String getUsedName() {
        return usedName;
    }

    public void setUsedName(String usedName) {
        this.usedName = usedName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getSocCardNum() {
        return socCardNum;
    }

    public void setSocCardNum(String socCardNum) {
        this.socCardNum = socCardNum;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOfficeTele() {
        return officeTele;
    }

    public void setOfficeTele(String officeTele) {
        this.officeTele = officeTele;
    }

    public String getHomeTele() {
        return homeTele;
    }

    public void setHomeTele(String homeTele) {
        this.homeTele = homeTele;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCareerBeginDate() {
        return careerBeginDate;
    }

    public void setCareerBeginDate(Date careerBeginDate) {
        this.careerBeginDate = careerBeginDate;
    }

    public Date getJoinCompanyDate() {
        return joinCompanyDate;
    }

    public void setJoinCompanyDate(Date joinCompanyDate) {
        this.joinCompanyDate = joinCompanyDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }
}
