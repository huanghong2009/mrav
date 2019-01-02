

package com.mobcolor.ms.youjia.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import com.mobcolor.ms.youjia.enums.Account;
import com.mobcolor.ms.youjia.model.AccountIndexModel;
import com.mobcolor.ms.youjia.model.AccountModel;
import com.mobcolor.ms.youjia.model.dto.AccountDTO;
import com.mobcolor.ms.youjia.model.dto.AccountIndexDTO;
import com.mobcolor.ms.youjia.service.AccountExecService;
import com.mobcolor.ms.youjia.service.AccountIndexService;
import com.mobcolor.ms.youjia.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-04-10 15:27:04
 */
@Service
public class AccountExecServiceImpl extends BaseSupportServiceImpl implements AccountExecService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AccountExecServiceImpl.class);

    @Resource
    private AccountService accountService;

    @Resource
    private AccountIndexService accountIndexService;



    private Map<String, AccountModel> accoutLock;



    public void init() throws BusinessException {
        accoutLock = new HashMap<>();
    }

    /**
     * 获取账号
     *
     * @param type     账号类型
     * @param appId    应用id
     * @param deviceId
     * @return
     * @throws BusinessException
     */
    @Override
    public synchronized AccountModel getAccount(String type, String appId, String deviceId) throws BusinessException {

        if (BaseUtils.isBlank(type) || BaseUtils.isBlank(appId) || BaseUtils.isBlank(deviceId)) {
            throw new BusinessException("缺少必要参数...");
        }

        if (accoutLock.containsKey(deviceId)) {
            logger.warn("-----------{}重复获取{}类型账号------------", deviceId, type);
            return accoutLock.get(deviceId);
        }

        AccountIndexModel accountIndexModel = accountIndexService.getAccountIndexByAppIdAndType(appId, type);

        if (null == accountIndexModel || BaseUtils.isBlank(accountIndexModel.getId())) {
            accountIndexModel = new AccountIndexModel();
            accountIndexModel.setAppId(appId);
            accountIndexModel.setType(type);
            accountIndexService.addAccountIndex(accountIndexModel);
        }

        AccountModel resultData = accountService.getAccount(type,accountIndexModel.getAccountIndex());

        if ( null != resultData) {
            accountIndexModel.setAccountIndex(resultData.getAccountIndex());
            accountIndexService.updateAccountIndex(accountIndexModel);
            accoutLock.put(deviceId, resultData);
        }

        return resultData;

    }


    /**
     * 解除账号锁定
     *
     * @param deviceId
     * @throws BusinessException
     */
    @Override
    public void unlockAccount(String deviceId, String isInVaild) throws BusinessException {

        if (BaseUtils.isBlank(deviceId)) {
            throw new BusinessException("缺少必要参数...");
        }

        if (!accoutLock.containsKey(deviceId)) {
            return;
        }

        AccountModel accountModel = accoutLock.get(deviceId);
        accoutLock.remove(deviceId);

        if (BaseUtils.isNotBlank(isInVaild) && isInVaild.equals("Y")) {
            acocuntInVaild(accountModel.getId());
        }
    }

    /**
     * 账号停用
     *
     * @param id
     * @throws BusinessException
     */
    @Override
    public void acocuntInVaild(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        AccountModel accountModel = new AccountModel();
        accountModel.setId(id);
        accountModel.setState(Account.INVALID);
        accountService.updateAccount(accountModel);
    }

    /**
     * 加载数据
     * @param taskId
     * @throws BusinessException
     */
    @Override
    public void initData(String taskId) throws BusinessException {
        logger.info("---------------{}:定时任务开始执行:{}--------------",taskId,"AccountExecServiceImpl.initData");
        init();
    }

    /**
     * 拼装数据
     * @param file
     * @param type
     * @return
     * @throws BusinessException
     */
    @Override
    public String assembleData(File file, String type) throws BusinessException {
        String data = accountService.assembleData(file,type);
        init();
        return data;
    }


}
