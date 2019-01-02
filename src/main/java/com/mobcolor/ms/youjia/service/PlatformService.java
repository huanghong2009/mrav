package com.mobcolor.ms.youjia.service;


import com.mobcolor.ms.youjia.enums.Platform;
import com.mobcolor.ms.youjia.model.PlatformChannelModel;
import com.mobcolor.ms.youjia.model.dto.PlatformChannelDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface PlatformService extends BaseService {

    /**
     * 新增一个平台渠道
     * @param name 渠道名称
     * @param platform 平台
     */
    void addPlatform(String name, Platform platform) throws BusinessException;

    /**
     * 查询渠道信息
     * @param platformChannelDTO
     * @return
     * @throws BusinessException
     */
    PageVO<PlatformChannelModel> queryPlatformByPage(PlatformChannelDTO platformChannelDTO) throws BusinessException;

    /**
     * 查询渠道
     * @return
     * @throws BusinessException
     */
    List<PlatformChannelModel> selectPlatformChannelModelList()throws BusinessException;

    /**
     * 修改渠道信息
     * @param name
     * @throws BusinessException
     */
    int updatePlatform(String id,String name ) throws BusinessException;

    /**
     * 删除一个渠道
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deletePlatform(String id) throws BusinessException;


}
