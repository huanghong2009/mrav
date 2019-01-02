package com.mobcolor.ms.youjia.service;

import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;
import com.mobcolor.ms.youjia.model.AccountIndexModel;
import com.mobcolor.ms.youjia.model.dto.AccountIndexDTO;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface AccountIndexService extends BaseService {

    /**
     * 新增一个应用下标索引
     * @param accountIndexModel 应用下标索引
     */
    void addAccountIndex(AccountIndexModel accountIndexModel) throws BusinessException;


    /**
     * 修改应用下标索引内容
     * @param accountIndexModel
     * @throws BusinessException
     */
    int updateAccountIndex(AccountIndexModel accountIndexModel) throws BusinessException;

    /**
     * 删除一个应用下标索引
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteAccountIndex(String id) throws BusinessException;

    /**
     * 查询应用下标索引数据
     * @param accountIndexDTO
     * @return
     * @throws BusinessException
     */
    List<AccountIndexModel> selectAccountIndexs(AccountIndexDTO accountIndexDTO) throws BusinessException;


    /**
     * 获取一条数据
     * @param appId
     * @param type
     * @return
     * @throws BusinessException
     */
    AccountIndexModel getAccountIndexByAppIdAndType(String appId,String type) throws BusinessException;

}
