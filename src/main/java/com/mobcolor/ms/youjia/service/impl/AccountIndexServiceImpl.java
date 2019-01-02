

package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import com.mobcolor.ms.youjia.model.AccountIndexModel;
import com.mobcolor.ms.youjia.model.dto.AccountIndexDTO;
import com.mobcolor.ms.youjia.service.AccountIndexService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-04-10 15:27:04
 */
@Service
public class AccountIndexServiceImpl extends BaseSupportServiceImpl implements AccountIndexService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AccountIndexServiceImpl.class);

    /**
     * 新增一个应用账号下标索引
     *
     * @param accountIndexModel 应用账号下标索引
     */
    public void addAccountIndex(AccountIndexModel accountIndexModel) {
        if (BaseUtils.isBlank(accountIndexModel.getAppId()) || BaseUtils.isBlank(accountIndexModel.getType()) ) {
            throw new BusinessException("缺少必要参数...");
        }
        accountIndexModel.setId(PrimaryUtil.getId());
        accountIndexModel.setCreaterTime(new Date());
        accountIndexModel.setAccountIndex(0);
        try {
            this.getDao().insert(accountIndexModel);
        } catch (Exception e) {
            logger.error("新增应用账号下标索引失败", e);
            throw new BusinessException("新增应用账号下标索引失败");
        }
    }


    /**
     * 修改应用账号下标索引
     *
     * @param accountIndexModel  应用账号下标索引
     * @throws BusinessException
     */
    @Override
    public int updateAccountIndex(AccountIndexModel accountIndexModel) throws BusinessException {
        try {
            if ( BaseUtils.isBlank(accountIndexModel.getId()) ) {
                throw new BusinessException("缺少必要参数...");
            }
             return  this.getDao().update(accountIndexModel);
        } catch (Exception e) {
            logger.error("修改应用账号下标索引信息失败", e);
            throw new BusinessException("修改应用账号下标索引信息失败");
        }
    }

    /**
     * 根据id删除某个应用账号下标索引
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteAccountIndex(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(AccountIndexModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询应用账号下标索引数据集
     * @param accountIndexDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AccountIndexModel> selectAccountIndexs(AccountIndexDTO accountIndexDTO) throws BusinessException {
        
        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM ACCOUNTINDEX WHERE 1=1";
            return this.getDao().selectList(AccountIndexModel.class,sql,params);
        } catch (Exception e) {
            logger.error("查询应用账号下标索引数据失败", e);
            throw new BusinessException("查询应用账号下标索引数据失败");
        }
    }

    /**
     * 查询一条数据
     * @param appId
     * @param type
     * @return
     * @throws BusinessException
     */
    @Override
    public AccountIndexModel getAccountIndexByAppIdAndType(String appId, String type) throws BusinessException {
        if (BaseUtils.isBlank(appId) || BaseUtils.isBlank(type)) {
            throw new BusinessException("缺少必要参数...");
        }

        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM ACCOUNT_INDEX WHERE APP_ID = :APP_ID  AND TYPE = :TYPE ";
            params.put("APP_ID",appId);
            params.put("TYPE",type);
            return this.getDao().selectOne(AccountIndexModel.class,sql,params);
        } catch (Exception e) {
            logger.error("查询应用账号下标索引数据失败", e);
            throw new BusinessException("查询应用账号下标索引数据失败");
        }
    }


}
