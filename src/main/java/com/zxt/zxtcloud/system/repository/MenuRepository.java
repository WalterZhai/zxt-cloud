package com.zxt.zxtcloud.system.repository;

import com.zxt.zxtcloud.system.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Transactional
public interface MenuRepository extends JpaRepository<Menu,String> {

    Menu findMenuByCodeAndIsDelete(String code,Integer isDelete);

    List<Menu> findMenusByIsDeleteAndParentMenuOrderBySeq(Integer isDelete, Menu menu);

    Set<Menu> findMenusByIsDeleteAndIdIn(Integer isDelete, List<String> ids);

    Page<Menu> findMenusByIsDeleteAndParentMenuOrderBySeq(Integer isDelete, Menu menu, Pageable pageable);

    Menu findMenuById(String id);

    @Query(nativeQuery = true, value =" select IFNULL(max(seq),0) from SYS_MENU t where t.is_delete=0 and t.parent_id=?1 ")
    Integer queryMaxSeqByParentMenuId(String parentMenuId);

    @Query(nativeQuery = true, value =" select IFNULL(max(seq),0) from SYS_MENU t where t.is_delete=0 and t.parent_id is null ")
    Integer queryMaxSeqByParentMenuIsNull();

    Menu findMenusByIsDeleteAndSeqAndParentMenuOrderBySeq(Integer isDelete, Integer seq, Menu menu);
}
