
package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.ms.youjia.model.AccountCategoriesModel;
import com.mobcolor.ms.youjia.model.dto.AccountCategoriesDTO;
import com.mobcolor.ms.youjia.service.AccountCategoriesService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-03-05 14:20:03
 */
@Service
public class AccountCategoriesServiceImpl extends BaseSupportServiceImpl implements AccountCategoriesService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AccountCategoriesServiceImpl.class);

    private HashMap<String,String> nameIndex;


    @Value("${accountCategories.id}")
    private String id;

    @Value("${accountCategories.name}")
    private String name;


    @Override
    public void init() throws BusinessException {
        nameIndex = new HashMap<>();
        List<AccountCategoriesModel> accountCategoriesModels = selectAccountCategoriess(new AccountCategoriesDTO());
        for (AccountCategoriesModel accountCategoriesModel : accountCategoriesModels) {
            nameIndex.put(accountCategoriesModel.getId(),accountCategoriesModel.getName());
        }

        logger.info("正在验证系统默认数据:系统默认类别--------");
        if (loadAccountCategories(id) == null){
            logger.info("正在创建:系统默认类别--------");
            AccountCategoriesModel accountCategoriesModel = new AccountCategoriesModel();
            accountCategoriesModel.setId(id);
            accountCategoriesModel.setName(name);
            addAccountCategories(accountCategoriesModel);
        }
        logger.info("end--------");
    }

    /**
     * 新增一个账号类别
     *
     * @param accountCategoriesModel 账号类别
     */
    public void addAccountCategories(AccountCategoriesModel accountCategoriesModel) {
        if (BaseUtils.isBlank(accountCategoriesModel.getName())) {
            throw new BusinessException("缺少必要参数...");
        }
        if (BaseUtils.isBlank(accountCategoriesModel.getId())){
            accountCategoriesModel.setId(PrimaryUtil.getId());
        }
        accountCategoriesModel.setCreaterTime(new Date());

        try {
            this.getDao().insert(accountCategoriesModel);
            nameIndex.put(accountCategoriesModel.getId(),accountCategoriesModel.getName());
        } catch (Exception e) {
            logger.error("新增账号类别失败", e);
            throw new BusinessException("新增账号类别失败");
        }
    }

    /**
     * 分页查询账号类别
     *
     * @param accountCategoriesDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<AccountCategoriesModel> queryAccountCategoriesByPage(AccountCategoriesDTO accountCategoriesDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM ACCOUNT_CATEGORIES WHERE 1=1 ";

            if (BaseUtils.isNotBlank(accountCategoriesDTO.getName())) {
                sql += " AND NAME LIKE ?";
                params.add("%" + accountCategoriesDTO.getName() + "%");
            }

            PageVO<AccountCategoriesModel> accountCategoriesModelPageVO = this.getDao().pageQuery(AccountCategoriesModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    accountCategoriesDTO.getTo_page(), accountCategoriesDTO.getPage_size());
            return accountCategoriesModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询账号类别失败", e);
            throw new BusinessException("分页查询账号类别失败");
        }

    }

    /**
     * 修改账号类别
     *
     * @param id      主键id
     * @param name  名称
     * @throws BusinessException
     */
    @Override
    public int updateAccountCategories(String id, String name) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id) || BaseUtils.isBlank(name)) {
                throw new BusinessException("缺少必要参数...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("NAME", name);
            int num = this.getDao().exec("UPDATE ACCOUNT_CATEGORIES SET NAME = :NAME WHERE ID = :ID", params);
            nameIndex.put(id,name);
            return num;
        } catch (Exception e) {
            logger.error("修改账号类别信息失败", e);
            throw new BusinessException("修改账号类别信息失败");
        }
    }

    /**
     * 根据id删除某个账号类别
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteAccountCategories(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().delete(AccountCategoriesModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询账号类别数据集
     *
     * @param accountCategoriesDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AccountCategoriesModel> selectAccountCategoriess(AccountCategoriesDTO accountCategoriesDTO) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM ACCOUNT_CATEGORIES WHERE 1=1";
            return this.getDao().selectList(AccountCategoriesModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询账号类别数据失败", e);
            throw new BusinessException("查询账号类别数据失败");
        }
    }

    /**
     * 加载一条数据
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public AccountCategoriesModel loadAccountCategories(String id) throws BusinessException {
        try {
            return this.getDao().load(AccountCategoriesModel.class,id);
        } catch (Exception e) {
            logger.error("加载数据失败", e);
            throw new BusinessException("加载数据失败");
        }
    }

    @Override
    public String getNameById(String id) throws BusinessException {
        return nameIndex.get(id);
    }


}
