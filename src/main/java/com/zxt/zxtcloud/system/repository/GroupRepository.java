package com.zxt.zxtcloud.system.repository;

import com.zxt.zxtcloud.system.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Transactional
public interface GroupRepository extends JpaRepository<Group,String> {


    Group findGroupById(String id);

    Page<Group> findGroupsByIsDeleteAndCodeLikeAndNameLikeOrderByCode(Integer isDelete, String code, String name, Pageable pageable);

    List<Group> findGroupsByIsDelete(Integer isDelete);
}
