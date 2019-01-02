

package com.mobcolor.ms.youjia.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobcolor.ms.youjia.enums.Account;
import com.mobcolor.ms.youjia.model.AccountErrorLogModel;
import com.mobcolor.ms.youjia.model.AccountModel;
import com.mobcolor.ms.youjia.model.dto.AccountDTO;
import com.mobcolor.ms.youjia.service.AccountService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-03-05 14:57:44
 */
@Service
public class AccountServiceImpl extends BaseSupportServiceImpl implements AccountService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AccountServiceImpl.class);


    /**
     * 新增一个账号
     *
     * @param accountModel 账号
     */
    public void addAccount(AccountModel accountModel) throws BusinessException {
        if (BaseUtils.isBlank(accountModel.getType()) || null == accountModel.getAccountIndex()
                || BaseUtils.isBlank(accountModel.getAccount()) || BaseUtils.isBlank(accountModel.getPassWord())) {
            throw new BusinessException("缺少必要参数...");
        }

        accountModel.setId(PrimaryUtil.getId());
        accountModel.setCreaterTime(new Date());
        accountModel.setLastUpdateTime(new Date());
        accountModel.setState(Account.VALID);

        try {
            this.getDao().insert(accountModel);
        } catch (Exception e) {
            logger.error("新增账号失败", e);
            throw new BusinessException("新增账号失败");
        }
    }


    /**
     * 新增一个账号
     *
     * @param accountModel 账号
     */
    public void addAccountIgnoreException(AccountModel accountModel) throws Exception {
        if (BaseUtils.isBlank(accountModel.getType())
                || BaseUtils.isBlank(accountModel.getAccount()) || BaseUtils.isBlank(accountModel.getPassWord())) {
            throw new BusinessException("缺少必要参数...");
        }

        accountModel.setId(PrimaryUtil.getId());
        accountModel.setCreaterTime(new Date());
        accountModel.setState(Account.VALID);

        try {
            this.getDao().insert(accountModel);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 批量增加
     *
     * @param accountModelList 账号
     * @throws BusinessException
     */
    @Override
    public void addBatchAccounts(List<AccountModel> accountModelList) throws BusinessException {
        try {
            this.getDao().insertBatch(accountModelList);
        } catch (Exception e) {
            logger.error("批量新增账号失败", e);
            throw new BusinessException("批量新增账号失败");
        }
    }

    /**
     * 分页查询账号
     *
     * @param accountDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<AccountModel> queryAccountByPage(AccountDTO accountDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM ACCOUNT WHERE 1=1 ";

            if (BaseUtils.isNotBlank(accountDTO.getType())) {
                sql += " AND TYPE = ?";
                params.add(accountDTO.getType());
            }

            if (null != accountDTO.getState()) {
                sql += " AND STATE = ?";
                params.add(accountDTO.getState().name());
            }

            PageVO<AccountModel> accountModelPageVO = this.getDao().pageQuery(AccountModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    accountDTO.getTo_page(), accountDTO.getPage_size());
            return accountModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询账号失败", e);
            throw new BusinessException("分页查询账号失败");
        }

    }

    /**
     * 修改账号
     *
     * @param accountModel 账号
     * @throws BusinessException
     */
    @Override
    public int updateAccount(AccountModel accountModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(accountModel.getId())) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(accountModel);
        } catch (Exception e) {
            logger.error("修改账号信息失败", e);
            throw new BusinessException("修改账号信息失败");
        }
    }

    /**
     * 根据id删除某个账号
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteAccount(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(AccountModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询账号数据集
     *
     * @param accountDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AccountModel> selectAccounts(AccountDTO accountDTO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        String sql = " SELECT * FROM ACCOUNT WHERE 1=1";

        if (null != accountDTO.getState()) {
            sql += " AND STATE = :STATE ";
            params.put("STATE", accountDTO.getState().name());
        }

        if (BaseUtils.isNotBlank(accountDTO.getType())) {
            sql += " AND TYPE = :TYPE ";
            params.put("TYPE", accountDTO.getType());
        }

        sql += " ORDER BY ACCOUNT_INDEX ";

        try {
            return this.getDao().selectList(AccountModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询账号数据失败", e);
            throw new BusinessException("查询账号数据失败");
        }
    }

    /**
     * 获得一个总数
     * @param accountDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<JSONObject> getTotal(AccountDTO accountDTO) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        String sql = " SELECT TYPE,COUNT(*) AS NUM FROM ACCOUNT WHERE 1=1";

        if (null != accountDTO.getState()) {
            sql += " AND STATE = :STATE ";
            params.put("STATE", accountDTO.getState().name());
        }

        if (BaseUtils.isNotBlank(accountDTO.getType())) {
            sql += " AND TYPE = :TYPE ";
            params.put("TYPE", accountDTO.getType());
        }
        sql += " GROUP BY TYPE ";
        try {
            return this.getDao().selectList(JSONObject.class, sql, params);
        } catch (Exception e) {
            logger.error("查询账号数据失败", e);
            throw new BusinessException("查询账号数据失败");
        }
    }

    @Override
    public AccountModel selectAccountByAccount(String accout) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM ACCOUNT WHERE ACCOUNT = :ACCOUNT";
            params.put("ACCOUNT", accout);
            return this.getDao().selectOne(AccountModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询账号数据失败", e);
            throw new BusinessException("查询账号数据失败");
        }
    }

    /**
     * 解析组装数据
     *
     * @param file
     * @throws BusinessException
     */
    @Override
    public String assembleData(File file, String type) throws BusinessException {
        if (null == file || BaseUtils.isBlank(type)) {
            throw new BusinessException("缺少必要参数...");
        }

        List<List<String>> excleData = BaseUtils.readExcel(file, 1).get(0);

        if (excleData.size() < 1) {
            logger.error("-----excel数据为空");
            throw new BusinessException("excel数据为空");
        }

        try {

            String bacthNo = this.sdf1.format(new Date());

            Integer index = getMaxIndex(type);
            if (index == null) {
                index = 0;
            }

            for (List<String> excleDatum : excleData) {

                index ++;

                /**
                 * 判断空行
                 */
                if (BaseUtils.isBlank(excleDatum.get(0)) && BaseUtils.isBlank(excleDatum.get(1))) {
                    break;
                }

                AccountModel accountModel = new AccountModel();
                accountModel.setAccount(excleDatum.get(0));
                accountModel.setPassWord(excleDatum.get(1));
                accountModel.setType(type);
                accountModel.setCreaterTime(new Date());
                accountModel.setLastUpdateTime(new Date());
                accountModel.setState(Account.VALID);
                accountModel.setId(PrimaryUtil.getId());
                accountModel.setAccountIndex(index);

                try {
                    addAccountIgnoreException(accountModel);
                } catch (Exception e) {
                    AccountErrorLogModel errorLogModel = new AccountErrorLogModel();
                    errorLogModel.setAccount(accountModel.getAccount());
                    errorLogModel.setLog(BaseUtils.getStackTrace(e));
                    errorLogModel.setBatchNo(bacthNo);
                    try {
                        this.getMongoDao().getTemplate().save(errorLogModel, AccountErrorLogModel.tableName);
                    } catch (Exception ex) {
                        logger.error("插入mongo错误数据失败:{}", accountModel);
                    }
                }
            }

            logger.info("assembleData:-----------END");

            return bacthNo;
        } catch (Exception e) {
            logger.error("组装数据失败", e);
            throw new BusinessException("组装数据失败");
        }
    }


    @Override
    public AccountModel loadAccount(String id) throws BusinessException {
        try {
            return this.getDao().load(AccountModel.class, id);
        } catch (Exception e) {
            logger.error("加载账号数据", e);
            throw new BusinessException("加载账号数据");
        }
    }

    /**
     * 修改下标
     *
     * @param type
     * @param index
     * @throws BusinessException
     */
    @Override
    public void updateAccountIndex(String type, Integer index) throws BusinessException {
        if (BaseUtils.isBlank(type) || null == index || index <= 0) {
            throw new BusinessException("缺少必要参数...");
        }
        Map<String, Object> params = new HashMap<>();
        String sql = "UPDATE ACCOUNT SET ACCOUNT_INDEX = ACCOUNT_INDEX - 1 WHERE TYPE = :TYPE AND STATE = :STATE AND  ACCOUNT_INDEX > :ACCOUNT_INDEX ";
        params.put("TYPE", type);
        params.put("ACCOUNT_INDEX", index);
        params.put("STATE", Account.INVALID.name());
        try {
            this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("修改下标失败", e);
            throw new BusinessException("修改下标失败");
        }
    }


    private Integer getMaxIndex(String type) throws BusinessException {
        if (BaseUtils.isBlank(type)) {
            throw new BusinessException("缺少必要参数...");
        }
        Map<String, Object> params = new HashMap<>();
        String sql = " SELECT MAX(ACCOUNT_INDEX) FROM  ACCOUNT WHERE TYPE =:TYPE ";
        params.put("TYPE", type);

        try {
            return this.getDao().selectOne(Integer.class, sql, params);
        } catch (Exception e) {
            logger.error("获取下标失败", e);
            throw new BusinessException("获取下标失败");
        }
    }

    /**
     * 获得一个账号
     * @param type
     * @param index
     * @return
     * @throws BusinessException
     */
    public AccountModel getAccount(String type,Integer index) throws BusinessException{
        if (BaseUtils.isBlank(type) || null == index ) {
            throw new BusinessException("缺少必要参数...");
        }
        Map<String, Object> params = new HashMap<>();
        String sql = " SELECT * FROM  ACCOUNT WHERE TYPE =:TYPE AND STATE = :STATE AND ACCOUNT_INDEX > :INDEX ORDER BY ACCOUNT_INDEX  LIMIT 1 ";
        params.put("TYPE", type);
        params.put("STATE",Account.VALID.name());
        params.put("INDEX",index);
        try {
            return this.getDao().selectOne(AccountModel.class, sql, params);
        } catch (Exception e) {
            logger.error("获取下标失败", e);
            throw new BusinessException("获取下标失败");
        }
    }
}
