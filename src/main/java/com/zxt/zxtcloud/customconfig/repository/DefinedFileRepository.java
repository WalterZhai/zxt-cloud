package com.zxt.zxtcloud.customconfig.repository;

import com.zxt.zxtcloud.customconfig.entity.DefinedFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface DefinedFileRepository extends JpaRepository<DefinedFile,String> {

    Page<DefinedFile> findDefinedFilesByIsDeleteAndCodeLikeAndNameLikeOrderByCreateDateAsc(Integer isDelete, String code, String name, Pageable pageable);

    DefinedFile findDefinedFileById(String id);
}
