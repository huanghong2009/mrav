package com.mobcolor.ms.youjia.service;

import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;
import com.mobcolor.ms.youjia.model.VpnTypeModel;
import com.mobcolor.ms.youjia.model.dto.VpnTypeDTO;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface VpnTypeService extends BaseService {

    /**
     * 新增一个vpn类别
     *
     * @param vpnTypeModel vpn类别
     */
    void addVpnType(VpnTypeModel vpnTypeModel) throws BusinessException;

    /**
     * 查询vpn类别
     *
     * @param vpnTypeDTO
     * @return
     * @throws BusinessException
     */
    PageVO<VpnTypeModel> queryVpnTypeByPage(VpnTypeDTO vpnTypeDTO) throws BusinessException;

    /**
     * 修改vpn类别内容
     *
     * @param name
     * @throws BusinessException
     */
    int updateVpnType(String id, String name) throws BusinessException;

    /**
     * 删除一个vpn类别
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteVpnType(String id) throws BusinessException;

    /**
     * 查询vpn类别数据
     *
     * @param vpnTypeDTO
     * @return
     * @throws BusinessException
     */
    List<VpnTypeModel> selectVpnTypes(VpnTypeDTO vpnTypeDTO) throws BusinessException;

    /**
     * 加载数据
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    VpnTypeModel loadVpnType(String id) throws BusinessException;

    /**
     * 加载缓存name
     * @param id
     * @return
     * @throws BusinessException
     */
    String getNameById(String id) throws BusinessException;
}
