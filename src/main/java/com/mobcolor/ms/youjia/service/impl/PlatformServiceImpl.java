package com.mobcolor.ms.youjia.service.impl;


import com.mobcolor.ms.youjia.enums.Platform;
import com.mobcolor.ms.youjia.model.dto.PlatformChannelDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.stereotype.Service;
import com.mobcolor.ms.youjia.model.PlatformChannelModel;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@Service
public class PlatformServiceImpl extends BaseSupportServiceImpl implements com.mobcolor.ms.youjia.service.PlatformService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(PlatformServiceImpl.class);

    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 新增一个平台渠道
     *
     * @param name     渠道名称
     * @param platform 平台
     */
    public void addPlatform(String name, Platform platform) {
        PlatformChannelModel platformChannelModel = new PlatformChannelModel();
        platformChannelModel.setName(name);
        platformChannelModel.setCreaterTime(new Date());
        platformChannelModel.setPlatform(platform.getName());
        platformChannelModel.setId(PrimaryUtil.getId());
        try {
            this.getDao().insert(platformChannelModel);
        } catch (Exception e) {
            logger.error("新增渠道出错", e);
            throw new BusinessException("新增渠道出错");
        }
    }

    /**
     * 分页查询渠道
     *
     * @param platformChannelDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<PlatformChannelModel> queryPlatformByPage(PlatformChannelDTO platformChannelDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM PLATFORM WHERE 1=1 ";

            if (BaseUtils.isNotBlank(platformChannelDTO.getPlatform())) {
                sql += " AND PLATFORM = ?";
                params.add(platformChannelDTO.getPlatform());
            }

            if (BaseUtils.isNotBlank(platformChannelDTO.getName())) {
                sql += " AND NAME LIKE ?";
                params.add("%" + platformChannelDTO.getName() + "%");
            }
            PageVO<PlatformChannelModel> platformChannelModelPageVO = this.getDao().pageQuery(PlatformChannelModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    platformChannelDTO.getTo_page(), platformChannelDTO.getPage_size());
            return platformChannelModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询渠道失败", e);
            throw new BusinessException("分页查询渠道失败");
        }

    }

    /**
     * 查询渠道
     * @return
     * @throws BusinessException
     */
    @Override
    public List<PlatformChannelModel> selectPlatformChannelModelList() throws BusinessException {
        try {
            String sql = "SELECT * FROM PLATFORM ";
            return this.getDao().selectList(PlatformChannelModel.class,sql);
        } catch (Exception e) {
            logger.error("查询渠道信息失败", e);
            throw new BusinessException("查询渠道信息失败");
        }
    }

    /**
     * 修改渠道信息
     *
     * @param id   主键id
     * @param name 渠道名称
     * @throws BusinessException
     */
    @Override
    public int updatePlatform(String id, String name) throws BusinessException {
        try {
            if (BaseUtils.isBlank(id) || BaseUtils.isBlank(name)) {
                throw new BusinessException("缺少必要参数...");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("ID", id);
            params.put("NAME", name);
            return this.getDao().exec("UPDATE PLATFORM SET NAME = :NAME WHERE ID = :ID",params);
        } catch (Exception e) {
            logger.error("修改渠道信息失败", e);
            throw new BusinessException("修改渠道信息失败");
        }
    }

    /**
     * 根据id删除某个渠道数据
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deletePlatform(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            return this.getDao().delete(PlatformChannelModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }


}
