package com.zxt.zxtcloud.common.init;

import com.zxt.zxtcloud.basedata.entity.PasswordPolicy;
import com.zxt.zxtcloud.basedata.entity.SystemParams;
import com.zxt.zxtcloud.basedata.repository.PasswordPolicyRepository;
import com.zxt.zxtcloud.basedata.repository.SystemParamsRepository;
import com.zxt.zxtcloud.common.constant.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
@Transactional
public class InitBasedataService {

    static final Logger logger = LoggerFactory.getLogger(InitBasedataService.class);

    @Autowired
    private SystemParamsRepository systemParamsRepository;

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;


    /**
     * @comment 初始化系统参数
     * @author Walter(翟笑天)
     * @date 2021/3/27
     */
    public void initSystemParams(){
        SystemParams unimaxParams = systemParamsRepository.findSystemParamsByIsDeleteAndCode(0,SysConstant.PARAMS_UNIMAX);
        if(unimaxParams==null){
            List<SystemParams> list = new ArrayList<>();
            //平台版本号
            SystemParams systemParams = new SystemParams();
            systemParams.setCode("UNIMAX_VERSION");
            systemParams.setName("平台版本号");
            systemParams.setValue("2.1.4");
            systemParams.setDescription("平台版本号");
            systemParams.setCreateId("admin");
            systemParams.setModifyId("admin");
            list.add(systemParams);
            //数据库版本
            systemParams = new SystemParams();
            systemParams.setCode("DB_VERSION");
            systemParams.setName("数据库版本");
            systemParams.setValue("MySQL 5.7.31");
            systemParams.setDescription("数据库版本");
            systemParams.setCreateId("admin");
            systemParams.setModifyId("admin");
            list.add(systemParams);
            //JAVA版本
            systemParams = new SystemParams();
            systemParams.setCode("JAVA_VERSION");
            systemParams.setName("JAVA版本");
            systemParams.setValue("1.8");
            systemParams.setDescription("JAVA版本");
            systemParams.setCreateId("admin");
            systemParams.setModifyId("admin");
            list.add(systemParams);
            //SPRING BOOT版本号
            systemParams = new SystemParams();
            systemParams.setCode("SPRING_BOOT");
            systemParams.setName("SPRING BOOT版本号");
            systemParams.setValue("2.1.0.RELEASE");
            systemParams.setDescription("SPRING BOOT版本号");
            systemParams.setCreateId("admin");
            systemParams.setModifyId("admin");
            list.add(systemParams);
            systemParamsRepository.saveAll(list);
        }

        List<PasswordPolicy> list = passwordPolicyRepository.findPasswordPoliciesByIsDelete(0);
        if(list==null || list.size()==0){
            PasswordPolicy passwordPolicy = new PasswordPolicy();
            passwordPolicy.setName("简单策略");
            passwordPolicy.setValue("^[a-zA-Z0-9]+$");
            passwordPolicy.setDescription("一个或多个字符或数字。");
            passwordPolicy.setIsUsed(0);
            passwordPolicy.setCreateId("admin");
            passwordPolicy.setModifyId("admin");
            passwordPolicyRepository.save(passwordPolicy);
            passwordPolicy = new PasswordPolicy();
            passwordPolicy.setName("复杂策略");
            passwordPolicy.setValue("^[a-zA-Z]+[0-9]+$");
            passwordPolicy.setDescription("以字符开头，数值结尾。");
            passwordPolicy.setIsUsed(1);
            passwordPolicy.setCreateId("admin");
            passwordPolicy.setModifyId("admin");
            passwordPolicyRepository.save(passwordPolicy);
        }

    }


}
