package com.mobcolor.ms.youjia.service;

import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseService;
import com.mobcolor.ms.youjia.model.AccountModel;

import java.io.File;
import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface AccountExecService extends BaseService {

    /**
     * 获得一条任务
     * @param type 账号类型
     * @param appId 应用id
     * @return
     */
    AccountModel getAccount(String type,String appId,String deviceId) throws BusinessException;

    /**
     * 解除账号锁定
     * @param deviceId 设备id
     * @param isInVaild 是否停用该账号
     * @throws BusinessException
     */
    void unlockAccount(String deviceId,String isInVaild) throws BusinessException;




    /**
     * 账号停用
     */
    void acocuntInVaild(String id) throws BusinessException;

    /**
     * 定时任务初始化
     * @param taskId
     * @throws BusinessException
     */
    void initData(String taskId)throws BusinessException;

    /**
     *解析组装数据
     * @param file
     * @throws BusinessException
     */
    String assembleData(File file, String type) throws BusinessException;
}
