package com.zxt.zxtcloud.basedata.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxt.zxtcloud.basedata.Impl.DepartServiceImpl;
import com.zxt.zxtcloud.basedata.bo.SimpleDepartBo;
import com.zxt.zxtcloud.basedata.entity.Depart;
import com.zxt.zxtcloud.basedata.repository.DepartRepository;
import com.zxt.zxtcloud.basedata.repository.EmployeeRepository;
import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.utils.MathsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Service
public class DeaprtService implements DepartServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(DeaprtService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartRepository departRepository;


    @Override
    public JSONArray departAjaxLoadTree() {
        JSONArray resultArr = new JSONArray();
        List<Depart> list = departRepository.findDepartsByParentDepartAndIsDelete(null,0);
        for(Depart d : list){
            JSONObject o = new JSONObject();
            o = recursionDepart(o,d);
            resultArr.add(o);
        }
        return resultArr;
    }

    //递归找儿子
    public JSONObject recursionDepart(JSONObject o,Depart d){
        o.put("title",d.getName());
        o.put("id",d.getId());
        List<Depart> list = departRepository.findDepartsByParentDepartAndIsDelete(d,0);
        if(list.size()>0){
            JSONArray arr = new JSONArray();
            for(Depart dchild : list){
                JSONObject oo = new JSONObject();
                oo.put("title",dchild.getName());
                oo.put("id",dchild.getId());
                oo = recursionDepart(oo,dchild);
                arr.add(oo);
            }
            o.put("children",arr);
        }
        return o;
    }

    @Override
    public TableEntity departTableData(String id, Integer page, Integer limit) {
        Depart depart = departRepository.findDepartById(id);
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Depart> pages = departRepository.findDepartsByIsDeleteAndParentDepartOrderByCode(0,depart,pageable);
        List<Depart> list = pages.getContent();
        List<SimpleDepartBo> result = new ArrayList<>();
        for(Depart d : list){
            result.add(new SimpleDepartBo(d));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }


}
