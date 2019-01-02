package com.mobcolor.ms.youjia.service;

import com.alibaba.fastjson.JSONObject;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;
import com.mobcolor.ms.youjia.model.VpnByDeviceModel;
import com.mobcolor.ms.youjia.model.VpnModel;
import com.mobcolor.ms.youjia.model.dto.VpnDTO;

import java.io.File;
import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface VpnService extends BaseService {

    /**
     * 新增一个vpn
     * @param vpnModel vpn
     */
    void addVpn(VpnModel vpnModel) throws BusinessException;

    /**
     * 新增一批vpn
     * @param vpnModelList vpn列表
     */
    void addBatchVpns(List<VpnModel> vpnModelList) throws BusinessException;

    /**
     * 查询vpn
     * @param vpnDTO
     * @return
     * @throws BusinessException
     */
    PageVO<VpnModel> queryVpnByPage(VpnDTO vpnDTO) throws BusinessException;

    /**
     * 修改vpn内容
     * @param vpnModel
     * @throws BusinessException
     */
    int updateVpn(VpnByDeviceModel vpnModel) throws BusinessException;

    /**
     * 删除一个vpn
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteVpn(String id) throws BusinessException;

    /**
     * 查询vpn数据
     * @param vpnDTO
     * @return
     * @throws BusinessException
     */
    List<VpnByDeviceModel> selectVpns(VpnDTO vpnDTO) throws BusinessException;


    /**
     *解析组装数据
     * @param file
     * @throws BusinessException
     */
    String assembleData(File file, String type) throws BusinessException;


    /**
     * 加载vpn数据
     * @param id
     * @return
     * @throws BusinessException
     */
    VpnModel loadVpn(String id)throws BusinessException;

    /**
     * 随机取一条vpn
     * @param type
     * @param region
     * @return
     * @throws BusinessException
     */
    VpnByDeviceModel getVpn(String type,String region,String deviceId) throws BusinessException;


    /**
     * 随机取一条vpn
     * @param type
     * @param region
     * @return
     * @throws BusinessException
     */
    VpnByDeviceModel getRandVpn(String type,String region) throws BusinessException;

    /**
     * 解除vpn锁定
     * @param deviceId 设备id
     * @param isInVaild 是否停用该账号
     * @throws BusinessException
     */
    void unlockVpn(String deviceId,String isInVaild) throws BusinessException;



}
