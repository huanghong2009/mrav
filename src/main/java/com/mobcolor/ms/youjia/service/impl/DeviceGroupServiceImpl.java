
package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.ms.youjia.model.DeviceGroupModel;
import com.mobcolor.ms.youjia.model.dto.DeviceGroupDTO;
import com.mobcolor.ms.youjia.service.DeviceGroupService;
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
 * @version 创建时间：2018-01-30 21:36:21
 */
@Service
public class DeviceGroupServiceImpl extends BaseSupportServiceImpl implements DeviceGroupService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(DeviceGroupServiceImpl.class);

    /**
     * key -设备组id，value - 执行组id
     */
    private Map<String, ArrayList<String>> deviceExecGroupIndex;


    @Override
    public void init() throws BusinessException {
        deviceExecGroupIndex = new HashMap<>();

        initDeviceExecGroupIndex();
    }

    /**
     * 初始化 deviceExecGroupIndex
     */
    private void initDeviceExecGroupIndex() {
        deviceExecGroupIndex.clear();
        List<DeviceGroupModel> deviceGroupModels = selectDeviceGroups(new DeviceGroupDTO());
        for (DeviceGroupModel deviceGroupModel : deviceGroupModels) {
            if (BaseUtils.isBlank(deviceGroupModel.getExecGroup())) {
                continue;
            }
            deviceExecGroupIndexAdd(deviceGroupModel.getId(), deviceGroupModel.getExecGroup());
        }
    }

    /**
     * 给设备添加数据
     */
    private void deviceExecGroupIndexAdd(String groupId, String execGroup) {
        String[] execGroups = execGroup.split(",");
        for (String group : execGroups) {
            if (!deviceExecGroupIndex.containsKey(groupId)) {
                deviceExecGroupIndex.put(groupId, new ArrayList<>());
            }
            deviceExecGroupIndex.get(groupId).add(group);
        }
    }

    /**
     * 新增一个设备组
     *
     * @param deviceGroupModel 设备组
     */
    public void addDeviceGroup(DeviceGroupModel deviceGroupModel) {
        if (BaseUtils.isBlank(deviceGroupModel.getName())) {
            throw new BusinessException("缺少必要参数...");
        }
        deviceGroupModel.setCreaterTime(new Date());
        deviceGroupModel.setId(PrimaryUtil.getId());
        try {
            this.getDao().insert(deviceGroupModel);
            if (BaseUtils.isNotBlank(deviceGroupModel.getExecGroup())) {
                deviceExecGroupIndexAdd(deviceGroupModel.getId(), deviceGroupModel.getExecGroup());
            }
        } catch (Exception e) {
            logger.error("新增设备组失败", e);
            throw new BusinessException("新增设备组失败");
        }
    }

    /**
     * 分页查询设备组
     *
     * @param deviceGroupDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<DeviceGroupModel> queryDeviceGroupByPage(DeviceGroupDTO deviceGroupDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM DEVICE_GROUP WHERE 1=1 ";

            if (BaseUtils.isNotBlank(deviceGroupDTO.getName())) {
                sql += " AND NAME LIKE ? ";
                params.add("%" + deviceGroupDTO.getName() + "%");
            }

            PageVO<DeviceGroupModel> deviceGroupModelPageVO = this.getDao().pageQuery(DeviceGroupModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    deviceGroupDTO.getTo_page(), deviceGroupDTO.getPage_size());
            return deviceGroupModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询设备组失败", e);
            throw new BusinessException("分页查询设备组失败");
        }

    }

    /**
     * 修改设备组
     *
     * @param deviceGroupModel
     * @throws BusinessException
     */
    @Override
    public int updateDeviceGroup(DeviceGroupModel deviceGroupModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(deviceGroupModel.getId())) {
                throw new BusinessException("缺少必要参数...");
            }
            int num = this.getDao().update(deviceGroupModel);
            initDeviceExecGroupIndex();
            return num;
        } catch (Exception e) {
            logger.error("修改设备组信息失败", e);
            throw new BusinessException("修改设备组信息失败");
        }
    }

    /**
     * 根据id删除某个设备组
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteDeviceGroup(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().delete(DeviceGroupModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询设备组数据集
     *
     * @param deviceGroupDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<DeviceGroupModel> selectDeviceGroups(DeviceGroupDTO deviceGroupDTO) throws BusinessException {

        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM DEVICE_GROUP ";
            return this.getDao().selectList(DeviceGroupModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询设备组数据失败", e);
            throw new BusinessException("查询设备组数据失败");
        }
    }

    /**
     * 根据设备组id获取执行组id
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public List<String> getExecGroupByDeviceGroupId(String id) throws BusinessException {
        if (!deviceExecGroupIndex.containsKey(id)) {
            return null;
        }
        List<String> execGroups = deviceExecGroupIndex.get(id);

        return execGroups;
    }

    /**
     * 加载一条数据
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public DeviceGroupModel loadDeviceGroup(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().load(DeviceGroupModel.class,id);
        } catch (Exception e) {
            logger.error("加载数据失败", e);
            throw new BusinessException("加载数据失败");
        }
    }


}
