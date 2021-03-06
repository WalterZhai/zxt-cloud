package com.zxt.zxtcloud.customconfig.repository;

import com.zxt.zxtcloud.customconfig.entity.DocuNum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface DocuNumRepository extends JpaRepository<DocuNum,String> {

    Page<DocuNum> findDocuNumsByIsDeleteAndCodeLikeAndNameLikeOrderByCreateDate(Integer isDelete, String code, String name, Pageable pageable);

    DocuNum findDocuNumById(String id);

    DocuNum findDocuNumsByIsDeleteAndCode(Integer isDelete, String code);
}
