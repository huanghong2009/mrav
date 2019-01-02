package com.mobcolor.ms.youjia.service;

import com.mobcolor.ms.youjia.model.CakeDockModel;
import com.mobcolor.ms.youjia.model.dto.CakeDockDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface CakeDockService extends BaseService {

    /**
     * 新增一个cake对接
     * @param cakeDockModel cake对接
     */
    void addCakeDock(CakeDockModel cakeDockModel) throws BusinessException;

    /**
     * 查询cake对接
     * @param cakeDockDTO
     * @return
     * @throws BusinessException
     */
    PageVO<CakeDockModel> queryCakeDockByPage(CakeDockDTO cakeDockDTO) throws BusinessException;

    /**
     * 修改cake对接内容
     * @param taskDetailId
     * @throws BusinessException
     */
    int updateCakeDock(String id, String taskDetailId) throws BusinessException;

    /**
     * 删除一个cake对接
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteCakeDock(String id) throws BusinessException;

    /**
     * 加载一条任务
     * @param id
     * @return
     * @throws BusinessException
     */
    CakeDockModel loadCakeDock(String id) throws BusinessException;
}
