package com.mobcolor.ms.thirdParty.service;

import com.mobcolor.ms.thirdParty.model.YouWeiHuDongModel;
import com.mobcolor.ms.thirdParty.model.dto.YouWeiHuDongDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public interface YouWeiHuDongService extends BaseService {

    /**
     * 获得一条有为互动任务
     * @param youWeiHuDongModel
     * @return
     * @throws BusinessException
     */
    YouWeiHuDongModel getYouWeiHuDongTask(YouWeiHuDongModel youWeiHuDongModel)throws BusinessException;

    /**
     * 新增一个有为互动数据
     * @param youWeiHuDongModel 有为互动数据
     */
    void addYouWeiHuDong(YouWeiHuDongModel youWeiHuDongModel) throws BusinessException;

    /**
     * 查询有为互动数据
     * @param youWeiHuDongDTO
     * @return
     * @throws BusinessException
     */
    PageVO<YouWeiHuDongModel> queryYouWeiHuDongByPage(YouWeiHuDongDTO youWeiHuDongDTO) throws BusinessException;

    /**
     * 修改有为互动数据内容
     * @param youWeiHuDongModel
     * @throws BusinessException
     */
    int updateYouWeiHuDong(YouWeiHuDongModel youWeiHuDongModel) throws BusinessException;

    /**
     * 删除一个有为互动数据
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    int deleteYouWeiHuDong(String id) throws BusinessException;

}
