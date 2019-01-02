package com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service;


import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.SupplementaryClickRuleModel;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.dto.SupplementaryClickRuleDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface SupplementaryClickRuleService extends BaseService {

    /**
     * 新增一个点击补量规则
     * @param supplementaryClickRuleModel 点击补量规则
     */
    void addSupplementaryClickRule(SupplementaryClickRuleModel supplementaryClickRuleModel) throws BusinessException;


    /**
     * 修改点击补量规则内容
     * @param supplementaryClickRuleModel
     * @throws BusinessException
     */
    int updateSupplementaryClickRule(SupplementaryClickRuleModel supplementaryClickRuleModel) throws BusinessException;

    /**
     * 删除一个点击补量规则
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteSupplementaryClickRule(String id) throws BusinessException;

    /**
     * 查询点击补量规则数据
     * @param supplementaryClickRuleDTO
     * @return
     * @throws BusinessException
     */
    List<SupplementaryClickRuleModel> selectSupplementaryClickRules(SupplementaryClickRuleDTO supplementaryClickRuleDTO) throws BusinessException;


    /**
     * 加载一条数据
     * @return
     * @throws BusinessException
     */
    SupplementaryClickRuleModel loadSupplementaryClickRules(String id) throws BusinessException;

}
