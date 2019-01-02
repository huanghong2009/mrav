
package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import com.mobcolor.ms.youjia.model.VpnTypeModel;
import com.mobcolor.ms.youjia.model.dto.VpnTypeDTO;
import com.mobcolor.ms.youjia.service.VpnTypeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-03-05 14:20:03
 */
@Service
public class VpnTypeServiceImpl extends BaseSupportServiceImpl implements VpnTypeService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(VpnTypeServiceImpl.class);

    private HashMap<String,String> nameIndex;

    


    @Override
    public void init() throws BusinessException {
        nameIndex = new HashMap<>();
        List<VpnTypeModel> vpnTypeModels = selectVpnTypes(new VpnTypeDTO());
        for (VpnTypeModel vpnTypeModel : vpnTypeModels) {
            nameIndex.put(vpnTypeModel.getId(),vpnTypeModel.getName());
        }
    }

    /**
     * 新增一个vpn类别
     *
     * @param vpnTypeModel vpn类别
     */
    public void addVpnType(VpnTypeModel vpnTypeModel) {
        if (BaseUtils.isBlank(vpnTypeModel.getName())) {
            throw new BusinessException("缺少必要参数...");
        }
        if (BaseUtils.isBlank(vpnTypeModel.getId())){
            vpnTypeModel.setId(PrimaryUtil.getId());
        }
        vpnTypeModel.setCreaterTime(new Date());

        try {
            this.getDao().insert(vpnTypeModel);
            nameIndex.put(vpnTypeModel.getId(),vpnTypeModel.getName());
        } catch (Exception e) {
            logger.error("新增vpn类别失败", e);
            throw new BusinessException("新增vpn类别失败");
        }
    }

    /**
     * 分页查询vpn类别
     *
     * @param vpnTypeDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<VpnTypeModel> queryVpnTypeByPage(VpnTypeDTO vpnTypeDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM VPN_TYPE WHERE 1=1 ";

            if (BaseUtils.isNotBlank(vpnTypeDTO.getName())) {
                sql += " AND NAME LIKE ?";
                params.add("%" + vpnTypeDTO.getName() + "%");
            }

            PageVO<VpnTypeModel> vpnTypeModelPageVO = this.getDao().pageQuery(VpnTypeModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    vpnTypeDTO.getTo_page(), vpnTypeDTO.getPage_size());
            return vpnTypeModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询vpn类别失败", e);
            throw new BusinessException("分页查询vpn类别失败");
        }

    }

    /**
     * 修改vpn类别
     *
     * @param id      主键id
     * @param name  名称
     * @throws BusinessException
     */
    @Override
    public int updateVpnType(String id, String name) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id) || BaseUtils.isBlank(name)) {
                throw new BusinessException("缺少必要参数...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("NAME", name);
            int num = this.getDao().exec("UPDATE VPN_TYPE SET NAME = :NAME WHERE ID = :ID", params);
            nameIndex.put(id,name);
            return num;
        } catch (Exception e) {
            logger.error("修改vpn类别信息失败", e);
            throw new BusinessException("修改vpn类别信息失败");
        }
    }

    /**
     * 根据id删除某个vpn类别
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteVpnType(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().delete(VpnTypeModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询vpn类别数据集
     *
     * @param vpnTypeDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<VpnTypeModel> selectVpnTypes(VpnTypeDTO vpnTypeDTO) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM VPN_TYPE WHERE 1=1";
            return this.getDao().selectList(VpnTypeModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询vpn类别数据失败", e);
            throw new BusinessException("查询vpn类别数据失败");
        }
    }

    /**
     * 加载一条数据
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public VpnTypeModel loadVpnType(String id) throws BusinessException {
        try {
            return this.getDao().load(VpnTypeModel.class,id);
        } catch (Exception e) {
            logger.error("加载数据失败", e);
            throw new BusinessException("加载数据失败");
        }
    }

    @Override
    public String getNameById(String id) throws BusinessException {
        return nameIndex.get(id);
    }


}
