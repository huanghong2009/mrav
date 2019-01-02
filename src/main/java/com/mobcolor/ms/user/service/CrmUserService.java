package com.mobcolor.ms.user.service;

import com.mobcolor.ms.user.model.CrmUserModel;
import com.mobcolor.ms.user.model.dto.CrmUserDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author 黄鸿 E-mail：hong.huang@jsxfedu.com
 * @version 1.0 创建时间： 2016/9/29.
 */
public interface CrmUserService {

    /**
     *用户登陆
     * @param email 用户登陆邮箱
     * @param password 密码
     * @return map：
     *      code：0 1 2  成功/失败/未激活
     *      uuid：xxxxxxxxxxxxxxxxxx
     */
    Map loginValidation(String email, String password);
    /**
     *用户验证
     * @param password 密码
     * @return 0 1 2  成功/失败/未激活
     */
    Map  userValidation(String email, String password)throws BusinessException;

    /**
     *查询所有用户
     * @param crmUserDTO
     * @return
     * @throws BusinessException
     */
    PageVO<CrmUserModel> queryCrmUsetByPage(CrmUserDTO crmUserDTO) throws BusinessException;

    /**
     *验证用户存在不存在
     * @param email
     * @return 0 1 2 存在/不存在/存在但未激活
     */
    boolean verifyEmailAvailability(String email)throws BusinessException;

    /**
     *开通账户
     * @param email 开通邮箱
     * @return 0 1 2 成功/失败/邮件已经存在
     * @throws BusinessException
     */
    @Transactional
    void openAccount(String name,String email, String passWord) throws BusinessException;
    /**
     *重置密码
     * @return
     * @throws BusinessException
     */
    void resetPassword( String email,String passwordOld,String passwordNew )throws BusinessException;


    /**
     * 超管初始化接口，会在系统启动时初始化超管账号。
     * @throws BusinessException
     */
    void adminInit()throws BusinessException;

}
