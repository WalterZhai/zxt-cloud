package com.zxt.zxtcloud.system.repository;

import com.zxt.zxtcloud.system.entity.Role;
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
public interface RoleRepository extends JpaRepository<Role,String> {

    Role findRoleByCodeAndIsDelete(String code, Integer isDelete);

    List<Role> findRolesByIsDelete(Integer isDelete);

    Role findRoleById(String id);

    Page<Role> findRolesByIsDeleteAndCodeLikeAndNameLikeOrderByCode(Integer isDelete, String code, String name, Pageable pageable);

}
