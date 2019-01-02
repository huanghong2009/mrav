
package com.mobcolor.ms.youjia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobcolor.ms.youjia.model.AdvertisementModel;
import com.mobcolor.ms.youjia.model.dto.AdvertisementDTO;
import com.mobcolor.ms.youjia.service.AdvertisementService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-01-05 14:12:35
 */
@Service
public class AdvertisementServiceImpl extends BaseSupportServiceImpl implements AdvertisementService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AdvertisementServiceImpl.class);

    /**
     * 新增一个广告
     *
     * @param advertisementModel 广告
     */
    public void addAdvertisement(AdvertisementModel advertisementModel) {
        if (BaseUtils.isBlank(advertisementModel.getAppName()) ||
                BaseUtils.isBlank(advertisementModel.getPlatformChannelId()) ||
                BaseUtils.isBlank(advertisementModel.getPlatformChannelName()) ||
                BaseUtils.isBlank(advertisementModel.getOriginalAdvertisementId()) ||
                BaseUtils.isBlank(advertisementModel.getScriptNames()) ||
                BaseUtils.isBlank(advertisementModel.getTaskLists()) ||
                BaseUtils.isBlank(advertisementModel.getClickRulesId()) ||
                null == advertisementModel.getMinClickNum() ||
                null == advertisementModel.getConversionRate() ||
                BaseUtils.isBlank(advertisementModel.getAddExecGroup()) ||
                BaseUtils.isBlank(advertisementModel.getRetainedExecGroup()) ||
                BaseUtils.isBlank(advertisementModel.getIsNeedRepeatedly())
                ) {
            throw new BusinessException("缺少必要参数(应用名称，渠道id，渠道名称,原广告id,关联脚本,新增执行组,留存执行组)...");
        }

        if (advertisementModel.getIsNeedRepeatedly().equals("Y") && BaseUtils.isBlank(advertisementModel.getRepeatedlyConfig()) && BaseUtils.isBlank(advertisementModel.getRepeatedlyDays())) {
            throw new BusinessException("缺少必要参数(多次唤醒配置参数)...");
        }

        String[] days = advertisementModel.getRepeatedlyDays().split("-");

        if (days.length != 2) {
            logger.error("多次唤醒配置参数格式错误:天数格式错误");
            throw new BusinessException("多次唤醒配置参数格式错误:天数格式错误");
        }

        try {
            for (String day : days) {
                int i = Integer.parseInt(day);
                if (i < 0 || i > 10) {
                    throw new Exception();
                }
            }

            if (Integer.parseInt(days[0]) > Integer.parseInt(days[1])) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error("多次唤醒配置参数格式错误:天数格式错误");
            throw new BusinessException("多次唤醒配置参数格式错误:天数格式错误");
        }

        JSONArray repeatedlyConfig = null;

        try {
            repeatedlyConfig = JSONArray.parseArray(advertisementModel.getRepeatedlyConfig());
        } catch (Exception e) {
            logger.error("多次唤醒配置参数格式错误：多次唤醒模版错误", e);
            throw new BusinessException("多次唤醒配置参数格式错误：多次唤醒模版错误");
        }

        if (repeatedlyConfig.size() < Integer.parseInt(days[1])) {
            logger.error("多次唤醒配置参数格式错误：模版数组数量小于天数");
            throw new BusinessException("多次唤醒配置参数格式错误：模版数组数量小于天数");
        }

        for (Object o : repeatedlyConfig) {
            String[] config = String.valueOf(o).split("-");
            if (config.length != 2) {
                logger.error("多次唤醒配置参数格式错误：多次唤醒模版错误");
                throw new BusinessException("多次唤醒配置参数格式错误：多次唤醒模版错误");
            } else if (Integer.parseInt(config[0]) > Integer.parseInt(config[1])) {
                logger.error("多次唤醒配置参数格式错误：多次唤醒模版错误");
                throw new BusinessException("多次唤醒配置参数格式错误：多次唤醒模版错误");
            }
        }

        try {
            JSONArray tasks = JSONArray.parseArray(advertisementModel.getTaskLists());
        } catch (Exception e) {
            logger.error("留存模版json格式错误", e);
            throw new BusinessException("留存模版json格式错误");
        }

        advertisementModel.setCreaterTime(new Date());
        advertisementModel.setId(PrimaryUtil.getId());

        try {
            this.getDao().insert(advertisementModel);
        } catch (Exception e) {
            logger.error("新增广告失败", e);
            throw new BusinessException("新增广告失败");
        }
    }

    /**
     * 分页查询广告
     *
     * @param advertisementDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<AdvertisementModel> queryAdvertisementByPage(AdvertisementDTO advertisementDTO) throws BusinessException {
        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM ADVERTISEMENT WHERE 1=1 ";

            if (null != advertisementDTO.getOriginalAdvertisementId()) {
                sql += " AND ORIGINAL_ADVERTISEMENT_ID = ?";
                params.add(advertisementDTO.getOriginalAdvertisementId());
            }

            if (BaseUtils.isNotBlank(advertisementDTO.getName())) {
                sql += " AND ( APP_NAME LIKE ?  OR  PLATFORM_CHANNEL_NAME LIKE ? )";
                params.add("%" + advertisementDTO.getName() + "%");
                params.add("%" + advertisementDTO.getName() + "%");
            }


            PageVO<AdvertisementModel> advertisementModelPageVO = this.getDao().pageQuery(AdvertisementModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    advertisementDTO.getTo_page(), advertisementDTO.getPage_size());
            return advertisementModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询广告失败", e);
            throw new BusinessException("分页查询广告失败");
        }

    }

    /**
     * 修改广告
     *
     * @param advertisementModel 广告
     * @throws BusinessException
     */
    @Override
    public int updateAdvertisement(AdvertisementModel advertisementModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(advertisementModel.getId())) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(advertisementModel);
        } catch (Exception e) {
            logger.error("修改广告信息失败", e);
            throw new BusinessException("修改广告信息失败");
        }
    }

    /**
     * 根据id删除某个广告
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteAdvertisement(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(AdvertisementModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 加载一条数据
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public AdvertisementModel loadAdvertisement(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().load(AdvertisementModel.class, id);
        } catch (Exception e) {
            logger.error("加载数据失败", e);
            throw new BusinessException("加载数据失败");
        }
    }


    public static void main(String[] args) {
        String str = "['41-25','25-36']";
        JSONArray objects = JSONArray.parseArray(str);
        String[] config = String.valueOf(objects.get(0)).split("-");
        logger.info(config.toString());
    }
}
