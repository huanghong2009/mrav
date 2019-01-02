
package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.ms.youjia.model.DeviceModel;
import com.mobcolor.ms.youjia.model.dto.DeviceDTO;
import com.mobcolor.ms.youjia.service.DeviceService;
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
 * @version 创建时间：2018-01-30 11:35:01
 */
@Service
public class DeviceServiceImpl extends BaseSupportServiceImpl implements DeviceService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(DeviceServiceImpl.class);

    /**
     * 设备列表索引缓存
     */
    private Map<String, DeviceModel> deviceModelIndex;

    /**
     * 设备列表缓存
     */
    private List<DeviceModel> deviceModelList;

    /**
     * 设备列表位置索引缓存
     */
    private Map<String, Integer> deviceModelListIndex;

    @Override
    public void init() throws BusinessException {
        deviceModelIndex = new HashMap<>();
        deviceModelList = new ArrayList<>();
        deviceModelListIndex = new HashMap<>();

        initDeviceModels();
    }


    /**
     * 初始化设备索引缓存
     *
     * @throws BusinessException
     */
    public void initDeviceModels() throws BusinessException {
        deviceModelIndex.clear();
        deviceModelList.clear();
        deviceModelList = selectDevices(new DeviceDTO());
        for (int i = 0; i < deviceModelList.size(); i++) {
            DeviceModel deviceModel = deviceModelList.get(i);
            deviceModelIndex.put(deviceModel.getDeviceNumber(), deviceModel);
            deviceModelListIndex.put(deviceModel.getDeviceNumber(), i);
        }

    }


    /**
     * 新增一个设备
     *
     * @param deviceModel 设备
     */
    public void addDevice(DeviceModel deviceModel) {

        if (BaseUtils.isBlank(deviceModel.getId())) {
            deviceModel.setId(PrimaryUtil.getId());
        }
        deviceModel.setCreaterTime(new Date());
        try {
            this.getDao().insert(deviceModel);
        } catch (Exception e) {
            logger.error("新增设备失败", e);
            throw new BusinessException("新增设备失败");
        }
    }

    /**
     * 分页查询设备
     *
     * @param deviceDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<DeviceModel> queryDeviceByPage(DeviceDTO deviceDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM DEVICE WHERE 1=1 ";

            if (BaseUtils.isNotBlank(deviceDTO.getName())) {
                sql += " AND NAME LIKE ?";
                params.add("%" + deviceDTO.getName() + "%");
            }


            PageVO<DeviceModel> deviceModelPageVO = this.getDao().pageQuery(DeviceModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    deviceDTO.getTo_page(), deviceDTO.getPage_size());
            return deviceModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询设备失败", e);
            throw new BusinessException("分页查询设备失败");
        }

    }

    /**
     * 修改设备
     *
     * @param deviceModel 设备信息
     * @throws BusinessException
     */
    @Override
    public int updateDevice(DeviceModel deviceModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(deviceModel.getId())) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(deviceModel);
        } catch (Exception e) {
            logger.error("修改设备信息失败", e);
            throw new BusinessException("修改设备信息失败");
        }
    }

    /**
     * 根据id删除某个设备
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteDevice(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(DeviceModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询设备数据集
     *
     * @param deviceDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<DeviceModel> selectDevices(DeviceDTO deviceDTO) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM DEVICE ";
            return this.getDao().selectList(DeviceModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询设备数据失败", e);
            throw new BusinessException("查询设备数据失败");
        }
    }

    /**
     * 上报设备
     *
     * @param deviceModel
     * @throws BusinessException
     */
    @Override
    public void reportDevice(DeviceModel deviceModel) throws BusinessException {
        if (BaseUtils.isBlank(deviceModel.getName())
                || BaseUtils.isBlank(deviceModel.getDeviceNumber())
                || BaseUtils.isBlank(deviceModel.getDevcieModel())
                || BaseUtils.isBlank(deviceModel.getSystemVersion())
                || BaseUtils.isBlank(deviceModel.getTsNumber())) {
            throw new BusinessException("缺少必要参数...");
        }

        String deviceNum = deviceModel.getDeviceNumber();
        /**
         * 如归有就update,否则就新增
         */
        if (deviceModelIndex.containsKey(deviceNum)) {
            deviceModel.setId(deviceModelIndex.get(deviceNum).getId());
            deviceModel.setDeviceNumber(null);
            deviceModel.setTsNumber(null);
            updateDevice(deviceModel);
            DeviceModel deviceModelNew = loadDeviceModelByDeviceNumber(deviceNum);
            deviceModelList.set(deviceModelListIndex.get(deviceNum), deviceModelNew);
        } else {
            deviceModel.setId(null);
            addDevice(deviceModel);
            deviceModelList.add(deviceModel);
            deviceModelListIndex.put(deviceNum,deviceModelList.size()-1);
        }

        deviceModelIndex.put(deviceNum, deviceModel);

    }

    /**
     * 加载设备信息
     *
     * @param num
     * @return
     * @throws BusinessException
     */
    @Override
    public DeviceModel loadDeviceModelByDeviceNumber(String num) throws BusinessException {
        if (BaseUtils.isBlank(num)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("DEVICE_NUMBER", num);
            String sql = " SELECT * FROM DEVICE WHERE DEVICE_NUMBER = :DEVICE_NUMBER ";
            return this.getDao().selectOne(DeviceModel.class, sql, params);
        } catch (Exception e) {
            logger.error("加载设备数据失败", e);
            throw new BusinessException("加载设备数据失败");
        }
    }

    @Override
    public DeviceModel loadDeviceModel(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().load(DeviceModel.class,id);
        } catch (Exception e) {
            logger.error("加载数据失败", e);
            throw new BusinessException("加载数据失败");
        }
    }

    /**
     * 获得设备组
     * @param deviceNumber
     * @return
     * @throws BusinessException
     */
    @Override
    public String getDeviceGroupByDevice(String deviceNumber) throws BusinessException {
        if (deviceModelIndex.containsKey(deviceNumber)) {
            return deviceModelIndex.get(deviceNumber).getDeviceGroupId();
        }else {
            return null;
        }
    }



}
