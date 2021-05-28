package com.zxt.zxtcloud.common.init;

import com.zxt.zxtcloud.common.constant.SysConstant;
import com.zxt.zxtcloud.customconfig.entity.DocuNum;
import com.zxt.zxtcloud.customconfig.repository.DocuNumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
@Transactional
public class InitCustomConfigService {

    static final Logger logger = LoggerFactory.getLogger(InitCustomConfigService.class);

    @Autowired
    private DocuNumRepository docuNumRepository;

    /**
     * @comment 初始化单据号
     * @author Walter(翟笑天)
     * @date 2021/3/27
     */
    public void initDocuNum(){
        DocuNum docuNum1 = docuNumRepository.findDocuNumsByIsDeleteAndCode(0,SysConstant.ROLE_SEQ);
        if(docuNum1==null){
            docuNum1 = new DocuNum();
            docuNum1.setName("角色编码序列");
            docuNum1.setCode(SysConstant.ROLE_SEQ);
            docuNum1.setMin(BigDecimal.ZERO);
            docuNum1.setStep(BigDecimal.ONE);
            docuNum1.setMax(new BigDecimal(SysConstant.DOCUNUM_MAX_VALUE));
            docuNum1.setCur(BigDecimal.ZERO);
            docuNum1.setDescription("角色编码申请自动递增序列");
            docuNum1.setCreateId("admin");
            docuNum1.setModifyId("admin");
            docuNumRepository.save(docuNum1);
        }
        DocuNum docuNum2 = docuNumRepository.findDocuNumsByIsDeleteAndCode(0,SysConstant.MENU_SEQ);
        if(docuNum2==null){
            docuNum2 = new DocuNum();
            docuNum2.setName("菜单编码序列");
            docuNum2.setCode(SysConstant.MENU_SEQ);
            docuNum2.setMin(BigDecimal.ZERO);
            docuNum2.setStep(BigDecimal.ONE);
            docuNum2.setMax(new BigDecimal(SysConstant.DOCUNUM_MAX_VALUE));
            docuNum2.setCur(BigDecimal.ZERO);
            docuNum2.setDescription("菜单编码申请自动递增序列");
            docuNum2.setCreateId("admin");
            docuNum2.setModifyId("admin");
            docuNumRepository.save(docuNum2);
        }
        DocuNum docuNum3 = docuNumRepository.findDocuNumsByIsDeleteAndCode(0,SysConstant.GROUP_SEQ);
        if(docuNum3==null){
            docuNum3 = new DocuNum();
            docuNum3.setName("用户组编码序列");
            docuNum3.setCode(SysConstant.GROUP_SEQ);
            docuNum3.setMin(BigDecimal.ZERO);
            docuNum3.setStep(BigDecimal.ONE);
            docuNum3.setMax(new BigDecimal(SysConstant.DOCUNUM_MAX_VALUE));
            docuNum3.setCur(BigDecimal.ZERO);
            docuNum3.setDescription("用户组编码申请自动递增序列");
            docuNum3.setCreateId("admin");
            docuNum3.setModifyId("admin");
            docuNumRepository.save(docuNum3);
        }
        DocuNum docuNum4 = docuNumRepository.findDocuNumsByIsDeleteAndCode(0,SysConstant.EMPLOYEE_SEQ);
        if(docuNum4==null){
            docuNum4 = new DocuNum();
            docuNum4.setName("员工编码序列");
            docuNum4.setCode(SysConstant.EMPLOYEE_SEQ);
            docuNum4.setMin(BigDecimal.ZERO);
            docuNum4.setStep(BigDecimal.ONE);
            docuNum4.setMax(new BigDecimal(SysConstant.DOCUNUM_MAX_VALUE));
            docuNum4.setCur(BigDecimal.ZERO);
            docuNum4.setDescription("员工编码申请自动递增序列");
            docuNum4.setCreateId("admin");
            docuNum4.setModifyId("admin");
            docuNumRepository.save(docuNum4);
        }
        DocuNum docuNum5 = docuNumRepository.findDocuNumsByIsDeleteAndCode(0,SysConstant.DEPART_SEQ);
        if(docuNum5==null){
            docuNum5 = new DocuNum();
            docuNum5.setName("部门编码序列");
            docuNum5.setCode(SysConstant.DEPART_SEQ);
            docuNum5.setMin(BigDecimal.ZERO);
            docuNum5.setStep(BigDecimal.ONE);
            docuNum5.setMax(new BigDecimal(SysConstant.DOCUNUM_MAX_VALUE));
            docuNum5.setCur(BigDecimal.ZERO);
            docuNum5.setDescription("部门编码申请自动递增序列");
            docuNum5.setCreateId("admin");
            docuNum5.setModifyId("admin");
            docuNumRepository.save(docuNum5);
        }

    }


}
