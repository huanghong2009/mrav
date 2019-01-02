package com.mobcolor.ms.youjia.service;

import com.mobcolor.ms.youjia.model.DeviceGroupModel;
import com.mobcolor.ms.youjia.model.dto.DeviceGroupDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface DeviceGroupService extends BaseService {

    /**
     * 新增一个设备组
     *
     * @param deviceGroupModel 设备组
     */
    void addDeviceGroup(DeviceGroupModel deviceGroupModel) throws BusinessException;

    /**
     * 查询设备组
     *
     * @param deviceGroupDTO
     * @return
     * @throws BusinessException
     */
    PageVO<DeviceGroupModel> queryDeviceGroupByPage(DeviceGroupDTO deviceGroupDTO) throws BusinessException;

    /**
     * 修改设备组内容
     *
     * @param deviceGroupModel
     * @throws BusinessException
     */
    int updateDeviceGroup(DeviceGroupModel deviceGroupModel) throws BusinessException;

    /**
     * 删除一个设备组
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteDeviceGroup(String id) throws BusinessException;

    /**
     * 查询设备组数据
     *
     * @param deviceGroupDTO
     * @return
     * @throws BusinessException
     */
    List<DeviceGroupModel> selectDeviceGroups(DeviceGroupDTO deviceGroupDTO) throws BusinessException;

    /**
     * 根据设备组id获取执行组id
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    List<String> getExecGroupByDeviceGroupId(String id) throws BusinessException;

    /**
     * 加载一条数据
     * @param id
     * @return
     * @throws BusinessException
     */
    DeviceGroupModel loadDeviceGroup(String id) throws BusinessException;
}
