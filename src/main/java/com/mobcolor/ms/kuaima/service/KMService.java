package com.mobcolor.ms.kuaima.service;

import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseService;
import com.mobcolor.ms.kuaima.model.KMListModel;
import com.mobcolor.ms.kuaima.model.KMModel;
import com.mobcolor.ms.kuaima.model.dto.KMDTO;
import com.mobcolor.ms.kuaima.model.dto.KMListDTO;
import com.mobcolor.ms.youjia.enums.Platform;
import com.mobcolor.ms.youjia.enums.TaskDetail;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018/8/1
 */
public interface KMService extends BaseService {

    /**
     * 创建任务
     *
     * @param kmModel
     * @throws BusinessException
     */
    void createTask(KMModel kmModel) throws BusinessException;

    /**
     * 插入数据
     *
     * @param kmModel
     * @throws BusinessException
     */
    void insertKMModel(KMModel kmModel) throws BusinessException;

    /**
     * 插入快马的任务list
     *
     * @param kmListModelList
     * @throws BusinessException
     */
    void insertKMListModel(List<KMListModel> kmListModelList) throws BusinessException;

    /**
     * 修改快马的状态
     *
     * @param id
     * @param taskDetail
     * @throws BusinessException
     */
    void updateKMState(String id, TaskDetail taskDetail) throws BusinessException;

    /**
     * 修改快马的状态
     *
     * @param kmListModel
     * @throws BusinessException
     */
    void updateKMListState(KMListModel kmListModel)throws BusinessException;

    /**
     * 删除
     * @param id
     * @throws BusinessException
     */
    void deleteKMModel(String id) throws BusinessException;

    /**
     * 删除
     * @param id
     * @throws BusinessException
     */
    void deleteKMListModel(String id) throws BusinessException;

    /**
     * 任务回调
     *
     * @param taskId
     * @param taskListId
     * @param taskDetail
     * @throws BusinessException
     */
    void taskCallBack(String taskId, String taskListId, TaskDetail taskDetail,String idfa,String uuid) throws BusinessException;

    /**
     * 获取任务
     * @throws BusinessException
     */
    KMListModel getTask(Platform platform,String srciptName) throws BusinessException;


    /**
     * 查询任务分页
     * @param kmdto
     * @return
     * @throws BusinessException
     */
    PageVO<KMModel> queryKMModelByPage(KMDTO kmdto) throws BusinessException;


    /**
     * 查询任务详细分页
     * @param kmListDTO
     * @return
     * @throws BusinessException
     */
    PageVO<KMListModel> queryKMModelListByPage(KMListDTO kmListDTO) throws BusinessException;
}
