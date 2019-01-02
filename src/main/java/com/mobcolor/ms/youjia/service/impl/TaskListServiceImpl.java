
package com.mobcolor.ms.youjia.service.impl;

import com.google.common.collect.Lists;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
import com.mobcolor.ms.youjia.model.TaskForcetModel;
import com.mobcolor.ms.youjia.model.TaskListModel;
import com.mobcolor.ms.youjia.model.dto.TaskListDTO;
import com.mobcolor.ms.youjia.service.TaskListService;
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
 * @version 创建时间：2017-12-26 13:52:46
 */
@Service
public class TaskListServiceImpl extends BaseSupportServiceImpl implements TaskListService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(TaskListServiceImpl.class);

    /**
     * 新增一个任务记录
     *
     * @param taskListModel 任务记录
     */
    public void addTaskList(TaskListModel taskListModel) {
        if (BaseUtils.isBlank(taskListModel.getTaskForcetId())
                || null == taskListModel.getType()
                || taskListModel.getNum() == 0
                || null == taskListModel.getExecTime()) {
            throw new BusinessException("缺少必要参数(任务组id，类型，数量,执行时间)或参数不合法...");
        }
        taskListModel.setCreaterTime(new Date());
        taskListModel.setId(PrimaryUtil.getId());
        try {
            this.getDao().insert(taskListModel);
        } catch (Exception e) {
            logger.error("新增任务记录失败", e);
            throw new BusinessException("新增任务记录失败");
        }
    }

    @Override
    public void addBatchTaskList(List<TaskListModel> taskListModels) throws BusinessException {
        try {
            this.getDao().insertBatch(taskListModels);
        } catch (Exception e) {
            logger.error("批量新增任务记录失败", e);
            throw new BusinessException("批量新增任务记录失败");
        }
    }

    /**
     * 分页查询任务记录
     *
     * @param taskListDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<TaskListModel> queryTaskList(TaskListDTO taskListDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM TASK_LIST WHERE 1=1 ";

            if (BaseUtils.isNotBlank(taskListDTO.getTaskForcetId())) {
                sql += " AND TASK_FORCE_ID = ?";
                params.add(taskListDTO.getTaskForcetId());
            }
            if (BaseUtils.isNotBlank(taskListDTO.getExecTime())) {
                sql += " AND EXEC_TIME = ?";
                params.add(taskListDTO.getExecTime());
            }

            if (BaseUtils.isNotBlank(taskListDTO.getIsCreate())) {
                sql += " AND IS_CREATE = ?";
                params.add(taskListDTO.getIsCreate());
            }

            if (BaseUtils.isNotBlank(taskListDTO.getIsQueryHaving()) && taskListDTO.getIsQueryHaving().equals("Y")){
                sql += " AND TASK_SCHEDULE_START < TASK_SCHEDULE_END";
            }

            if (BaseUtils.isNotBlank(taskListDTO.getIsQueryAll()) && taskListDTO.getIsQueryAll().equals("Y")){
                return this.getDao().selectList(TaskListModel.class, sql, params.toArray());
            }else {
                if (BaseUtils.isNotBlank(taskListDTO.getIsRepeatedly()) && taskListDTO.getIsRepeatedly().equals("Y")) {
                    sql += " AND ( REPEATEDLY_CORRELATION_ID IS NOT NULL OR REPEATEDLY_CORRELATION_ID != '')  GROUP BY TASK_LIST_ID ORDER BY REPEATEDLY_INDEX ";
                } else {
                    sql += " AND ( REPEATEDLY_CORRELATION_ID IS NULL OR REPEATEDLY_CORRELATION_ID = '')";
                }
            }

            return this.getDao().selectList(TaskListModel.class, sql, params.toArray());
        } catch (Exception e) {
            logger.error("查询任务记录失败", e);
            throw new BusinessException("查询任务记录失败");
        }

    }



    /**
     * 根据id查询任务
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public List<TaskListModel> queryTaskListByIds(List<String> ids) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("IDS", ids);
            String sql = "SELECT * FROM TASK_LIST WHERE ID IN (:IDS) ";
            return this.getDao().selectList(TaskListModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询任务记录失败", e);
            throw new BusinessException("查询任务记录失败");
        }
    }

    @Override
    public List<TaskListModel> queryTodayTaskList() throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("EXEC_TIME", sdf.format(new Date()));
            params.put("TYPE", TaskList.ADD.name());
            String sql = "SELECT * FROM TASK_LIST WHERE EXEC_TIME = :EXEC_TIME  AND TYPE = :TYPE ";
            return this.getDao().selectList(TaskListModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询任务记录失败", e);
            throw new BusinessException("查询任务记录失败");
        }
    }

    /**
     * 分页查询任务记录
     *
     * @param taskListDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<TaskListModel> queryTaskListByPage(TaskListDTO taskListDTO) throws BusinessException {
        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM TASK_LIST WHERE 1=1 ";

            if (BaseUtils.isNotBlank(taskListDTO.getTaskForcetId())) {
                sql += " AND TASK_FORCE_ID = ?";
                params.add(taskListDTO.getTaskForcetId());
            }

            if (BaseUtils.isNotBlank(taskListDTO.getExecTime())) {
                sql += " AND EXEC_TIME = ?";
                params.add(taskListDTO.getExecTime());
            }

            if (null != taskListDTO.getType()) {
                sql += " AND TYPE = ?";
                params.add(taskListDTO.getType().name());
            }

            if (BaseUtils.isNotBlank(taskListDTO.getIsCreate())) {
                sql += " AND IS_CREATE = ?";
                params.add(taskListDTO.getIsCreate());
            }

            if (BaseUtils.isNotBlank(taskListDTO.getName())) {
                sql += " AND NAME LIKE ?";
                params.add("%" + taskListDTO.getName() + "%");
            }

            sql += " AND TASK_SCHEDULE_END != 0  ";

            sql += " ORDER BY TYPE,CREATER_TIME DESC ";

            return this.getDao().pageQuery(TaskListModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    taskListDTO.getTo_page(), taskListDTO.getPage_size());
        } catch (Exception e) {
            logger.error("查询任务记录失败", e);
            throw new BusinessException("查询任务记录失败");
        }
    }

    /**
     * 修改任务记录
     *
     * @param id  主键id
     * @param num 执行次数
     * @throws BusinessException
     */
    @Override
    public int updateTaskListNum(String id, int num) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id) || 0 == num) {
                throw new BusinessException("缺少必要参数或参数不合法...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("NUM", num);
            return this.getDao().exec("UPDATE TASK_LIST SET NUM = :NUM WHERE ID = :ID", params);
        } catch (Exception e) {
            logger.error("修改任务记录信息失败", e);
            throw new BusinessException("修改任务记录信息失败");
        }
    }

    @Override
    public int updateTaskListIsCreate(String id, String isCreate) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id) || BaseUtils.isBlank(isCreate)) {
                throw new BusinessException("缺少必要参数或参数不合法...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("IS_CREATE", isCreate);
            return this.getDao().exec("UPDATE TASK_LIST SET IS_CREATE = :IS_CREATE WHERE ID = :ID", params);
        } catch (Exception e) {
            logger.error("修改任务记录信息失败", e);
            throw new BusinessException("修改任务记录信息失败");
        }
    }

    @Override
    public int updateTaskListTaskScheduleEnd(String id, int taskScheduleEnd) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id)) {
                throw new BusinessException("缺少必要参数或参数不合法...");
            }
            if (0 == taskScheduleEnd) {
                logger.warn("-----------{}参数是0--------", id);
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("TASK_SCHEDULE_END", taskScheduleEnd);
            return this.getDao().exec("UPDATE TASK_LIST SET TASK_SCHEDULE_END = :TASK_SCHEDULE_END WHERE ID = :ID", params);
        } catch (Exception e) {
            logger.error("修改任务记录信息失败", e);
            throw new BusinessException("修改任务记录信息失败");
        }
    }

    /**
     * 修改任务进度start
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public int updateTaskListTaskScheduleStart(String id) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id)) {
                throw new BusinessException("缺少必要参数或参数不合法...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            return this.getDao().exec("UPDATE TASK_LIST SET TASK_SCHEDULE_START = TASK_SCHEDULE_START+1 WHERE ID = :ID", params);
        } catch (Exception e) {
            logger.error("修改任务记录进度失败", e);
            throw new BusinessException("修改任务记录进度失败");
        }
    }

    @Override
    public int updateTaskListIsCreate(List<String> ids, String isCreate) throws BusinessException {
        try {
            if (ids.size() < 0 || BaseUtils.isBlank(isCreate)) {
                throw new BusinessException("缺少必要参数或参数不合法...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", ids);
            params.put("IS_CREATE", isCreate);
            params.put("STATE", TaskDetail.RUNNING.name());
            return this.getDao().exec("UPDATE TASK_LIST SET IS_CREATE = :IS_CREATE,STATE = :STATE WHERE ID IN (:ID)", params);
        } catch (Exception e) {
            logger.error("修改任务记录信息是否创建失败", e);
            throw new BusinessException("修改任务记录是否创建失败");
        }
    }

    /**
     * 修改任务list状态
     *
     * @param ids
     * @param taskDetail
     * @return
     * @throws BusinessException
     */
    @Override
    public int updateTaskListState(List<String> ids, TaskDetail taskDetail) throws BusinessException {
        try {
            if (ids.size() < 0 || null == taskDetail) {
                throw new BusinessException("缺少必要参数或参数不合法...");
            }
            String sql = "UPDATE TASK_LIST SET STATE = :STATE WHERE ID IN (:ID) AND STATE IN ('RUNNING','SUSPEND')";
            Map<String, Object> params = new HashMap<>();
            params.put("STATE", taskDetail.name());

            List<List<String>> datas = Lists.partition(ids,500);
            int num = 0;
            for (List<String> data : datas) {
                params.put("ID", data);
                num += this.getDao().exec(sql, params);
            }

            return num;
        } catch (Exception e) {
            logger.error("修改任务记录信息状态失败", e);
            throw new BusinessException("修改任务记录状态失败");
        }
    }

    @Override
    public int updateTodayTaskListState(TaskDetail taskDetail) throws BusinessException {
        try {
            if (null == taskDetail) {
                throw new BusinessException("缺少必要参数或参数不合法...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("EXEC_TIME", sdf.format(new Date()));
            params.put("STATE", taskDetail.name());
            return this.getDao().exec("UPDATE TASK_LIST SET STATE = :STATE WHERE EXEC_TIME = :EXEC_TIME  AND STATE IN ('RUNNING','SUSPEND')", params);
        } catch (Exception e) {
            logger.error("修改任务记录信息状态失败", e);
            throw new BusinessException("修改任务记录状态失败");
        }
    }


    /**
     * 根据id删除某个任务记录
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteTaskList(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(TaskListModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }


    /**
     * 加载一条记录
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public TaskListModel load(String id) throws BusinessException {
        try {
            return this.getDao().load(TaskListModel.class, id);
        } catch (Exception e) {
            logger.error("加载一条list失败", e);
            throw new BusinessException("加载一条list失败");
        }

    }

    @Override
    public TaskListModel load(String taskForcetId, String execTime) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("EXEC_TIME", execTime);
            params.put("TASK_FORCE_ID", taskForcetId);
            return this.getDao().selectOne(TaskListModel.class, "SELECT * FROM  TASK_LIST WHERE TASK_FORCE_ID = :TASK_FORCE_ID AND EXEC_TIME = :EXEC_TIME ", params);
        } catch (Exception e) {
            logger.error("修改任务记录信息状态失败", e);
            throw new BusinessException("修改任务记录状态失败");
        }
    }

    @Override
    public void update(TaskListModel taskListModel) throws BusinessException {
        try {
            this.getDao().update(taskListModel);
        } catch (Exception e) {
            logger.error("修改失败", e);
            throw new BusinessException("修改失败");
        }
    }


    /**
     * 修改任务是否需要回调
     *
     * @param taskForcetId
     * @throws BusinessException
     */
    @Override
    public void updateIsNeedCallBack(String taskForcetId, String isNeedCallBack) throws BusinessException {

        if (BaseUtils.isBlank(taskForcetId)) {
            throw new BusinessException("缺少必要参数...");
        }

        try {

            Map<String, Object> params = new HashMap<>();
            params.put("IS_NEED_CALL_BACK", isNeedCallBack);
            params.put("TASK_FORCE_ID", taskForcetId);

            this.getDao().exec("UPDATE TASK_LIST SET IS_NEED_CALL_BACK = :IS_NEED_CALL_BACK WHERE TASK_FORCE_ID = :TASK_FORCE_ID", params);

        } catch (Exception e) {
            logger.error("修改任务记录信息状态失败", e);
            throw new BusinessException("修改任务记录状态失败");
        }
    }


}
