package com.mobcolor.ms.youjia.service;


import com.mobcolor.ms.youjia.model.AppManageModel;
import com.mobcolor.ms.youjia.model.dto.AppManageDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface AppManageService extends BaseService {

    /**
     * 新增一个应用分组
     * @param appManageModel
     */
    void addAppManage(AppManageModel appManageModel) throws BusinessException;

    /**
     * 查询应用分组
     * @param appManageDTO
     * @return
     * @throws BusinessException
     */
    PageVO<AppManageModel> queryAppManageByPage(AppManageDTO appManageDTO) throws BusinessException;

    /**
     * 修改应用分组
     * @param appManageModel
     * @throws BusinessException
     */
    int updateAppManage(AppManageModel appManageModel) throws BusinessException;

    /**
     * 删除一个应用分组
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteAppManage(String id) throws BusinessException;
}
