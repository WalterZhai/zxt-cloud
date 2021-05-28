package com.zxt.zxtcloud.basedata.repository;

import com.zxt.zxtcloud.basedata.entity.Depart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface DepartRepository extends JpaRepository<Depart,String> {

    Depart findDepartByCode(String code);

    List<Depart> findDepartsByParentDepartAndIsDelete(Depart d, Integer isDelete);

    Depart findDepartById(String id);

    Page<Depart> findDepartsByIsDeleteAndParentDepartOrderByCode(Integer isDelete, Depart d, Pageable pageable);

    List<Depart> findDepartsByIsDelete(Integer isDelete);

}
