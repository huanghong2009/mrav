package com.mobcolor.ms.youjia.service;

import com.alibaba.fastjson.JSONObject;

import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.model.TaskDetailModel;
import com.mobcolor.ms.youjia.model.dto.TaskDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseService;


import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface TaskService extends BaseService {

    /**
     * 新增一个任务组
     * @param taskDTO 任务组
     */
    void addTask(TaskDTO taskDTO) throws BusinessException;

    /**
     * 任务重置
     * @throws BusinessException
     */
    void listReset(String taskForcetId,String execTime)throws BusinessException;

    /**
     * 创建今天的任务
     * @param taskId
     * @throws BusinessException
     */
    void createTodayTask(String taskId) throws BusinessException;

    /**
     * 加载今天的任务
     * @throws BusinessException
     */
    void loadTodayTask(List<String> ids)throws BusinessException;

    /**
     * 加载今天的任务
     * @throws BusinessException
     */
    void loadTodayTask()throws BusinessException;

    /**
     * 获得一条任务
     * @return
     * @throws BusinessException
     */
    TaskDetailModel getTask(String deviceNumber) throws BusinessException;

    /**
     * 批量启用或停用
     * @param taskListId
     * @param taskDetail
     * @return
     * @throws BusinessException
     */
    int batchAbled(String taskListId,TaskDetail taskDetail) throws BusinessException;

    /**
     * 全部启用或停用
     * @param taskDetail
     * @param taskDetail
     * @return
     * @throws BusinessException
     */
    int allAbled(TaskDetail taskDetail) throws BusinessException;

    /**
     * 执行一段查询sql
     * @param sql
     * @return
     * @throws BusinessException
     */
    List<JSONObject> execQuerySql(String sql) throws BusinessException;

    /**
     * 添加一个任务放入缓存
     * @param id
     * @throws BusinessException
     */
    void addTaskToList(String id) throws BusinessException;

    /**
     * 定时任务加载多次登陆
     * @param taskId
     * @throws BusinessException
     */
    void loadRepeatedlyData(String taskId) throws BusinessException;

}
