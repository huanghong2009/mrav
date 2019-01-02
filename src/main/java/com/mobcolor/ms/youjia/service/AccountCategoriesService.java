package com.mobcolor.ms.youjia.service;

import com.mobcolor.ms.youjia.model.AccountCategoriesModel;
import com.mobcolor.ms.youjia.model.dto.AccountCategoriesDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface AccountCategoriesService extends BaseService {

    /**
     * 新增一个账号类别
     *
     * @param accountCategoriesModel 账号类别
     */
    void addAccountCategories(AccountCategoriesModel accountCategoriesModel) throws BusinessException;

    /**
     * 查询账号类别
     *
     * @param accountCategoriesDTO
     * @return
     * @throws BusinessException
     */
    PageVO<AccountCategoriesModel> queryAccountCategoriesByPage(AccountCategoriesDTO accountCategoriesDTO) throws BusinessException;

    /**
     * 修改账号类别内容
     *
     * @param name
     * @throws BusinessException
     */
    int updateAccountCategories(String id, String name) throws BusinessException;

    /**
     * 删除一个账号类别
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteAccountCategories(String id) throws BusinessException;

    /**
     * 查询账号类别数据
     *
     * @param accountCategoriesDTO
     * @return
     * @throws BusinessException
     */
    List<AccountCategoriesModel> selectAccountCategoriess(AccountCategoriesDTO accountCategoriesDTO) throws BusinessException;

    /**
     * 加载数据
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    AccountCategoriesModel loadAccountCategories(String id) throws BusinessException;

    /**
     * 加载缓存name
     * @param id
     * @return
     * @throws BusinessException
     */
    String getNameById(String id) throws BusinessException;
}
