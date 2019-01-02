
package com.mobcolor.ms.youjia.service.impl;


import com.google.common.collect.Lists;
import com.mobcolor.framework.utils.*;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.SupplementaryClickRuleModel;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.service.SupplementaryClickRuleService;
import com.mobcolor.ms.youjia.enums.CountType;
import com.mobcolor.ms.youjia.enums.PatchClicksType;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
import com.mobcolor.ms.youjia.model.TaskDetailModel;
import com.mobcolor.ms.youjia.model.dto.TaskCountDTO;
import com.mobcolor.ms.youjia.model.dto.TaskDetailDTO;
import com.mobcolor.ms.youjia.service.TaskDetailService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017-12-26 14:32:28
 */
@Service
public class TaskDetailServiceImpl extends BaseSupportServiceImpl implements TaskDetailService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(TaskDetailServiceImpl.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private SupplementaryClickRuleService supplementaryClickRuleService;

    /**
     * 新增一个任务详情
     *
     * @param taskDetailModel 任务详情
     */
    public void addTaskDetail(TaskDetailModel taskDetailModel) {
        if (BaseUtils.isBlank(taskDetailModel.getTaskForcetId())
                || BaseUtils.isBlank(taskDetailModel.getTaskListId())
                || BaseUtils.isBlank(taskDetailModel.getAppName())
                || null == taskDetailModel.getType()
                || BaseUtils.isBlank(taskDetailModel.getScriptNames())) {
            throw new BusinessException("缺少必要参数(名称，类型，平台，内容)...");
        }
        taskDetailModel.setCreaterTime(new Date());
        taskDetailModel.setId(PrimaryUtil.getId());
        taskDetailModel.setState(TaskDetail.WAIT);
        taskDetailModel.setIsCallBcak(String.valueOf(false));
        try {
            this.getDao().insert(taskDetailModel);
        } catch (Exception e) {
            logger.error("新增任务详情失败", e);
            throw new BusinessException("新增任务详情失败");
        }
    }

    @Override
    public void addBatchTaskDetail(List<TaskDetailModel> taskDetailModelList) throws BusinessException {
        try {
            this.getDao().insertBatch(taskDetailModelList);
        } catch (Exception e) {
            logger.error("新增批量任务详情失败", e);
            throw new BusinessException("新增批量任务详情失败");
        }
    }

    /**
     * 分页查询任务详情
     *
     * @param taskDetailDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<TaskDetailModel> queryTaskDetails(TaskDetailDTO taskDetailDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM TASK_DETAIL WHERE 1=1 ";

            if (BaseUtils.isNotBlank(taskDetailDTO.getTaskForcetId())) {
                sql += " AND TASK_FORCE_ID = ?";
                params.add(taskDetailDTO.getTaskForcetId());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getTaskListId())) {
                sql += " AND TASK_LIST_ID = ?";
                params.add(taskDetailDTO.getTaskListId());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getIsCallBcak())) {
                sql += " AND IS_CALL_BACK = ?";
                params.add(taskDetailDTO.getIsCallBcak());
            }

            if (null != taskDetailDTO.getType()) {
                sql += " AND TYPE = ?";
                params.add(taskDetailDTO.getType().name());
            }

            if (null != taskDetailDTO.getState()) {
                sql += " AND STATE = ?";
                params.add(taskDetailDTO.getState().name());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getCreaterTime())) {
                sql += " AND CREATER_TIME LIKE ? ";
                params.add(taskDetailDTO.getCreaterTime() + "%");
            }


            return this.getDao().selectList(TaskDetailModel.class, sql, params.toArray());
        } catch (Exception e) {
            logger.error("查询任务详情失败", e);
            throw new BusinessException("查询任务详情失败");
        }

    }

    @Override
    public List<TaskDetailModel> queryTaskDetailByListId(List<String> ids) throws BusinessException {
        if (null == ids || ids.size() == 0) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            Map<String, Object> params = new HashMap<>();

            String sql = "SELECT * FROM TASK_DETAIL WHERE TASK_LIST_ID IN (:ID) AND STATE IN('RUNNING','SUSPEND','WAIT')";

            List<List<String>> datas = Lists.partition(ids, 500);

            List<TaskDetailModel> result = new ArrayList<>();
            for (List<String> data : datas) {
                params.put("ID", data);
                result.addAll(this.getDao().selectList(TaskDetailModel.class, sql, params));
            }

            return result;
        } catch (Exception e) {
            logger.error("查询任务详情失败", e);
            throw new BusinessException("查询任务详情失败");
        }
    }

    /**
     * 统计任务详细
     *
     * @param taskDetailDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<TaskCountDTO> countTaskDetailsByPage(TaskDetailDTO taskDetailDTO) throws BusinessException {
        try {
            List<String> params = new ArrayList<String>();

            StringBuffer sql = new StringBuffer("SELECT a.filed,a.num ,b.num as callBackNum FROM ( ");

            countQueryParames(sql, params, taskDetailDTO);

            sql.append(" GROUP BY STATE ) a LEFT JOIN ( ");

            countQueryParames(sql, params, taskDetailDTO);

            sql.append(" AND IS_CALL_BACK = 'Y'  GROUP BY STATE ) b on a.filed = b.filed");

            String sql1 = new String(sql);

            return this.getDao().pageQuery(TaskCountDTO.class, sql1,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    taskDetailDTO.getTo_page(), taskDetailDTO.getPage_size());
        } catch (Exception e) {
            logger.error("查询任务详情失败", e);
            throw new BusinessException("查询任务详情失败");
        }
    }

    private void countQueryParames(StringBuffer sql, List<String> params, TaskDetailDTO taskDetailDTO) throws BusinessException {
        sql.append("SELECT COUNT(ID) AS num,STATE AS filed FROM TASK_DETAIL WHERE 1=1 ");

        if (BaseUtils.isNotBlank(taskDetailDTO.getTaskListId())) {
            sql.append(" AND TASK_LIST_ID = ?");
            params.add(taskDetailDTO.getTaskListId());
        }

        if (BaseUtils.isNotBlank(taskDetailDTO.getName())) {
            sql.append(" AND  APP_NAME LIKE ? ");
            params.add(taskDetailDTO.getName() + "%");
        }

        if (null == taskDetailDTO.getCountType() || taskDetailDTO.getCountType().equals(CountType.DAY)) {
            sql.append(" AND CREATER_TIME BETWEEN ?  AND ? ");
            String time = simpleDateFormat.format(new Date());
            params.add(time + " 00:00:00");
            params.add(time + " 23:59:59");
        } else if (taskDetailDTO.getCountType().equals(CountType.WEEK)) {
            sql.append(" AND CREATER_TIME BETWEEN ?  AND ? ");
            params.add(sdf1.format(DateUtils.getBeginDayOfWeek()));
            params.add(sdf1.format(DateUtils.getEndDayOfWeek()));
        } else if (taskDetailDTO.getCountType().equals(CountType.MONTH)) {
            sql.append(" AND CREATER_TIME BETWEEN ?  AND ? ");
            params.add(sdf1.format(DateUtils.getBeginDayOfMonth()));
            params.add(sdf1.format(DateUtils.getEndDayOfMonth()));
        } else {
            if (BaseUtils.isNotBlank(taskDetailDTO.getStartTime()) && BaseUtils.isNotBlank(taskDetailDTO.getEndTime())) {
                sql.append(" AND CREATER_TIME BETWEEN ?  AND ? ");
                params.add(taskDetailDTO.getStartTime() + " 00:00:00");
                params.add(taskDetailDTO.getEndTime() + " 23:59:59");
            } else if (BaseUtils.isNotBlank(taskDetailDTO.getStartTime())) {
                sql.append(" AND CREATER_TIME > ? ");
                params.add(taskDetailDTO.getStartTime() + " 00:00:00");
            } else if (BaseUtils.isNotBlank(taskDetailDTO.getEndTime())) {
                sql.append(" AND CREATER_TIME < ? ");
                params.add(taskDetailDTO.getEndTime() + " 23:59:59");
            } else {
                throw new BusinessException("缺少必要参数:自定义时间参数为空");
            }
        }
    }

    /**
     * 统计条数
     *
     * @param taskDetailDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public Long queryCountTaskDetails(TaskDetailDTO taskDetailDTO) throws BusinessException {
        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT COUNT(*) FROM TASK_DETAIL WHERE 1=1 ";

            if (BaseUtils.isNotBlank(taskDetailDTO.getTaskForcetId())) {
                sql += " AND TASK_FORCE_ID = ?";
                params.add(taskDetailDTO.getTaskForcetId());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getTaskListId())) {
                sql += " AND TASK_LIST_ID = ?";
                params.add(taskDetailDTO.getTaskListId());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getIsCallBcak())) {
                sql += " AND IS_CALL_BACK = ?";
                params.add(taskDetailDTO.getIsCallBcak());
            }

            if (null != taskDetailDTO.getType()) {
                sql += " AND TYPE = ?";
                params.add(taskDetailDTO.getType().name());
            }

            if (null != taskDetailDTO.getState()) {
                sql += " AND STATE = ?";
                params.add(taskDetailDTO.getState().name());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getCreaterTime())) {
                sql += " AND CREATER_TIME LIKE ? ";
                params.add(taskDetailDTO.getCreaterTime() + "%");
            }

            return this.getDao().selectOne(Long.class, sql, params.toArray());
        } catch (Exception e) {
            logger.error("查询任务详情失败", e);
            throw new BusinessException("查询任务详情失败");
        }
    }

    @Override
    public List<TaskDetailModel> queryUntreatedTaskDetails(String createrTime) throws BusinessException {
        if (BaseUtils.isBlank(createrTime)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM TASK_DETAIL WHERE CREATER_TIME  BETWEEN ?  AND ? AND STATE IN ('RUNNING','SUSPEND')";
            params.add(createrTime + " 00:00:00");
            params.add(createrTime + " 23:59:59");
            return this.getDao().selectList(TaskDetailModel.class, sql, params.toArray());
        } catch (Exception e) {
            logger.error("查询任务详情失败", e);
            throw new BusinessException("查询任务详情失败");
        }
    }

    @Override
    public PageVO<TaskDetailModel> queryTaskDetailsByPage(TaskDetailDTO taskDetailDTO) throws BusinessException {
        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM TASK_DETAIL WHERE 1=1 ";

            if (BaseUtils.isNotBlank(taskDetailDTO.getTaskForcetId())) {
                sql += " AND TASK_FORCE_ID = ?";
                params.add(taskDetailDTO.getTaskForcetId());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getTaskListId())) {
                sql += " AND TASK_LIST_ID = ?";
                params.add(taskDetailDTO.getTaskListId());
            }

            if (null != taskDetailDTO.getType()) {
                sql += " AND TYPE = ?";
                params.add(taskDetailDTO.getType().name());
            }

            if (null != taskDetailDTO.getState()) {
                sql += " AND STATE = ?";
                params.add(taskDetailDTO.getState().name());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getIsCallBcak())) {
                sql += " AND IS_CALL_BACK = ?";
                params.add(taskDetailDTO.getIsCallBcak());
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getName())) {
                sql += " AND ( APP_NAME LIKE ? OR  PLATFORM_CHANNEL_NAME LIKE ?)";
                params.add("%" + taskDetailDTO.getName() + "%");
                params.add("%" + taskDetailDTO.getName() + "%");
            }

            if (BaseUtils.isNotBlank(taskDetailDTO.getCreaterTime())) {
                sql += " AND CREATER_TIME BETWEEN ?  AND ? ";
                params.add(taskDetailDTO.getCreaterTime() + " 00:00:00");
                params.add(taskDetailDTO.getCreaterTime() + " 23:59:59");
            }

            return this.getDao().pageQuery(TaskDetailModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    taskDetailDTO.getTo_page(), taskDetailDTO.getPage_size());

        } catch (Exception e) {
            logger.error("查询任务详情失败", e);
            throw new BusinessException("查询任务详情失败");
        }
    }

    /**
     * 修改任务详情
     *
     * @param taskDetailModel
     * @throws BusinessException
     */
    @Override
    public int updateTaskDetail(TaskDetailModel taskDetailModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(taskDetailModel.getId())) {
                logger.info("缺少必要参数{}", taskDetailModel);
                throw new BusinessException("缺少必要参数...");
            }

            taskDetailModel.setTaskForcetId(null);
            taskDetailModel.setTaskListId(null);
            taskDetailModel.setAppName(null);
            taskDetailModel.setServerConfig(null);
            taskDetailModel.setType(null);
            taskDetailModel.setScriptNames(null);
            taskDetailModel.setCreaterTime(null);
            taskDetailModel.setUpdateTime(new Date());
            TaskDetailModel taskDetailModel1 = this.getDao().load(TaskDetailModel.class, taskDetailModel.getId());

            if (taskDetailModel1.getType().equals(TaskList.ADD)) {
                if (BaseUtils.isNotBlank(taskDetailModel.getIsCallBcak()) && taskDetailModel.getIsCallBcak().equals("Y")) {
                    if (taskDetailModel1.getPatchClicksType().equals(PatchClicksType.ADVERTISER_CALL_BACK)) {
                        patchClick(taskDetailModel1);
                    }

                } else {
                    if (taskDetailModel.getState().equals(TaskDetail.SUCCEED) && taskDetailModel1.getPatchClicksType().equals(PatchClicksType.DEVICE_CALL_BACK)) {
                        patchClick(taskDetailModel1);
                    }
                }
            }

            return this.getDao().update(taskDetailModel);
        } catch (Exception e) {
            logger.error("修改任务详情信息失败", e);
            throw new BusinessException("修改任务详情信息失败");
        }
    }


    /**
     * 补点击
     *
     * @param taskDetailModel
     * @throws BusinessException
     */
    private void patchClick(TaskDetailModel taskDetailModel) throws BusinessException {
        SupplementaryClickRuleModel supplementaryClickRuleModel = supplementaryClickRuleService.loadSupplementaryClickRules(taskDetailModel.getClickRulesId());
        String[] init = supplementaryClickRuleModel.getCallBackMethod().split(":");
        try {
            Object bean = this.getApplicationContext().getBean(init[0]);
            Method method = bean.getClass().getMethod(init[1], String.class);
            method.invoke(bean, taskDetailModel.getTaskListId());
        } catch (Exception e) {
            logger.error("修改补量-未找到初始化方法...", e);
            throw new BusinessException("修改补量-未找到初始化方法...");
        }
    }


    /**
     * 根据id删除某个任务详情
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteTaskDetail(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(TaskDetailModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    @Override
    public int deleteTaskDetailByListId(String listId) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("TASK_LIST_ID", listId);
            String sql = "DELETE FROM  TASK_DETAIL  WHERE TASK_LIST_ID = :TASK_LIST_ID ";
            return this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("删除列表失败", e);
            throw new BusinessException("删除列表失败");
        }
    }

    /**
     * 修改任务状态（批量启用或停用）
     *
     * @param taskListId
     * @param state
     * @return
     * @throws BusinessException
     */
    @Override
    public int updateTaskDetailStateById(String taskListId, TaskDetail state) throws BusinessException {
        if (BaseUtils.isBlank(taskListId) || null == state) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("TASK_LIST_ID", taskListId);
            params.put("STATE", state.name());
            String sql = "UPDATE TASK_DETAIL SET STATE = :STATE WHERE TASK_LIST_ID = :TASK_LIST_ID  AND STATE IN ('RUNNING','SUSPEND','WAIT') ";
            return this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("修改任务状态失败", e);
            throw new BusinessException("修改任务状态失败");
        }
    }

    @Override
    public int updateTodayTaskDetailState(TaskDetail state) throws BusinessException {
        if (null == state) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("STATE", state.name());
            params.put("START", sdf.format(new Date()) + " 00:00:00");
            params.put("END", sdf.format(new Date()) + " 23:59:59");

            String sql = "UPDATE TASK_DETAIL SET STATE = :STATE WHERE CREATER_TIME BETWEEN :START AND :END  AND STATE IN ('RUNNING','SUSPEND','WAIT') ";
            return this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("修改任务状态失败", e);
            throw new BusinessException("修改任务状态失败");
        }
    }

    @Override
    public int updateTaskDetailStateById(List<String> taskListIds, TaskDetail state) throws BusinessException {
        if (null == taskListIds || taskListIds.size() == 0 || null == state) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            Map<String, Object> params = new HashMap<>();

            params.put("STATE", state.name());
            String sql = "UPDATE TASK_DETAIL SET STATE = :STATE WHERE TASK_LIST_ID IN (:TASK_LIST_ID)  AND STATE IN ('RUNNING','SUSPEND','WAIT') ";

            List<List<String>> datas = Lists.partition(taskListIds, 500);

            int num = 0;
            for (List<String> data : datas) {
                params.put("TASK_LIST_ID", data);
                num += this.getDao().exec(sql, params);
            }

            return num;
        } catch (Exception e) {
            logger.error("修改任务状态失败", e);
            throw new BusinessException("修改任务状态失败");
        }
    }


    @Override
    public TaskDetailModel load(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().load(TaskDetailModel.class, id);
        } catch (Exception e) {
            logger.error("获取指定id数据不存在", e);
            throw new BusinessException("获取指定id数据不存在");
        }
    }


}
