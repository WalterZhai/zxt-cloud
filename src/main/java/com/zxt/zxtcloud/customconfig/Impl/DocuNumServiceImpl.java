package com.zxt.zxtcloud.customconfig.Impl;

import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.customconfig.entity.DocuNum;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface DocuNumServiceImpl {

    TableEntity docuNumTableData(String code, String name, Integer page, Integer limit);

    DocuNum next(DocuNum docuNum);

    DocuNum next(String code);

}
