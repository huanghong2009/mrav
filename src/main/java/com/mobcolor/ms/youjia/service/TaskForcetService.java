package com.mobcolor.ms.youjia.service;


import com.mobcolor.ms.youjia.model.TaskForcetModel;
import com.mobcolor.ms.youjia.model.dto.TaskForcetDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface TaskForcetService extends BaseService {

    /**
     * 新增一个任务组
     * @param taskForcetModel 任务组
     */
    void addTaskForcet(TaskForcetModel taskForcetModel) throws BusinessException;


    /**
     * 查询任务组
     * @param {lowerName}DTO
     * @return
     * @throws BusinessException
     */
    PageVO<TaskForcetModel> queryTaskForcetByPage(TaskForcetDTO taskForcetDTO) throws BusinessException;

    /**
     * 修改任务组内容
     * @param scriptNamest
     * @throws BusinessException
     */
    int updateTaskForcet(String id, String scriptNamest) throws BusinessException;

    /**
     * 删除一个任务组
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteTaskForcet(String id) throws BusinessException;

    /**
     * 查询一条数据
     * @param id
     * @return
     * @throws BusinessException
     */
    TaskForcetModel getTaskForcetService(String id) throws BusinessException;

}
