package com.mobcolor.ms.youjia.service;


import com.mobcolor.ms.youjia.model.AdvertisementModel;
import com.mobcolor.ms.youjia.model.dto.AdvertisementDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface AdvertisementService extends BaseService {

    /**
     * 新增一个广告
     *
     * @param advertisementModel 广告
     */
    void addAdvertisement(AdvertisementModel advertisementModel) throws BusinessException;

    /**
     * 查询广告
     *
     * @param advertisementDTO
     * @return
     * @throws BusinessException
     */
    PageVO<AdvertisementModel> queryAdvertisementByPage(AdvertisementDTO advertisementDTO) throws BusinessException;

    /**
     * 修改广告内容
     *
     * @param advertisementModel
     * @throws BusinessException
     */
    int updateAdvertisement(AdvertisementModel advertisementModel) throws BusinessException;

    /**
     * 删除一个广告
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteAdvertisement(String id) throws BusinessException;

    /**
     * 获得一个广告
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    AdvertisementModel loadAdvertisement(String id) throws BusinessException;
}
