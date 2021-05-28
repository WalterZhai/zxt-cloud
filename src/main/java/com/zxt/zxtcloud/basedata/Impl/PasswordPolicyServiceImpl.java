package com.zxt.zxtcloud.basedata.Impl;

import com.zxt.zxtcloud.basedata.entity.PasswordPolicy;
import com.zxt.zxtcloud.common.entity.TableEntity;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface PasswordPolicyServiceImpl {

    TableEntity passwordPolicyTableData(Integer page, Integer limit);

    void usedPasswordPolicy(PasswordPolicy passwordPolicy);
}
