package com.mobcolor.ms.user.service.impl;


import com.mobcolor.ms.user.enums.CrmEnmu;
import com.mobcolor.ms.user.model.CrmUserModel;
import com.mobcolor.ms.user.model.dto.CrmUserDTO;
import com.mobcolor.ms.user.service.CrmUserService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 黄鸿 E-mail：hong.huang@jsxfedu.com
 * @version 1.0 创建时间： 2016/10/8.
 */
@Service
public class CrmUserServiceImpl extends BaseSupportServiceImpl implements CrmUserService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(CrmUserServiceImpl.class);//日志类

    @Value("${superAdministrator.email}")//加载超管账号
    private String adminEmail;

    @Value("${superAdministrator.pass}")//加载超管密码
    private String adminPassword;


    /**
     * 系统初始化调用的方法，目前用户从初始化超管
     *
     * @throws BusinessException
     */
    public void init() throws BusinessException {
        logger.info("超管初始化-----------------------------------------");
        adminInit();
    }


    /**
     * 用户验证
     *
     * @param email    用户cokie
     * @param password 密码
     * @return 0 1 2 3 成功/失败/未激活/已停用
     */
    @Override
    public Map userValidation(String email, String password) throws BusinessException {
        Map<String,Object> result = new HashMap<>();
        try {

            CrmUserModel crmUserModel = getCrmUserModel(email);
            /**
             * A:如果查询结果为空，直接返回失败
             * B:查询到结果：
             *          1：状态正常 验证密码是否正确  返回0 1
             *          2：状态不正常（待激活） 返回2
             *
             */

            if (crmUserModel == null || BaseUtils.isBlank(crmUserModel.getId())) {
                logger.error("用户为空");
                throw new BusinessException("用户未开通,请联系管理员");

            } else if (crmUserModel.getStatus().equals(CrmEnmu.DISABLE)) {
                logger.info("用户已停用");
                throw new BusinessException("用户已停用,请联系管理员");
            } else {

                /**
                 * 加盐比较，拿加盐加密后的密码与库里面的比较
                 */
                String passwordHash = SHA256.Encrypt(BaseUtils.addSalt(password, crmUserModel.getSalt()));//加密后的密码

                if (passwordHash.equals(crmUserModel.getPassword())) {
                    result.put("state",true);
                    result.put("data",crmUserModel);
                } else {
                    result.put("state",false);
                }
                return result;
            }
        } catch (Exception ex) {
            logger.info("用户未开通,请联系管理员", ex.getMessage());
            throw new BusinessException("获取对象失败,失败原因:" + ex.getMessage());
        }
    }

    /**
     * 查询所有用户
     *
     * @param crmUserDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<CrmUserModel> queryCrmUsetByPage(CrmUserDTO crmUserDTO) throws BusinessException {
        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM CRM_USER WHERE 1=1 ";

            if (BaseUtils.isNotBlank(crmUserDTO.getEmail())) {
                sql += " AND EMAIL = ?";
                params.add(crmUserDTO.getEmail());
            }
            if (null != crmUserDTO.getStatus()) {
                sql += " AND STATUS = ?";
                params.add(crmUserDTO.getStatus().name());
            }

            return this.getDao().pageQuery(CrmUserModel.class, sql, params.size() > 0 ? params.toArray(new Object[params.size()]) : null, crmUserDTO.getTo_page(), crmUserDTO.getPage_size());
        } catch (Exception ex) {
            logger.info("查询用户列表失败:", ex.getMessage());
            throw new BusinessException("查询用户列表失败");
        }
    }

    /**
     * 验证用户状态
     *
     * @param email
     * @return 0 1 2 存在/不存在
     */
    @Override
    public boolean verifyEmailAvailability(String email) throws BusinessException {
        try {
            //查询这个用户
            CrmUserModel crmUserModel = getCrmUserModel(email);
            if (crmUserModel == null || BaseUtils.isBlank(crmUserModel.getId())) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new BusinessException("获取对象失败,失败原因:" + e.getMessage());
        }
    }


    /**
     * 开通账户
     *
     * @param email 开通邮箱
     * @return 0 1 2  成功/失败/邮件已经存在
     * @throws BusinessException
     */
    public void openAccount(String name,String email, String passWord) throws BusinessException {
        boolean isExistence = verifyEmailAvailability(email);//验证账户状态

        /**
         * A:账户存在
         * B:账户不存在，开通账户。发送邮件
         * C:账户处于待激活状态，直接发送邮件
         */

        if (isExistence) {
            throw new BusinessException("账户已存在");
        } else {
            //新增账户
            addCrmUser(name,email, passWord);
        }
    }

    /**
     * 初始化超管
     *
     * @throws BusinessException
     */
    private void initSuperAdmin() throws BusinessException {
        if (verifyEmailAvailability(this.adminEmail)) {
            return;
        }
        //新增账户
        addCrmUser("Admin",this.adminEmail, this.adminPassword);
    }

    /**
     * 修改密码
     *
     * @param passwordOld 原密码
     * @param passwordNew 新密码
     * @param email       邮箱
     * @return
     * @throws BusinessException
     */
    @Override
    public void resetPassword(String email, String passwordOld, String passwordNew) throws BusinessException {
        if (BaseUtils.isBlank(email) || BaseUtils.isBlank(passwordOld) || BaseUtils.isBlank(passwordNew)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {

            CrmUserModel crmUserModel = getCrmUserModel(email);

            if (!crmUserModel.getPassword().equals(SHA256.Encrypt(BaseUtils.addSalt(passwordOld, crmUserModel.getSalt())))) {
                throw new BusinessException("原密码错误："+passwordOld);
            }

            /*
            密码加盐加密处理
             */
            String salt = BaseUtils.creatSalt();//获取盐值
            String passwordHash = SHA256.Encrypt(BaseUtils.addSalt(passwordNew, salt));//获取加盐后加密的值；

            crmUserModel.setSalt(salt);
            crmUserModel.setPassword(passwordHash);

            this.getDao().update(crmUserModel);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new BusinessException("更新失败:" + e.getMessage());
        }
    }


    /**
     * 新增用户
     *
     * @param emial
     * @param passWord 密码
     */
    private void addCrmUser(String name, String emial, String passWord) throws BusinessException {
        if (BaseUtils.isBlank(name) || BaseUtils.isBlank(emial) || BaseUtils.isBlank(passWord)) {
           throw new BusinessException("缺少必要参数...");
        }

        String salt = BaseUtils.creatSalt();//获取盐值
        String passwordHash = SHA256.Encrypt(BaseUtils.addSalt(passWord, salt));//获取加盐后加密的值；

        CrmUserModel crmUserModel = new CrmUserModel();
        crmUserModel.setId(PrimaryUtil.getId());
        crmUserModel.setUsername(name);
        crmUserModel.setEmail(emial);
        crmUserModel.setStatus(CrmEnmu.ENABLE);
        crmUserModel.setSalt(salt);
        crmUserModel.setPassword(passwordHash);
        crmUserModel.setCreateTime(new Date());

        try {
            this.getDao().insert(crmUserModel);
        } catch (Exception e) {
            logger.info("新增用户失败", e.getMessage());
            throw new BusinessException("新增用户失败");
        }
    }


    /**
     * 查询一条用户信息
     */
    private CrmUserModel getCrmUserModel(String email) {
        try {
            //查询这个用户
            CrmUserModel crmUserModel = this.getDao().selectOne(CrmUserModel.class, "SELECT * FROM CRM_USER WHERE EMAIL=?", new Object[]{email});
            return crmUserModel;
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            throw new BusinessException("获取对象失败,失败原因:" + ex.getMessage());
        }
    }


    /**
     * 系统初始化超管（很重要）
     *
     * @throws BusinessException
     */
    @Override
    public void adminInit() throws BusinessException {
        try {
            if (BaseUtils.isNotBlank(this.adminEmail)) {
                //如果超级管理员账户存在，就不管他
                //如果不存在，就创建它，并发送邮件
                //如果处于未激活状态，则只发送邮件
                //设置redis，超级管理员邮件永久有效
//                String uuid=this.getDao().getUUID();//生成uuid'
//
//                setRedisValue(uuid,this.adminEmail,0);//设置到redis里面去;//将此设置到redis里面去
                initSuperAdmin();
            } else {
                throw new BusinessException("缺少默认配置");
            }
        } catch (Exception e) {
            System.exit(1);
            logger.info(e.getMessage());
            throw new BusinessException("初始化超管失败:" + e.getMessage());
        }
    }


    /**
     * 用户登陆
     *
     * @param email    用户登陆邮箱
     * @param password 密码
     * @return
     */
    @Override
    public Map loginValidation(String email, String password) {
        if (BaseUtils.isBlank(email) || BaseUtils.isBlank(password)) {
            throw new BusinessException("缺少必要参数...");
        }
        return userValidation(email, password);//验证用户
    }


}
