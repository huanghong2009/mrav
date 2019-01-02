
package com.mobcolor.ms.supplementaryClicks.cake.service.impl;

import com.mobcolor.ms.supplementaryClicks.cake.model.CakeSupplementaryClickModel;
import com.mobcolor.ms.supplementaryClicks.cake.model.dto.CakeSupplementaryClickBusinessDTO;
import com.mobcolor.ms.supplementaryClicks.cake.model.dto.CakeSupplementaryClickDTO;
import com.mobcolor.ms.supplementaryClicks.cake.service.CakeSupplementaryClickService;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.enums.SupplementaryClick;
import com.mobcolor.ms.youjia.model.AdvertisementModel;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-01-24 10:46:54
 */
@Service
public class CakeSupplementaryClickServiceImpl extends BaseSupportServiceImpl implements CakeSupplementaryClickService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(CakeSupplementaryClickServiceImpl.class);

    @Override
    public void createCakeSupplementaryClick(AdvertisementModel advertisementModel, String taskListId) throws BusinessException {
        CakeSupplementaryClickModel cakeSupplementaryClickModel = new CakeSupplementaryClickModel();
        cakeSupplementaryClickModel.setClickUrl(advertisementModel.getServerConfig().replace("{cid}", PrimaryUtil.getId()));
        cakeSupplementaryClickModel.setOriginalAdvertisementId(advertisementModel.getOriginalAdvertisementId());
        cakeSupplementaryClickModel.setTaskListId(taskListId);
        cakeSupplementaryClickModel.setScheduleStart(0);
        cakeSupplementaryClickModel.setScheduleEnd(advertisementModel.getMinClickNum());
        cakeSupplementaryClickModel.setConversionRate(advertisementModel.getConversionRate());

        addCakeSupplementaryClick(cakeSupplementaryClickModel);
    }

    /**
     * 新增一个cake补点击
     *
     * @param cakeSupplementaryClickModel cake补点击
     */
    public void addCakeSupplementaryClick(CakeSupplementaryClickModel cakeSupplementaryClickModel) {
        if (BaseUtils.isBlank(cakeSupplementaryClickModel.getClickUrl())
                || BaseUtils.isBlank(cakeSupplementaryClickModel.getOriginalAdvertisementId())
                || BaseUtils.isBlank(cakeSupplementaryClickModel.getTaskListId())
                || null == cakeSupplementaryClickModel.getConversionRate()) {
            throw new BusinessException("缺少必要参数...");
        }

        cakeSupplementaryClickModel.setCreaterTime(new Date());
        cakeSupplementaryClickModel.setUpdateTime(new Date());
        cakeSupplementaryClickModel.setId(PrimaryUtil.getId());
        cakeSupplementaryClickModel.setState(SupplementaryClick.RUNNING);
        try {
            this.getDao().insert(cakeSupplementaryClickModel);
        } catch (Exception e) {
            logger.error("新增cake补点击失败", e);
            throw new BusinessException("新增cake补点击失败");
        }
    }


    /**
     * 修改cake补点击
     *
     * @param cakeSupplementaryClickModel 修改的内容
     * @throws BusinessException
     */
    @Override
    public int updateCakeSupplementaryClick(CakeSupplementaryClickModel cakeSupplementaryClickModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(cakeSupplementaryClickModel.getId())) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(cakeSupplementaryClickModel);
        } catch (Exception e) {
            logger.error("修改cake补点击信息失败", e);
            throw new BusinessException("修改cake补点击信息失败");
        }
    }

    /**
     * 给补点击开始进度+1
     *
     * @param execTime
     * @return
     * @throws BusinessException
     */
    @Override
    public int updateCakeSupplementaryClickScheduleStart(String execTime) throws BusinessException {
        try {
            if (BaseUtils.isBlank(execTime)) {
                throw new BusinessException("缺少必要参数...");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("EXEC_TIME", execTime + "%");
            params.put("STATE", SupplementaryClick.RUNNING.name());
            String sql = "UPDATE CAKE_SUPPLEMENTARY_CLICK SET TASK_SCHEDULE_START = TASK_SCHEDULE_START+1  WHERE CREATER_TIME LIKE  :EXEC_TIME AND STATE = :STATE AND TASK_SCHEDULE_START < TASK_SCHEDULE_END";
            return this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("修改cake补点击信息失败", e);
            throw new BusinessException("修改cake补点击信息失败");
        }
    }

    /**
     * 给补点击开始进度+1
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public int updateCakeSupplementaryClickScheduleStart(List<String> ids) throws BusinessException {

        if (ids == null || ids.size() == 0) {
            return 0;
        }

        try {
            Map<String, Object> params = new HashMap<>();

            params.put("IDS", ids);
            params.put("STATE", SupplementaryClick.RUNNING.name());

            String sql = "UPDATE CAKE_SUPPLEMENTARY_CLICK SET TASK_SCHEDULE_START = TASK_SCHEDULE_START+1  WHERE ID IN (:IDS) AND STATE = :STATE AND TASK_SCHEDULE_START < TASK_SCHEDULE_END";

            return this.getDao().exec(sql, params);

        } catch (Exception e) {
            logger.error("修改cake补点击信息失败", e);
            throw new BusinessException("修改cake补点击信息失败");
        }
    }

    /**
     * 给补点击结束进度+num
     *
     * @param taskListId
     * @return
     * @throws BusinessException
     */
    @Override
    public int updateCakeSupplementaryClickScheduleEnd(String taskListId) throws BusinessException {
        try {
            if (BaseUtils.isBlank(taskListId)) {
                throw new BusinessException("缺少必要参数...");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("TASK_LIST_ID", taskListId);
            String sql = "UPDATE CAKE_SUPPLEMENTARY_CLICK SET TASK_SCHEDULE_END = TASK_SCHEDULE_END + CONVERSION_RATE  WHERE TASK_LIST_ID = :TASK_LIST_ID";
            return this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("修改cake补点击信息失败", e);
            throw new BusinessException("修改cake补点击信息失败");
        }
    }

    /**
     * 修改状态
     *
     * @param taskIdList         需要被变更状态的id
     * @param supplementaryClick 状态
     * @return
     * @throws BusinessException
     */
    @Override
    public int updateCakeSupplementaryClickState(List<String> taskIdList, SupplementaryClick supplementaryClick) throws BusinessException {
        try {
            if (null == taskIdList || taskIdList.size() <= 0 || null == supplementaryClick) {
                throw new BusinessException("缺少必要参数...");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("TASK_LIST_ID", taskIdList);
            params.put("STATE", supplementaryClick.name());
            String sql = "UPDATE CAKE_SUPPLEMENTARY_CLICK SET STATE = :STATE  WHERE TASK_LIST_ID IN (:TASK_LIST_ID) ";
            return this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("修改cake补点击信息失败", e);
            throw new BusinessException("修改cake补点击信息失败");
        }
    }

    /**
     * 根据id删除某个cake补点击
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteCakeSupplementaryClick(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(CakeSupplementaryClickModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询cake补点击数据集
     *
     * @param cakeSupplementaryClickDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<CakeSupplementaryClickModel> selectCakeSupplementaryClicks(CakeSupplementaryClickDTO cakeSupplementaryClickDTO) throws BusinessException {
        String sql = " SELECT *,'CN' AS REGION FROM CAKE_SUPPLEMENTARY_CLICK WHERE 1=1 ";
        Map<String, Object> params = new HashMap<>();
        if (BaseUtils.isNotBlank(cakeSupplementaryClickDTO.getExecTime())) {
            sql += " AND CREATER_TIME LIKE :EXEC_TIME";
            params.put("EXEC_TIME", cakeSupplementaryClickDTO.getExecTime() + "%");
        }
        if (null != cakeSupplementaryClickDTO.getState()) {
            sql += " AND STATE = :STATE";
            params.put("STATE", cakeSupplementaryClickDTO.getState().name());
        }

        try {
            return this.getDao().selectList(CakeSupplementaryClickModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询cake补点击数据失败", e);
            throw new BusinessException("查询cake补点击数据失败");
        }
    }

    /**
     * 获得今日的任务
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public synchronized List<CakeSupplementaryClickBusinessDTO> getCakeSupplementaryClicks(String region) throws BusinessException {
        if (BaseUtils.isBlank(region)) {
            throw new BusinessException("缺少必要参数...");
        }
        String regionNew = region.toUpperCase();

        if (!regionNew.equals("CN")) {
            logger.info("-------{}-------", regionNew);
            return new ArrayList<CakeSupplementaryClickBusinessDTO>();
        }

        CakeSupplementaryClickDTO cakeSupplementaryClickDTO = new CakeSupplementaryClickDTO();
        String execTime = sdf.format(new Date());

        Map<String, Object> params = new HashMap<>();
        String sql = " SELECT ID as id,'1' AS analog_click ,ORIGINAL_ADVERTISEMENT_ID AS 'key',CREATER_TIME AS 'time'," +
                "CLICK_URL AS click_url,'" + region + "' AS region FROM CAKE_SUPPLEMENTARY_CLICK WHERE  CREATER_TIME LIKE :EXEC_TIME AND STATE = :STATE AND TASK_SCHEDULE_START < TASK_SCHEDULE_END GROUP BY ORIGINAL_ADVERTISEMENT_ID ORDER BY CREATER_TIME";

        params.put("EXEC_TIME", execTime + "%");
        params.put("STATE", SupplementaryClick.RUNNING.name());

        try {

            List<CakeSupplementaryClickBusinessDTO> cakeSupplementaryClickModels = this.getDao().selectList(CakeSupplementaryClickBusinessDTO.class, sql, params);

            List<String> ids = new ArrayList<>();

            for (CakeSupplementaryClickBusinessDTO cakeSupplementaryClickModel : cakeSupplementaryClickModels) {
                ids.add(cakeSupplementaryClickModel.getId());
            }

            updateCakeSupplementaryClickScheduleStart(ids);

            return cakeSupplementaryClickModels;
        } catch (Exception e) {
            logger.error("获得今日补量失败", e);
            throw new BusinessException("获得今日补量失败");
        }
    }


}
