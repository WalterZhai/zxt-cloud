package com.zxt.zxtcloud.common.init;

import com.zxt.zxtcloud.common.constant.SysConstant;
import com.zxt.zxtcloud.common.utils.StringUtils;
import com.zxt.zxtcloud.customconfig.Impl.DocuNumServiceImpl;
import com.zxt.zxtcloud.system.entity.Group;
import com.zxt.zxtcloud.system.entity.Menu;
import com.zxt.zxtcloud.system.entity.Role;
import com.zxt.zxtcloud.system.entity.User;
import com.zxt.zxtcloud.system.repository.GroupRepository;
import com.zxt.zxtcloud.system.repository.MenuRepository;
import com.zxt.zxtcloud.system.repository.RoleRepository;
import com.zxt.zxtcloud.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
@Transactional
public class InitImplementsService {

    static final Logger logger = LoggerFactory.getLogger(InitImplementsService.class);

    @PersistenceContext(unitName = "unimaxPersistenceUnit")
    private EntityManager unimaxEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private DocuNumServiceImpl docuNumServiceImpl;


    /**
     * @comment 初始化spring session 表
     * @author Walter(翟笑天)
     * @date 2021/3/10
     */
    public void initSpringSessionTable(){
        String judgeSql = " select COUNT(*) from information_schema.tables where table_name ='spring_session' ";
        BigInteger judge = (BigInteger) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigInteger.ZERO)==0){
            StringBuffer sql = new StringBuffer();
            sql.append(" CREATE TABLE `spring_session` ( ");
            sql.append("   `PRIMARY_ID` char(36) NOT NULL, ");
            sql.append("   `SESSION_ID` char(36) NOT NULL, ");
            sql.append("   `CREATION_TIME` bigint(20) NOT NULL, ");
            sql.append("   `LAST_ACCESS_TIME` bigint(20) NOT NULL, ");
            sql.append("   `MAX_INACTIVE_INTERVAL` int(11) NOT NULL, ");
            sql.append("   `EXPIRY_TIME` bigint(20) NOT NULL, ");
            sql.append("   `PRINCIPAL_NAME` varchar(100) DEFAULT NULL, ");
            sql.append("   PRIMARY KEY (`PRIMARY_ID`), ");
            sql.append("   UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`), ");
            sql.append("   KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`), ");
            sql.append("   KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`) ");
            sql.append(" ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC ");
            unimaxEntityManager.createNativeQuery(sql.toString()).executeUpdate();
            logger.info("spring session 主表建立成功");
        }
        judgeSql = " select COUNT(*) from information_schema.tables where table_name ='spring_session_attributes' ";
        judge = (BigInteger) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigInteger.ZERO)==0){
            StringBuffer sql = new StringBuffer();
            sql.append(" CREATE TABLE `spring_session_attributes` ( ");
            sql.append("   `SESSION_PRIMARY_ID` char(36) NOT NULL, ");
            sql.append("   `ATTRIBUTE_NAME` varchar(200) NOT NULL, ");
            sql.append("   `ATTRIBUTE_BYTES` blob NOT NULL, ");
            sql.append("   PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`), ");
            sql.append("   CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE ");
            sql.append(" ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC ");
            unimaxEntityManager.createNativeQuery(sql.toString()).executeUpdate();
            logger.info("spring session 副表建立成功");
        }
    }

    /**
     * @comment 初始化admin用户、系统管理员角色、系统自带菜单
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    public void initAdmin(){
        User user = userRepository.findUserByLoginNameAndIsDelete("admin",0);
        if(user==null){
            user = new User();
            user.setLoginName(SysConstant.ADMIN);
            user.setPassword(SysConstant.PASSWORD);
            user.setLoginName(SysConstant.ADMIN);
            user.setIsLocked(0);
            user.setName(SysConstant.ADMIN);
            user.setCreateId(SysConstant.ADMIN);
            user.setModifyId(SysConstant.ADMIN);
            user = userRepository.save(user);

            //用户组
            Group group = new Group();
            group.setCode(SysConstant.SYSTEM_GROUP_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.GROUP_SEQ).getCur().toString(),3,"0"));
            group.setName(SysConstant.SYSTEM_GROUP_NAME);
            group.setDescription(SysConstant.SYSTEM_GROUP_DESCRIPTION);
            group.setCreateId(SysConstant.ADMIN);
            group.setModifyId(SysConstant.ADMIN);
            group = groupRepository.save(group);
            user.getGroups().add(group);

            //角色
            Role role = new Role();
            role.setCode(SysConstant.SYSTEM_ROLE_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.ROLE_SEQ).getCur().toString(),3,"0"));
            role.setName(SysConstant.SYSTEM_ROLE_NAME);
            role.setDescription(SysConstant.SYSTEM_ROLE_DESCRIPTION);
            role.setCreateId(SysConstant.ADMIN);
            role.setModifyId(SysConstant.ADMIN);
            role = roleRepository.save(role);
            user.getRoles().add(role);


            //权限管理 菜单
            Menu menu1 = new Menu();
            menu1.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu1.setName(SysConstant.MENU_PERMIT_MANAGER_NAME);
            menu1.setSeq(1);
            menu1.setType(0);
            menu1.setIcon("layui-icon-auz");
            menu1.setCreateId(SysConstant.ADMIN);
            menu1.setModifyId(SysConstant.ADMIN);
            menu1 = menuRepository.save(menu1);
            role.getMenus().add(menu1);
            //用户管理 菜单
            Menu menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_USER_MANAGER_NAME);
            menu2.setHref("topage?url=system/user/user.html");
            menu2.setParentMenu(menu1);
            menu2.setSeq(1);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //角色管理 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_ROLE_MANAGER_NAME);
            menu2.setHref("topage?url=system/role/role.html");
            menu2.setParentMenu(menu1);
            menu2.setSeq(2);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //菜单管理 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_MENU_MANAGER_NAME);
            menu2.setHref("topage?url=system/menu/menu.html");
            menu2.setParentMenu(menu1);
            menu2.setSeq(3);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //用户组管理 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_GROUP_MANAGER_NAME);
            menu2.setHref("topage?url=system/group/group.html");
            menu2.setParentMenu(menu1);
            menu2.setSeq(4);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);




            //共享数据 菜单
            menu1 = new Menu();
            menu1.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu1.setName(SysConstant.MENU_BASE_DATA_NAME);
            menu1.setSeq(2);
            menu1.setType(0);
            menu1.setIcon("layui-icon-template-1");
            menu1.setCreateId(SysConstant.ADMIN);
            menu1.setModifyId(SysConstant.ADMIN);
            menu1 = menuRepository.save(menu1);
            role.getMenus().add(menu1);
            //系统数据 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_DATA_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(1);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //系统参数 菜单
            Menu menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_PARAMS_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/systemParams/systemParams.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //密码策略 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_PASSWORD_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/passwordPolicy/passwordPolicy.html");
            menu3.setSeq(2);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //组织架构 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_ORG_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(2);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //员工信息 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_EMPLOYEE_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/employee/employee.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //部门员工 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_DEPART_EMPLOYEE_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/employee/depart-employee.html");
            menu3.setSeq(2);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //部门信息 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_DEPART_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/depart/depart.html");
            menu3.setSeq(3);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);

            //客户化配置 菜单
            menu1 = new Menu();
            menu1.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu1.setName(SysConstant.MENU_CUSTOM_CONFIG_NAME);
            menu1.setSeq(3);
            menu1.setType(0);
            menu1.setIcon("layui-icon-set");
            menu1.setCreateId(SysConstant.ADMIN);
            menu1.setModifyId(SysConstant.ADMIN);
            menu1 = menuRepository.save(menu1);
            role.getMenus().add(menu1);
            //消息引擎 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_MESSAGE_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(1);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //消息管理 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_MESSAGE_MANAGER_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/message/message.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //流程引擎 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_PROCESS_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(2);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //流程管理 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_PROCESS_MANAGER_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/process/process.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //任务调度 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_TASK_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(3);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //任务管理 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_TASK_MANAGER_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/scheduleTask/scheduleTask.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //自定义项 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_CUSTOM_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(4);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            menu2 = menuRepository.save(menu2);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //自定义档案 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_CUSTOM_FILE_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/definedFile/definedFile.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //单据号管理 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(docuNumServiceImpl.next(SysConstant.MENU_SEQ).getCur().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_DOCU_NUM_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/docuNum/docuNum.html");
            menu3.setSeq(2);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            menu3 = menuRepository.save(menu3);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);

            userRepository.save(user);
        }
    }


}
