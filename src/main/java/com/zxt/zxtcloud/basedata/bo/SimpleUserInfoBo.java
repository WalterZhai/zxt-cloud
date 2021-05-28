package com.zxt.zxtcloud.basedata.bo;

import com.zxt.zxtcloud.basedata.entity.Employee;
import com.zxt.zxtcloud.common.utils.TimeUtils;

import java.util.Date;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class SimpleUserInfoBo {

    private String code;
    private String name;
    private String usedName;
    private String sex;
    private String idCardNum;
    private String socCardNum;
    private String birthday;
    private String address;
    private String zipcode;
    private String officeTele;
    private String homeTele;
    private String mobile;
    private String email;
    private String careerBeginDate;
    private String joinCompanyDate;
    private String remark;
    private String departId;
    private String departName;
    private String education;
    private String major;
    private String job;
    private String bankNumber;
    private String contractEndDate;

    protected String id;
    protected Date createDate;
    protected String createId;
    protected Date modifyDate;
    protected String modifyId;
    protected int isDelete;
    protected String uda1;
    protected String uda2;
    protected String uda3;
    protected String uda4;
    protected String uda5;

    public SimpleUserInfoBo() {
    }

    public SimpleUserInfoBo(Employee employee) {

        this.code = employee.getCode();
        this.name = employee.getName();
        this.usedName = employee.getUsedName();
        if(employee.getSex()==0){
            this.sex = "男";
        }else{
            this.sex = "女";
        }
        this.idCardNum = employee.getIdCardNum();
        this.socCardNum = employee.getSocCardNum();
        this.birthday = employee.getBirthday()==null?"": TimeUtils.convertDateToShortString(employee.getBirthday());
        this.address = employee.getAddress();
        this.zipcode = employee.getZipcode();
        this.officeTele = employee.getOfficeTele();
        this.homeTele = employee.getHomeTele();
        this.mobile = employee.getMobile();
        this.email = employee.getEmail();
        this.careerBeginDate = employee.getCareerBeginDate()==null?"": TimeUtils.convertDateToShortString(employee.getCareerBeginDate());
        this.joinCompanyDate = employee.getJoinCompanyDate()==null?"": TimeUtils.convertDateToShortString(employee.getJoinCompanyDate());
        this.remark = employee.getRemark();
        this.departId = employee.getDepart()==null?"":employee.getDepart().getId();
        this.departName = employee.getDepart()==null?"":employee.getDepart().getName();
        this.education = employee.getEducation();
        this.major = employee.getMajor();
        this.job = employee.getJob();
        this.bankNumber = employee.getBankNumber();
        this.contractEndDate = employee.getContractEndDate()==null?"": TimeUtils.convertDateToShortString(employee.getContractEndDate());

        this.id = employee.getId();
        this.createDate = employee.getCreateDate();
        this.createId = employee.getCreateId();
        this.modifyDate = employee.getModifyDate();
        this.modifyId = employee.getModifyId();
        this.isDelete = employee.getIsDelete();
        this.uda1 = employee.getUda1();
        this.uda2 = employee.getUda2();
        this.uda3 = employee.getUda3();
        this.uda4 = employee.getUda4();
        this.uda5 = employee.getUda5();
    }


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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public String getCareerBeginDate() {
        return careerBeginDate;
    }

    public void setCareerBeginDate(String careerBeginDate) {
        this.careerBeginDate = careerBeginDate;
    }

    public String getJoinCompanyDate() {
        return joinCompanyDate;
    }

    public void setJoinCompanyDate(String joinCompanyDate) {
        this.joinCompanyDate = joinCompanyDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
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

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

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
}
