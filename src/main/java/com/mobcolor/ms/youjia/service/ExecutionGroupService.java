package com.mobcolor.ms.youjia.service;

import com.mobcolor.ms.youjia.model.ExecutionGroupModel;
import com.mobcolor.ms.youjia.model.dto.ExecutionGroupDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface ExecutionGroupService extends BaseService {

    /**
     * 新增一个执行组
     * @param executionGroupModel 执行组
     */
    void addExecutionGroup(ExecutionGroupModel executionGroupModel) throws BusinessException;

    /**
     * 查询执行组
     * @param executionGroupDTO
     * @return
     * @throws BusinessException
     */
    PageVO<ExecutionGroupModel> queryExecutionGroupByPage(ExecutionGroupDTO executionGroupDTO) throws BusinessException;

    /**
     * 修改执行组内容
     * @param executionGroupModel
     * @throws BusinessException
     */
    int updateExecutionGroup(ExecutionGroupModel executionGroupModel) throws BusinessException;

    /**
     * 删除一个执行组
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteExecutionGroup(String id) throws BusinessException;

    /**
     * 查询执行组数据
     * @param executionGroupDTO
     * @return
     * @throws BusinessException
     */
    List<ExecutionGroupModel> selectExecutionGroups(ExecutionGroupDTO executionGroupDTO) throws BusinessException;

}
