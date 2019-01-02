package com.mobcolor.ms.youjia.service.impl;


import com.mobcolor.ms.youjia.model.AppManageModel;
import com.mobcolor.ms.youjia.model.dto.AppManageDTO;
import com.mobcolor.ms.youjia.service.AppManageService;
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
 * @version 创建时间：2017/12/19
 */
@Service
public class AppManageServiceImpl extends BaseSupportServiceImpl implements AppManageService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AppManageServiceImpl.class);

    /**
     * 新增一个应用
     *
     * @param appManageModel 应用名称
     */
    public void addAppManage(AppManageModel appManageModel) {
        if (BaseUtils.isBlank(appManageModel.getName())
                || BaseUtils.isBlank(appManageModel.getBundleId())) {
            throw new BusinessException("缺少必要参数...");
        }
        appManageModel.setCreaterTime(new Date());
        appManageModel.setId(PrimaryUtil.getId());
        try {
            this.getDao().insert(appManageModel);
        } catch (Exception e) {
            logger.error("新增应用", e);
            throw new BusinessException("新增应用");
        }
    }

    /**
     * 分页查询应用
     *
     * @param appManageDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<AppManageModel> queryAppManageByPage(AppManageDTO appManageDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM APP_MANAGE WHERE 1=1 ";

            if (BaseUtils.isNotBlank(appManageDTO.getName())) {
                sql += " AND ( BUNDLE_ID LIKE ?  OR NAME LIKE ?)";
                params.add("%" + appManageDTO.getName() + "%");
                params.add("%" + appManageDTO.getName() + "%");
            }

            PageVO<AppManageModel> appManageModelPageVO = this.getDao().pageQuery(AppManageModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    appManageDTO.getTo_page(), appManageDTO.getPage_size());
            return appManageModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询应用失败", e);
            throw new BusinessException("分页查询应用失败");
        }

    }

    /**
     * 修改应用
     *
     * @param appManageModel 应用名称
     * @throws BusinessException
     */
    @Override
    public int updateAppManage(AppManageModel appManageModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(appManageModel.getId())) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(appManageModel);
        } catch (Exception e) {
            logger.error("修改应用信息失败", e);
            throw new BusinessException("修改应用信息失败");
        }
    }

    /**
     * 根据id删除某个应用
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteAppManage(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().delete(AppManageModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }


}
