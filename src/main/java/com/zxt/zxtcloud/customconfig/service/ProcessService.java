package com.zxt.zxtcloud.customconfig.service;

import com.zxt.zxtcloud.common.entity.TableEntity;
import com.zxt.zxtcloud.common.utils.StringUtils;
import com.zxt.zxtcloud.customconfig.Impl.ProcessServiceImpl;
import com.zxt.zxtcloud.customconfig.bo.SimpleProcessBo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProcessService implements ProcessServiceImpl {


    @PersistenceContext(unitName = "unimaxPersistenceUnit")
    private EntityManager unimaxEntityManager;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Override
    public TableEntity findModels(String title, Integer page, Integer limit) {
        StringBuffer sqlBuff = new StringBuffer();
        sqlBuff.append(" select m.id_,m.rev_,m.name_,m.key_,m.version_,m.meta_info_,d.deploy_time_ ");
        sqlBuff.append(" from ACT_RE_MODEL m left join ACT_RE_DEPLOYMENT d on m.deployment_id_=d.id_  ");
        sqlBuff.append(" where m.name_ like '" + StringUtils.string2LikeParam(title) + "' ");
        sqlBuff.append(" order by m.create_time_ ");
        sqlBuff.append(" LIMIT 0,10 ");
        List<Map> list = (List<Map>) unimaxEntityManager.createNativeQuery(sqlBuff.toString()).unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
        List<SimpleProcessBo> result = new ArrayList<>();
        for(Map map : list){
            SimpleProcessBo bo = new SimpleProcessBo();
            bo.setId(map.get("id_")==null?"":map.get("id_").toString());
            bo.setRev(map.get("rev_")==null?null:Integer.parseInt(map.get("rev_").toString()));
            bo.setName(map.get("name_")==null?"":map.get("name_").toString());
            bo.setKey(map.get("key_")==null?"":map.get("key_").toString());
            bo.setVersion(map.get("version_")==null?null:Integer.parseInt(map.get("version_").toString()));
            bo.setMetaInfo(map.get("meta_info_")==null?"":map.get("meta_info_").toString());
            bo.setDeployTime(map.get("deploy_time_")==null?null:(Date)map.get("deploy_time_"));
            result.add(bo);
        }

        String countSql = "select count(*) from ACT_RE_MODEL m where m.name_ like '" + StringUtils.string2LikeParam(title) + "'";
        BigInteger ct = (BigInteger) unimaxEntityManager.createNativeQuery(countSql).getSingleResult();
        return new TableEntity(result, new BigDecimal(ct));
    }


    @Override
    public String startProcessByKey(String processDefinitionKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        return processInstance.getId();
    }

    @Override
    public List<Task> getTask(String processInstanceId) {
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
//        if(list!=null && list.size()>0){
//            for(Task task:list){
//                System.out.println("任务ID:"+task.getId());
//                System.out.println("任务名称:"+task.getName());
//                System.out.println("任务的创建时间:"+task.getCreateTime());
//                System.out.println("任务的办理人:"+task.getAssignee());
//                System.out.println("流程实例ID："+task.getProcessInstanceId());
//                System.out.println("执行对象ID:"+task.getExecutionId());
//                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
//            }
//        }
        return list;
    }

    @Override
    public List<Task> getTaskByUserId(String userId) {
        List<Task> list = taskService.createTaskQuery().taskAssignee(userId).list();
//        for (Task task : list) {
//            System.out.println("id=" + task.getId());
//            System.out.println("name=" + task.getName());
//            System.out.println("assignee=" + task.getAssignee());
//            System.out.println("createTime=" + task.getCreateTime());
//            System.out.println("executionId=" + task.getExecutionId());
//        }
        return list;
    }

    @Override
    public List<Task> getTaskByProcessInstanceIdAndUserId(String processInstanceId, String userId) {
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(userId).list();
        return list;
    }

    @Override
    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    @Override
    public boolean isEnd(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(processInstance==null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void endProcessInstance(String processInstanceId) {
        runtimeService.deleteProcessInstance(processInstanceId, "结束");
    }

}
