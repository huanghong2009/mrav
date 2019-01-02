
package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.ms.youjia.model.CakeDockModel;
import com.mobcolor.ms.youjia.model.dto.CakeDockDTO;
import com.mobcolor.ms.youjia.service.CakeDockService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-01-11 14:48:58
 */
@Service
public class CakeDockServiceImpl extends BaseSupportServiceImpl implements CakeDockService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(CakeDockServiceImpl.class);

    /**
     * 新增一个cake对接
     *
     * @param cakeDockModel cake对接
     */
    public void addCakeDock(CakeDockModel cakeDockModel) {
        if ( BaseUtils.isBlank(cakeDockModel.getId()) || BaseUtils.isBlank(cakeDockModel.getTaskDetailId())) {
            throw new BusinessException("缺少必要参数idfa|任务id...");
        }
        String sql = "INSERT INTO CAKE_DOCK (ID,TASK_DETAIL_ID,CREATER_TIME) VALUES(:ID,:TASK_DETAIL_ID,:CREATER_TIME)";
        Map<String, Object> params = new HashMap<>();
        params.put("ID", cakeDockModel.getId());
        params.put("TASK_DETAIL_ID", cakeDockModel.getTaskDetailId());
        params.put("CREATER_TIME", new Date());
        try {
            this.getDao().exec(sql,params);
        } catch (Exception e) {
            logger.error("新增cake对接失败", e);
            if ( null!= loadCakeDock(cakeDockModel.getId()) ) {
                updateCakeDock(cakeDockModel.getId(), cakeDockModel.getTaskDetailId());
                return;
            }
            throw new BusinessException("新增cake对接失败");
        }
    }

    /**
     * 分页查询cake对接
     *
     * @param cakeDockDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<CakeDockModel> queryCakeDockByPage(CakeDockDTO cakeDockDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM CAKE_DOCK WHERE 1=1 ";

            if (null != cakeDockDTO.getId()) {
                sql += " AND ID = ?";
                params.add(cakeDockDTO.getId());
            }

            if (BaseUtils.isNotBlank(cakeDockDTO.getTaskDetailId())) {
                sql += " AND TASK_DETAIL_ID = ?";
                params.add(cakeDockDTO.getId());
            }

            PageVO<CakeDockModel> cakeDockModelPageVO = this.getDao().pageQuery(CakeDockModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    cakeDockDTO.getTo_page(), cakeDockDTO.getPage_size());
            return cakeDockModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询cake对接失败", e);
            throw new BusinessException("分页查询cake对接失败");
        }

    }

    /**
     * 修改cake对接
     *
     * @param id      主键id
     * @param taskDetailId 任务id
     * @throws BusinessException
     */
    @Override
    public int updateCakeDock(String id, String taskDetailId) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id) || BaseUtils.isBlank(taskDetailId)) {
                throw new BusinessException("缺少必要参数...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("TASK_DETAIL_ID", taskDetailId);
            return this.getDao().exec("UPDATE CAKE_DOCK SET TASK_DETAIL_ID = :TASK_DETAIL_ID WHERE ID = :ID", params);
        } catch (Exception e) {
            logger.error("修改cake对接信息失败", e);
            throw new BusinessException("修改cake对接信息失败");
        }
    }

    /**
     * 根据id删除某个cake对接
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteCakeDock(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(CakeDockModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    @Override
    public CakeDockModel loadCakeDock(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().load(CakeDockModel.class, id);
        } catch (Exception e) {
            logger.error("加载数据失败", e);
            throw new BusinessException("加载数据失败");
        }
    }


}
