package com.mobcolor.ms.youjia.service;

import com.alibaba.fastjson.JSONObject;
import com.mobcolor.ms.youjia.model.AccountModel;
import com.mobcolor.ms.youjia.model.dto.AccountDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.io.File;
import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface AccountService extends BaseService {

    /**
     * 新增一个账号
     * @param accountModel 账号
     */
    void addAccount(AccountModel accountModel) throws BusinessException;

    /**
     * 新增一批账号
     * @param accountModelList 账号列表
     */
    void addBatchAccounts(List<AccountModel> accountModelList) throws BusinessException;

    /**
     * 查询账号
     * @param accountDTO
     * @return
     * @throws BusinessException
     */
    PageVO<AccountModel> queryAccountByPage(AccountDTO accountDTO) throws BusinessException;

    /**
     * 修改账号内容
     * @param accountModel
     * @throws BusinessException
     */
    int updateAccount(AccountModel accountModel) throws BusinessException;

    /**
     * 删除一个账号
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteAccount(String id) throws BusinessException;

    /**
     * 查询账号数据
     * @param accountDTO
     * @return
     * @throws BusinessException
     */
    List<AccountModel> selectAccounts(AccountDTO accountDTO) throws BusinessException;


    /**
     * 获得总数
     * @param accountDTO
     * @return
     * @throws BusinessException
     */
    List<JSONObject> getTotal(AccountDTO accountDTO) throws BusinessException;

    /**
     * 查询账号数据根据账号
     * @param account
     * @return
     * @throws BusinessException
     */
    AccountModel selectAccountByAccount(String account) throws BusinessException;

    /**
     *解析组装数据
     * @param file
     * @throws BusinessException
     */
    String assembleData(File file, String type) throws BusinessException;


    /**
     * 加载账号数据
     * @param id
     * @return
     * @throws BusinessException
     */
    AccountModel loadAccount(String id)throws BusinessException;

    /**
     * 修改下标
     * @param type
     * @param index
     * @throws BusinessException
     */
    void updateAccountIndex(String type,Integer index) throws BusinessException;


    /**
     * 获得一条数据
     * @param type
     * @param index
     * @return
     * @throws BusinessException
     */
    AccountModel getAccount(String type,Integer index) throws BusinessException;

}
