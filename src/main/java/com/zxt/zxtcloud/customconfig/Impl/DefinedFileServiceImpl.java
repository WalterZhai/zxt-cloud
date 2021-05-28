package com.zxt.zxtcloud.customconfig.Impl;

import com.zxt.zxtcloud.common.entity.TableEntity;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface DefinedFileServiceImpl {

    TableEntity definedFileTableData(String code, String name, Integer page, Integer limit);

    TableEntity definedFileDetailTableData(String selectid, Integer page, Integer limit);

}
