package com.mobcolor.ms.supplementaryClicks.cake.service;

import com.mobcolor.ms.supplementaryClicks.cake.model.CakeSupplementaryClickModel;
import com.mobcolor.ms.supplementaryClicks.cake.model.dto.CakeSupplementaryClickBusinessDTO;
import com.mobcolor.ms.supplementaryClicks.cake.model.dto.CakeSupplementaryClickDTO;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.enums.SupplementaryClick;
import com.mobcolor.ms.youjia.model.AdvertisementModel;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface CakeSupplementaryClickService extends BaseService {

    /**
     * 创建一个cake补点击
     * @param taskListId 任务id'
     */
    void createCakeSupplementaryClick(AdvertisementModel advertisementModel,String taskListId) throws BusinessException;

    /**
     * 新增一个cake补点击
     * @param cakeSupplementaryClickModel cake补点击
     */
    void addCakeSupplementaryClick(CakeSupplementaryClickModel cakeSupplementaryClickModel) throws BusinessException;


    /**
     * 修改cake补点击内容
     * @param cakeSupplementaryClickModel
     * @throws BusinessException
     */
    int updateCakeSupplementaryClick(CakeSupplementaryClickModel cakeSupplementaryClickModel) throws BusinessException;


    /**
     * 给补点击开始进度数量+1
     * @param execTime
     * @return
     * @throws BusinessException
     */
    int updateCakeSupplementaryClickScheduleStart(String execTime) throws BusinessException;


    /**
     * 给补点击开始进度数量+1
     * @param ids
     * @return
     * @throws BusinessException
     */
    int updateCakeSupplementaryClickScheduleStart(List<String> ids) throws BusinessException;


    /**
     * 修改补点击结束进度
     * @param taskListId
     * @return
     * @throws BusinessException
     */
    int updateCakeSupplementaryClickScheduleEnd(String taskListId) throws BusinessException;

    /**
     * 修改补点击状态
     * @param taskIdList 需要被变更状态的id
     * @param cakeSupplementaryClick 状态
     * @return
     * @throws BusinessException
     */
    int updateCakeSupplementaryClickState(List<String> taskIdList, SupplementaryClick cakeSupplementaryClick) throws BusinessException;


    /**
     * 删除一个cake补点击
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteCakeSupplementaryClick(String id) throws BusinessException;

    /**
     * 查询cake补点击数据
     * @param cakeSupplementaryClickDTO
     * @return
     * @throws BusinessException
     */
    List<CakeSupplementaryClickModel> selectCakeSupplementaryClicks(CakeSupplementaryClickDTO cakeSupplementaryClickDTO) throws BusinessException;

    /**
     * 获得今日的补量
     * @return
     * @throws BusinessException
     */
    List<CakeSupplementaryClickBusinessDTO>  getCakeSupplementaryClicks(String region) throws BusinessException;

}
