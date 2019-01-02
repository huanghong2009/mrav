package com.mobcolor.ms.youjia.service;


import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.model.TaskDetailModel;
import com.mobcolor.ms.youjia.model.dto.TaskCountDTO;
import com.mobcolor.ms.youjia.model.dto.TaskDetailDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface TaskDetailService extends BaseService {

    /**
     * 新增一个任务详情
     * @param taskDetailModel 任务详情
     */
    void addTaskDetail(TaskDetailModel taskDetailModel) throws BusinessException;

    /**
     * 新增一个批量
     * @param taskDetailModelList
     * @throws BusinessException
     */
    void addBatchTaskDetail(List<TaskDetailModel> taskDetailModelList) throws BusinessException;

    /**
     * 查询任务详情
     * @param taskDetailDTO
     * @return
     * @throws BusinessException
     */
    List<TaskDetailModel> queryTaskDetails(TaskDetailDTO taskDetailDTO) throws BusinessException;


    /**
     * 查询任务详情
     * @param ids
     * @return
     * @throws BusinessException
     */
    List<TaskDetailModel> queryTaskDetailByListId(List<String> ids) throws BusinessException;


    /**
     * 统计任务详情
     * @param taskDetailDTO
     * @return
     * @throws BusinessException
     */
    PageVO<TaskCountDTO> countTaskDetailsByPage(TaskDetailDTO taskDetailDTO) throws BusinessException;

    /**
     * 查询任务详情
     * @param taskDetailDTO
     * @return
     * @throws BusinessException
     */
    Long queryCountTaskDetails(TaskDetailDTO taskDetailDTO) throws BusinessException;

    /**
     * 查询任务详情
     * @param createrTime
     * @return
     * @throws BusinessException
     */
    List<TaskDetailModel> queryUntreatedTaskDetails(String createrTime) throws BusinessException;

    /**
     * 查询任务详情
     * @param taskDetailDTO
     * @return
     * @throws BusinessException
     */
    PageVO<TaskDetailModel> queryTaskDetailsByPage(TaskDetailDTO taskDetailDTO) throws BusinessException;

    /**
     * 修改任务详情内容
     * @param taskDetailModel
     * @throws BusinessException
     */
    int updateTaskDetail(TaskDetailModel taskDetailModel) throws BusinessException;

    /**
     * 删除一个任务详情
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteTaskDetail(String id) throws BusinessException;

    /**
     * 删除一个任务详情
     * @param listId listIdid
     * @return
     * @throws BusinessException
     */
    int deleteTaskDetailByListId(String listId) throws BusinessException;

    /**
     * 修改任务状态
     * @param taskListId
     * @param state
     * @return
     * @throws BusinessException
     */
    int updateTaskDetailStateById(String taskListId, TaskDetail state) throws BusinessException;

    /**
     * 修改今日任务状态
     * @param state
     * @param state
     * @return
     * @throws BusinessException
     */
    int updateTodayTaskDetailState(TaskDetail state) throws BusinessException;

    /**
     * 修改任务状态
     * @param taskListIds
     * @param state
     * @return
     * @throws BusinessException
     */
    int updateTaskDetailStateById(List<String> taskListIds, TaskDetail state) throws BusinessException;

    /**
     * 加载一条记录
     * @param id
     * @return
     * @throws BusinessException
     */
    TaskDetailModel load(String id)throws BusinessException;
}
