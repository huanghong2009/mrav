

package com.mobcolor.ms.youjia.service.impl;

import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import com.mobcolor.ms.youjia.enums.Account;
import com.mobcolor.ms.youjia.model.AccountModel;
import com.mobcolor.ms.youjia.model.VpnByDeviceModel;
import com.mobcolor.ms.youjia.model.VpnErrorLogModel;
import com.mobcolor.ms.youjia.model.VpnModel;
import com.mobcolor.ms.youjia.model.dto.VpnDTO;
import com.mobcolor.ms.youjia.service.VpnService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-04-12 15:16:04
 */
@Service
public class VpnServiceImpl extends BaseSupportServiceImpl implements VpnService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(VpnServiceImpl.class);

    private Map<String, VpnByDeviceModel> vpnLock;


    public void init() throws BusinessException {
        vpnLock = new HashMap<>();
        VpnDTO vpnDTO = new VpnDTO();
        vpnDTO.setIsNullDevice("Y");
        vpnDTO.setState(Account.VALID);
        List<VpnByDeviceModel> vpnModels = selectVpns(vpnDTO);
        for (VpnByDeviceModel vpnModel : vpnModels) {
            vpnLock.put(vpnModel.getDeviceId(), vpnModel);
        }

    }

    /**
     * 新增一个vpn
     *
     * @param vpnModel vpn
     */
    public void addVpn(VpnModel vpnModel) {
        if (BaseUtils.isBlank(vpnModel.getAccount()) || BaseUtils.isBlank(vpnModel.getPassWord())) {
            throw new BusinessException("缺少必要参数...");
        }
        vpnModel.setId(PrimaryUtil.getId());
        vpnModel.setCreaterTime(new Date());
        try {
            this.getDao().insert(vpnModel);
        } catch (Exception e) {
            logger.error("新增vpn失败", e);
            throw new BusinessException(e, "新增vpn失败");
        }
    }

    /**
     * 新增批量
     *
     * @param vpnModelList vpn列表
     * @throws BusinessException
     */
    @Override
    public void addBatchVpns(List<VpnModel> vpnModelList) throws BusinessException {
        try {
            this.getDao().insertBatch(vpnModelList);
        } catch (Exception e) {
            logger.error("批量新增账号失败", e);
            throw new BusinessException("批量新增账号失败");
        }
    }


    /**
     * 分页查询vpn
     *
     * @param vpnDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<VpnModel> queryVpnByPage(VpnDTO vpnDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM VPN WHERE 1=1 ";

            if (BaseUtils.isNotBlank(vpnDTO.getType())) {
                sql += " AND TYPE = ?";
                params.add(vpnDTO.getType());
            }

            if (BaseUtils.isNotBlank(vpnDTO.getRegion())) {
                sql += " AND REGION = ?";
                params.add(vpnDTO.getRegion());
            }

            if (null != vpnDTO.getState()) {
                sql += " AND STATE = ?";
                params.add(vpnDTO.getState().name());
            }

            PageVO<VpnModel> vpnModelPageVO = this.getDao().pageQuery(VpnModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    vpnDTO.getTo_page(), vpnDTO.getPage_size());
            return vpnModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询vpn失败", e);
            throw new BusinessException("分页查询vpn失败");
        }

    }

    /**
     * 修改vpn
     *
     * @param vpnModel vpn
     * @throws BusinessException
     */
    @Override
    public int updateVpn(VpnByDeviceModel vpnModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(vpnModel.getId())) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(vpnModel);
        } catch (Exception e) {
            logger.error("修改vpn信息失败", e);
            throw new BusinessException("修改vpn信息失败");
        }
    }

    /**
     * 根据id删除某个vpn
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteVpn(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().delete(VpnModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }

    /**
     * 查询vpn数据集
     *
     * @param vpnDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<VpnByDeviceModel> selectVpns(VpnDTO vpnDTO) throws BusinessException {

        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM VPN WHERE 1=1 ";
            if (vpnDTO.getState() != null) {
                sql += " AND STATE = :STATE ";
                params.put("STATE", vpnDTO.getState().name());
            }
            if (BaseUtils.isNotBlank(vpnDTO.getIsNullDevice()) && vpnDTO.getIsNullDevice().equals("Y")) {
                sql += " AND ( DEVICE_ID IS NOT NULL OR DEVICE_ID != '' ) ";
            }
            return this.getDao().selectList(VpnByDeviceModel.class, sql, params);
        } catch (Exception e) {
            logger.error("查询vpn数据失败", e);
            throw new BusinessException("查询vpn数据失败");
        }
    }

    @Override
    public String assembleData(File file, String type) throws BusinessException {
        if (null == file || BaseUtils.isBlank(type)) {
            throw new BusinessException("缺少必要参数...");
        }

        List<List<String>> excleData = BaseUtils.readExcel(file, 2).get(0);

        if (excleData.size() < 1) {
            logger.error("-----excel数据为空");
            throw new BusinessException("excel数据为空");
        }

        try {

            String bacthNo = this.sdf1.format(new Date());

            for (List<String> excleDatum : excleData) {

                /**
                 * 判断空行
                 */
                if (BaseUtils.isBlank(excleDatum.get(0)) && BaseUtils.isBlank(excleDatum.get(1))) {
                    break;
                }

                VpnModel vpnModel = new VpnModel();
                vpnModel.setAccount(excleDatum.get(0));
                vpnModel.setPassWord(excleDatum.get(1));
                vpnModel.setType(type);
                vpnModel.setCreaterTime(new Date());
                vpnModel.setLastUpdateTime(new Date());
                vpnModel.setState(Account.VALID);
                vpnModel.setId(PrimaryUtil.getId());

                if (BaseUtils.isNotBlank(excleDatum.get(2))) {
                    vpnModel.setRegion(excleDatum.get(2));
                }

                try {
                    addVpn(vpnModel);
                } catch (Exception e) {
                    VpnErrorLogModel errorLogModel = new VpnErrorLogModel();
                    errorLogModel.setAccount(vpnModel.getAccount());
                    errorLogModel.setLog(BaseUtils.getStackTrace(e));
                    errorLogModel.setBatchNo(bacthNo);
                    try {
                        this.getMongoDao().getTemplate().save(errorLogModel, VpnErrorLogModel.tableName);
                    } catch (Exception ex) {
                        logger.error("插入mongo错误数据失败:{}", vpnModel);
                    }
                }
            }

            logger.info("assembleData:-----------END");

            return bacthNo;
        } catch (Exception e) {
            logger.error("组装数据失败", e);
            throw new BusinessException("组装数据失败");
        }
    }

    /**
     * 加载vpn数据
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public VpnModel loadVpn(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().load(VpnModel.class, id);
        } catch (Exception e) {
            logger.error("加载vpn数据失败", e);
            throw new BusinessException("加载vpn数据失败");
        }
    }

    @Override
    public synchronized VpnByDeviceModel getVpn(String type, String region, String deviceId) throws BusinessException {
        if (BaseUtils.isBlank(type) || BaseUtils.isBlank(deviceId)) {
            throw new BusinessException("缺少必要参数...");
        }

        if (vpnLock.containsKey(deviceId)) {
            logger.warn("-----------{}重复获取{}类型vpn------------", deviceId, type);
            return vpnLock.get(deviceId);
        }

        VpnByDeviceModel resultData = getRandVpn(type, region);

        if (null != resultData) {
            lockVpnData(resultData.getId(), deviceId);
            vpnLock.put(deviceId, resultData);
        }

        return resultData;
    }

    /**
     * 锁vpn
     *
     * @param id
     * @param deviceId
     * @throws BusinessException
     */
    private void lockVpnData(String id, String deviceId) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " UPDATE VPN SET DEVICE_ID =:DEVICE_ID WHERE ID =:ID ";
            params.put("DEVICE_ID", deviceId);
            params.put("ID", id);
            this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("查询vpn类别数据失败", e);
            throw new BusinessException("查询vpn类别数据失败");
        }
    }

    /**
     * 随机取一条vpn
     *
     * @param type
     * @param region
     * @return
     * @throws BusinessException
     */
    @Override
    public VpnByDeviceModel getRandVpn(String type, String region) throws BusinessException {
        if (BaseUtils.isBlank(type)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {

            Map<String, Object> params = new HashMap<>();
            String sql = " SELECT * FROM VPN WHERE TYPE =:TYPE AND STATE =:STATE AND ( DEVICE_ID IS NULL OR DEVICE_ID ='') ";
            params.put("TYPE", type);
            params.put("STATE", Account.VALID.name());

            if (BaseUtils.isNotBlank(region)) {
                sql += " AND REGION = :REGION ";
                params.put("REGION", region);
            }

            sql += " ORDER BY RAND() LIMIT 1 ";

            return this.getDao().selectOne(VpnByDeviceModel.class, sql, params);
        } catch (Exception e) {
            logger.error("获取vpn失败", e);
            throw new BusinessException("获取vpn失败");
        }
    }

    @Override
    public void unlockVpn(String deviceId, String isInVaild) throws BusinessException {
        if (BaseUtils.isBlank(deviceId)) {
            throw new BusinessException("缺少必要参数...");
        }

        if (!vpnLock.containsKey(deviceId)) {
            return;
        }

        VpnByDeviceModel vpnModel = vpnLock.get(deviceId);
        vpnLock.remove(deviceId);

        setUnlockVpnAndState(vpnModel.getId(),isInVaild);

    }


    private void setUnlockVpnAndState(String id, String isInVaild) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            String sql = " UPDATE VPN SET DEVICE_ID = NULL ";
            params.put("ID", id);

            if (BaseUtils.isNotBlank(isInVaild) && isInVaild.equals("Y")) {
                sql += " ,STATE = 'INVALID' ";
            }

            sql += " WHERE ID =:ID ";

            this.getDao().exec(sql, params);
        } catch (Exception e) {
            logger.error("查询vpn类别数据失败", e);
            throw new BusinessException("查询vpn类别数据失败");
        }
    }

}
