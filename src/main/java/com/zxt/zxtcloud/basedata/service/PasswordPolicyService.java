package com.zxt.zxtcloud.basedata.service;

import com.zxt.zxtcloud.basedata.Impl.PasswordPolicyServiceImpl;
import com.zxt.zxtcloud.basedata.entity.PasswordPolicy;
import com.zxt.zxtcloud.basedata.repository.PasswordPolicyRepository;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.utils.MathsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Service
public class PasswordPolicyService implements PasswordPolicyServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(PasswordPolicyService.class);

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    @Override
    public TableEntity passwordPolicyTableData(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<PasswordPolicy> pages = passwordPolicyRepository.findPasswordPoliciesByIsDelete(0,pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void usedPasswordPolicy(PasswordPolicy passwordPolicy) {
        List<PasswordPolicy> list = passwordPolicyRepository.findPasswordPoliciesByIsDelete(0);
        for(PasswordPolicy pp : list){
            if(passwordPolicy.equals(pp)){
                pp.setIsUsed(0);
            }else{
                pp.setIsUsed(1);
            }
        }
        passwordPolicyRepository.saveAll(list);
    }


}
