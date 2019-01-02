package com.mobcolor.ms.youjia.service;


import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.model.TaskListModel;
import com.mobcolor.ms.youjia.model.dto.TaskListDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface TaskListService extends BaseService {

    /**
     * 新增一个任务记录
     *
     * @param taskListModel 任务记录
     */
    void addTaskList(TaskListModel taskListModel) throws BusinessException;

    /**
     * 批量
     *
     * @param taskListModels
     * @throws BusinessException
     */
    void addBatchTaskList(List<TaskListModel> taskListModels) throws BusinessException;

    /**
     * 查询任务记录
     *
     * @param taskListDTO
     * @return
     * @throws BusinessException
     */
    List<TaskListModel> queryTaskList(TaskListDTO taskListDTO) throws BusinessException;



    /**
     * 查询任务记录
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    List<TaskListModel> queryTaskListByIds(List<String> ids) throws BusinessException;


    /**
     * 查询今天的新增任务
     *
     * @return
     * @throws BusinessException
     */
    List<TaskListModel> queryTodayTaskList() throws BusinessException;



    /**
     * 查询任务记录-page
     *
     * @param taskListDTO
     * @return
     * @throws BusinessException
     */
    PageVO<TaskListModel> queryTaskListByPage(TaskListDTO taskListDTO) throws BusinessException;

    /**
     * 修改任务条数
     *
     * @param num
     * @throws BusinessException
     */
    int updateTaskListNum(String id, int num) throws BusinessException;

    /**
     * 修改任务记录内
     *
     * @param isCreate
     * @throws BusinessException
     */
    int updateTaskListIsCreate(String id, String isCreate) throws BusinessException;

    /**
     * 修改任务记录内
     *
     * @param taskScheduleEnd
     * @throws BusinessException
     */
    int updateTaskListTaskScheduleEnd(String id, int taskScheduleEnd) throws BusinessException;


    /**
     * 修改任务记录进度
     *
     * @throws BusinessException
     */
    int updateTaskListTaskScheduleStart(String id) throws BusinessException;

    /**
     * 修改任务是否创建
     *
     * @param isCreate
     * @throws BusinessException
     */
    int updateTaskListIsCreate(List<String> id, String isCreate) throws BusinessException;

    /**
     * 修改任务状态
     *
     * @param taskDetail
     * @throws BusinessException
     */
    int updateTaskListState(List<String> id, TaskDetail taskDetail) throws BusinessException;


    /**
     * 修改今日任务状态
     *
     * @param taskDetail
     * @throws BusinessException
     */
    int updateTodayTaskListState(TaskDetail taskDetail) throws BusinessException;


    /**
     * 删除一个任务记录
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteTaskList(String id) throws BusinessException;


    /**
     * 加载一条记录
     * @param id
     * @return
     * @throws BusinessException
     */
    TaskListModel load(String id) throws BusinessException;


    /**
     * 加载一条记录
     * @return
     * @throws BusinessException
     */
    TaskListModel load(String taskForcetId,String execTime) throws BusinessException;

    /**
     * 修改
     * @param taskListModel
     * @throws BusinessException
     */
    void update(TaskListModel taskListModel) throws BusinessException;

    /**
     * 修改任务是否需要回调
     * @param taskForcetId
     * @throws BusinessException
     */
    void updateIsNeedCallBack(String taskForcetId,String isNeedCallBack) throws BusinessException;
}
