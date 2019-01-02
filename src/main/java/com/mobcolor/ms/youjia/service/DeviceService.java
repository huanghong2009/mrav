package com.mobcolor.ms.youjia.service;


import com.mobcolor.ms.youjia.model.DeviceModel;
import com.mobcolor.ms.youjia.model.dto.DeviceDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface DeviceService extends BaseService {

    /**
     * 新增一个设备
     * @param deviceModel 设备
     */
    void addDevice(DeviceModel deviceModel) throws BusinessException;

    /**
     * 查询设备
     * @param deviceDTO
     * @return
     * @throws BusinessException
     */
    PageVO<DeviceModel> queryDeviceByPage(DeviceDTO deviceDTO) throws BusinessException;

    /**
     * 修改设备内容
     * @param deviceModel
     * @throws BusinessException
     */
    int updateDevice(DeviceModel deviceModel) throws BusinessException;

    /**
     * 删除一个设备
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteDevice(String id) throws BusinessException;

    /**
     * 查询设备数据
     * @param deviceDTO
     * @return
     * @throws BusinessException
     */
    List<DeviceModel> selectDevices(DeviceDTO deviceDTO) throws BusinessException;


    /**
     * 上报机器信息
     * @param deviceModel
     * @throws BusinessException
     */
    void reportDevice(DeviceModel deviceModel) throws BusinessException;

    /**
     * 加载一条设备信息
     * @param num
     * @return
     * @throws BusinessException
     */
    DeviceModel loadDeviceModelByDeviceNumber(String num) throws BusinessException;

    /**
     * 加载一条设备信息
     * @param id
     * @return
     * @throws BusinessException
     */
    DeviceModel loadDeviceModel(String id) throws BusinessException;

    /**
     * 获得设备信息
     * @param deviceNum
     * @return
     * @throws BusinessException
     */
    String getDeviceGroupByDevice(String deviceNum)throws BusinessException;
}
