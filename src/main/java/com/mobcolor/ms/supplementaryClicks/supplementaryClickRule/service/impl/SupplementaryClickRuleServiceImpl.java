
package com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service.impl;


import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.SupplementaryClickRuleModel;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.dto.SupplementaryClickRuleDTO;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service.SupplementaryClickRuleService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-01-23 21:03:59
 */
@Service
public class SupplementaryClickRuleServiceImpl extends BaseSupportServiceImpl implements SupplementaryClickRuleService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(SupplementaryClickRuleServiceImpl.class);

    public Map<String,SupplementaryClickRuleModel> supplementaryClickRuleIndex = new HashMap();

    public List<SupplementaryClickRuleModel> supplementaryClickRuleModelList = new ArrayList<>();


    @Override
    public void init() throws BusinessException {
        logger.info("规则初始化-----------------------------------------");
        try {
            /**
             * -------------将规则加入缓存---------------
             */
            List<SupplementaryClickRuleModel> supplementaryClickRuleModelList = selectSupplementaryClickRules(new SupplementaryClickRuleDTO());
            for (SupplementaryClickRuleModel supplementaryClickRuleModel : supplementaryClickRuleModelList) {
                supplementaryClickRuleIndex.put(supplementaryClickRuleModel.getId(),supplementaryClickRuleModel);
            }
            /**
             * -------------end------------------------
             */
            logger.info("规则初始化成功-----------------------------------------");
        } catch (Exception e) {
            //异常重启1 非正常退出
            logger.info("异常退出-----------------------------------------");
            System.exit(1);
        }
    }

    /**
     * 新增一个点击补量规则
     *
     * @param supplementaryClickRuleModel 点击补量规则
     */
    public void addSupplementaryClickRule(SupplementaryClickRuleModel supplementaryClickRuleModel) {
        if (BaseUtils.isBlank(supplementaryClickRuleModel.getInitMethod())
                || BaseUtils.isBlank(supplementaryClickRuleModel.getCallBackMethod())
                || BaseUtils.isBlank(supplementaryClickRuleModel.getChangeStateMethod()) ) {
            throw new BusinessException("缺少必要参数");
        }
        supplementaryClickRuleModel.setCreaterTime(new Date());
        supplementaryClickRuleModel.setUpdateTime(new Date());
        supplementaryClickRuleModel.setId(PrimaryUtil.getId());
        try {
            this.getDao().insert(supplementaryClickRuleModel);
        } catch (Exception e) {
            logger.error("新增点击补量规则失败", e);
            throw new BusinessException("新增点击补量规则失败");
        }
    }

    /**
     * 修改点击补量规则
     *
     * @param supplementaryClickRuleModel     修改的内容
     * @throws BusinessException
     */
    @Override
    public int updateSupplementaryClickRule(SupplementaryClickRuleModel supplementaryClickRuleModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(supplementaryClickRuleModel.getId()) ) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(supplementaryClickRuleModel);
        } catch (Exception e) {
            logger.error("修改点击补量规则信息失败", e);
            throw new BusinessException("修改点击补量规则信息失败");
        }
    }

    /**
     * 根据id删除某个点击补量规则
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteSupplementaryClickRule(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(SupplementaryClickRuleModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询点击补量规则数据集
     * @param supplementaryClickRuleDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<SupplementaryClickRuleModel> selectSupplementaryClickRules(SupplementaryClickRuleDTO supplementaryClickRuleDTO) throws BusinessException {
        if (supplementaryClickRuleModelList.size() !=0 ){
            return supplementaryClickRuleModelList;
        }
        try {
            String sql = " SELECT * FROM SUPPLEMENTARY_CLICK_RULE ";
            supplementaryClickRuleModelList = this.getDao().selectList(SupplementaryClickRuleModel.class,sql);
            return supplementaryClickRuleModelList;
        } catch (Exception e) {
            logger.error("查询点击补量规则数据失败", e);
            throw new BusinessException("查询点击补量规则数据失败");
        }
    }

    @Override
    public SupplementaryClickRuleModel loadSupplementaryClickRules(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }

        if (supplementaryClickRuleIndex.containsKey(id)){
            return supplementaryClickRuleIndex.get(id);
        }

        try {
            SupplementaryClickRuleModel supplementaryClickRuleModel = this.getDao().load(SupplementaryClickRuleModel.class,id);
            supplementaryClickRuleIndex.put(supplementaryClickRuleModel.getId(),supplementaryClickRuleModel);
            return supplementaryClickRuleModel;
        } catch (Exception e) {
            logger.error("加载数据失败", e);
            throw new BusinessException("加载数据失败");
        }
    }


}
