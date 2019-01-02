
package com.mobcolor.ms.youjia.service.impl;


import com.mobcolor.ms.youjia.enums.TaskForcet;
import com.mobcolor.ms.youjia.model.TaskForcetModel;
import com.mobcolor.ms.youjia.model.dto.TaskForcetDTO;
import com.mobcolor.ms.youjia.service.AdvertisementService;
import com.mobcolor.ms.youjia.service.TaskForcetService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017-12-26 13:24:34
 */
@Service
public class TaskForcetServiceImpl extends BaseSupportServiceImpl implements TaskForcetService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(TaskForcetServiceImpl.class);

    @Resource
    private AdvertisementService advertisementService;

    /**
     * 新增一个任务组
     *
     * @param taskForcetModel 任务组
     */
    public void addTaskForcet(TaskForcetModel taskForcetModel) {
        if (BaseUtils.isBlank(taskForcetModel.getScriptNames())  ||
                BaseUtils.isBlank(taskForcetModel.getAppName()) ||
                BaseUtils.isBlank(taskForcetModel.getOriginalAdvertisementId()) ||
                BaseUtils.isBlank(taskForcetModel.getPlatformChannelId()) ||
                BaseUtils.isBlank(taskForcetModel.getPlatformChannelName())
                ) {
            throw new BusinessException("缺少必要参数(广告id)...");
        }
        if (BaseUtils.isBlank(taskForcetModel.getId())){
            taskForcetModel.setId(PrimaryUtil.getId());
        }
        try {
            taskForcetModel.setCreaterTime(new Date());
            taskForcetModel.setState(TaskForcet.EXECUTION_WAIT);
            this.getDao().insert(taskForcetModel);
        } catch (Exception e) {
            logger.error("新增任务组失败", e);
            throw new BusinessException("新增任务组失败");
        }
    }


    /**
     * 分页查询任务组
     *
     * @param taskForcetDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<TaskForcetModel> queryTaskForcetByPage(TaskForcetDTO taskForcetDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM TASK_FORCE WHERE 1=1 ";

            if (null != taskForcetDTO.getOriginalAdvertisementId()) {
                sql += " AND ORIGINAL_ADVERTISEMENT_ID = ?";
                params.add(taskForcetDTO.getOriginalAdvertisementId());
            }

            if (BaseUtils.isNotBlank(taskForcetDTO.getName())) {
                sql += " AND ( APP_NAME LIKE ? OR PLATFORM_CHANNEL_NAME LIKE ? ) ";
                params.add("%" + taskForcetDTO.getName() + "%");
                params.add("%" + taskForcetDTO.getName() + "%");
            }

            sql += " ORDER BY CREATER_TIME DESC ";

            PageVO<TaskForcetModel> taskForcetModelPageVO = this.getDao().pageQuery(TaskForcetModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    taskForcetDTO.getTo_page(), taskForcetDTO.getPage_size());
            return taskForcetModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询任务组失败", e);
            throw new BusinessException("分页查询任务组失败");
        }

    }

    /**
     * 修改任务组
     *
     * @param id      主键id
     * @param scriptNames 关联脚本
     * @throws BusinessException
     */
    @Override
    public int updateTaskForcet(String id, String scriptNames) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id) || BaseUtils.isBlank(scriptNames)) {
                throw new BusinessException("缺少必要参数...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("SCRIPT_NAMES", scriptNames);
            return this.getDao().exec("UPDATE TASK_FORCE SET SCRIPT_NAMES = :SCRIPT_NAMES WHERE ID = :ID", params);
        } catch (Exception e) {
            logger.error("修改任务组信息失败", e);
            throw new BusinessException("修改任务组信息失败");
        }
    }

    /**
     * 根据id删除某个任务组
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteTaskForcet(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(TaskForcetModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    @Override
    public TaskForcetModel getTaskForcetService(String id) throws BusinessException {
        try {
            return  this.getDao().load(TaskForcetModel.class,id);
        } catch (Exception e) {
            logger.error("获取数据失败", e);
            throw new BusinessException("获取数据失败");
        }
    }


}
