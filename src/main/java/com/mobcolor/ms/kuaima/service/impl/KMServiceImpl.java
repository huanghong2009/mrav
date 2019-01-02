package com.mobcolor.ms.kuaima.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import com.mobcolor.ms.kuaima.model.KMListModel;
import com.mobcolor.ms.kuaima.model.KMModel;
import com.mobcolor.ms.kuaima.model.dto.KMDTO;
import com.mobcolor.ms.kuaima.model.dto.KMListDTO;
import com.mobcolor.ms.kuaima.service.KMService;
import com.mobcolor.ms.youjia.enums.Platform;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018/8/1
 */
@Service
public class KMServiceImpl extends BaseSupportServiceImpl implements KMService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(KMServiceImpl.class);

    private static Map<String, String> deviceTaskLock = new HashMap<>();

    /**
     * 创建任务
     *
     * @param kmModel
     * @throws BusinessException
     */
    @Override
    public void createTask(KMModel kmModel) throws BusinessException {
        if (BaseUtils.isBlank(kmModel.getName()) || BaseUtils.isBlank(kmModel.getExecTime()) ||
                BaseUtils.isBlank(kmModel.getConfig()) || BaseUtils.isBlank(kmModel.getScriptNames()) ||
                kmModel.getTaskTotal() <= 0 || null == kmModel.getPlatform()) {
            throw new BusinessException("缺少必要参数或参数不合法...");
        }

        kmModel.setCreaterTime(new Date());
        kmModel.setState(TaskDetail.WAIT);
        kmModel.setId(PrimaryUtil.getId());
        kmModel.setSucceedNumber(0);
        kmModel.setFailedNumber(0);


        JSONArray configs = null;

        List<KMListModel> kmListModelList = new ArrayList<>();
        KMListModel kmListModel = null;

        try {
            configs = JSONArray.parseArray(kmModel.getConfig());

            /**
             * data: startDate - endDate - number
             */
            for (Object config : configs) {
                kmListModel = new KMListModel();
                String data = (String) config;
                String[] datas = data.split("-");

                if (datas.length != 3)
                    throw new BusinessException("格式不对");

                String[] nums = datas[2].split("~");

                if (nums.length != 2)
                    throw new BusinessException("格式不对");

                int num = BaseUtils.getRandom(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));

                kmListModel.setName(kmModel.getName());
                kmListModel.setCreaterTime(new Date());
                kmListModel.setScriptNames(kmModel.getScriptNames());
                kmListModel.setTotal(kmModel.getTaskTotal() * num / 10000);
                kmListModel.setSucceedNumber(0);
                kmListModel.setFailedNumber(0);
                kmListModel.setId(PrimaryUtil.getId());
                kmListModel.setTaskId(kmModel.getId());
                kmListModel.setStartTime(this.sdf1.parse(kmModel.getExecTime() + " " + datas[0] + ":00"));
                kmListModel.setEndTime(this.sdf1.parse(kmModel.getExecTime() + " " + datas[1] + ":00"));
                kmListModel.setState(TaskDetail.WAIT);
                kmListModel.setPlatform(kmModel.getPlatform());
                kmListModel.setExecTime(kmModel.getExecTime());
                kmListModelList.add(kmListModel);
            }

        } catch (Exception e) {
            logger.error("自定义配置格式错误", e);
            throw new BusinessException("自定义配置格式错误");
        }

        insertKMModel(kmModel);
        insertKMListModel(kmListModelList);

    }

    /**
     * 插入数据
     *
     * @param kmModel
     * @throws BusinessException
     */
    @Override
    public void insertKMModel(KMModel kmModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(kmModel.getId())) {
                kmModel.setId(PrimaryUtil.getId());
            }
            this.getDao().insert(kmModel);
        } catch (Exception e) {
            logger.error("插入数据失败", e);
            throw new BusinessException("插入数据失败");
        }
    }

    /**
     * 插入快马的数据list
     *
     * @param kmListModelList
     * @throws BusinessException
     */
    @Override
    public void insertKMListModel(List<KMListModel> kmListModelList) throws BusinessException {
        try {
            this.getDao().insertBatch(kmListModelList);
        } catch (Exception e) {
            logger.error("批量插入kmlist失败", e);
            throw new BusinessException("批量插入kmlist失败");
        }
    }

    /**
     * 修改任务状态
     *
     * @param id
     * @param taskDetail
     * @throws BusinessException
     */
    @Override
    @Transactional
    public void updateKMState(String id, TaskDetail taskDetail) throws BusinessException {
        if (BaseUtils.isBlank(id) || null == taskDetail) {
            throw new BusinessException("缺少必要参数...");
        }

        String sql1 = "UPDATE KM SET STATE = :STATE WHERE ID = :ID ";
        String sql2 = "UPDATE KM_LIST SET STATE = :STATE WHERE TASK_ID = :ID ";

        Map<String, Object> parmas = new HashMap<>();
        parmas.put("STATE", taskDetail.name());
        parmas.put("ID", id);

        try {
            this.getDao().exec(sql1, parmas);
            this.getDao().exec(sql2, parmas);
        } catch (Exception e) {
            logger.error("修改任务状态失败", e);
            throw new BusinessException("修改任务记录失败");
        }

    }

    /**
     * 修改任务状态
     *
     * @param kmListModel
     * @throws BusinessException
     */
    @Override
    public void updateKMListState(KMListModel kmListModel) throws BusinessException {
        if (BaseUtils.isBlank(kmListModel.getId())) {
            throw new BusinessException("缺少必要参数...");
        }
        Map<String, Object> parmas = new HashMap<>();
        parmas.put("ID", kmListModel.getId());

        String sql = "UPDATE KM_LIST SET ";
        boolean isFirst = true;

        if (null != kmListModel.getState()) {
            parmas.put("STATE", kmListModel.getState().name());
            sql += " STATE = :STATE ";
            isFirst = false;
        }

        if (!isFirst && kmListModel.getTotal() > 0) {
            sql += ",";
        }

        if (kmListModel.getTotal() > 0) {
            sql += " TOTAL = :TOTAL ";
            parmas.put("TOTAL", kmListModel.getTotal());
            isFirst = false;
        }

        if (isFirst) {
            throw new BusinessException("缺少必要参数...");
        }

        sql += " WHERE ID = :ID ";

        try {
            this.getDao().exec(sql, parmas);
        } catch (Exception e) {
            logger.error("修改任务list状态失败", e);
            throw new BusinessException("修改任务list记录失败");
        }
    }

    /**
     * 删除
     *
     * @param id
     * @throws BusinessException
     */
    @Override
    public void deleteKMModel(String id) throws BusinessException {
        try {
            this.getDao().delete(KMModel.class, id);
            this.getDao().exec("DELETE FROM KM_LIST WHERE TASK_ID = '" + id + "'");
        } catch (Exception e) {
            logger.error("删除任务失败", e);
            throw new BusinessException("删除任务失败");
        }
    }

    /**
     * 删除任务
     *
     * @param id
     * @throws BusinessException
     */
    @Override
    public void deleteKMListModel(String id) throws BusinessException {
        try {
            this.getDao().delete(KMListModel.class, id);
        } catch (Exception e) {
            logger.error("删除任务失败", e);
            throw new BusinessException("删除任务失败");
        }
    }

    /**
     * 任务回调
     *
     * @param taskId
     * @param taskListId
     * @param taskDetail
     * @throws BusinessException
     */
    @Override
    public void taskCallBack(String taskId, String taskListId, TaskDetail taskDetail, String idfa, String uuid) throws BusinessException {
        if (BaseUtils.isBlank(taskId) || BaseUtils.isBlank(taskListId) || null == taskDetail || BaseUtils.isBlank(idfa) || BaseUtils.isBlank(uuid)) {
            throw new BusinessException("缺少必要参数...");
        }

        if (deviceTaskLock.containsKey(idfa) && deviceTaskLock.get(idfa).equals(uuid)) {
            logger.error("任务重复提交:{},{} ", idfa, uuid);
            throw new BusinessException("任务重复提交");
        }

        deviceTaskLock.put(idfa, uuid);

        String sql1 = "";
        String sql2 = "";

        if (taskDetail.equals(TaskDetail.SUCCEED)) {
            sql1 = "UPDATE KM SET SUCCEED_NUMBER = SUCCEED_NUMBER + 1 WHERE ID = :ID ";
            sql2 = "UPDATE KM_LIST SET SUCCEED_NUMBER = SUCCEED_NUMBER + 1 WHERE ID = :LIST_ID ";
        } else if (taskDetail.equals(TaskDetail.FAILED)) {
            sql1 = "UPDATE KM SET FAILED_NUMBER = FAILED_NUMBER + 1 WHERE ID = :ID ";
            sql2 = "UPDATE KM_LIST SET FAILED_NUMBER = FAILED_NUMBER + 1 WHERE ID = :LIST_ID ";
        } else {
            throw new BusinessException("未知的状态参数");
        }

        Map<String, Object> parmas = new HashMap<>();
        parmas.put("LIST_ID", taskListId);
        parmas.put("ID", taskId);

        try {
            this.getDao().exec(sql1, parmas);
            this.getDao().exec(sql2, parmas);
        } catch (Exception e) {
            logger.error("修改任务进度失败", e);
            throw new BusinessException("修改任务进度失败");
        }

    }

    /**
     * 获取任务
     */
    @Override
    public KMListModel getTask(Platform platform,String srciptName) throws BusinessException {
        if (null == platform || BaseUtils.isBlank(srciptName)) {
            throw new BusinessException("缺少必要参数...");
        }
        return getRandRunningTask(platform,srciptName);
    }

    /**
     * 查询任务分页
     *
     * @param kmdto
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<KMModel> queryKMModelByPage(KMDTO kmdto) throws BusinessException {
        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM KM WHERE 1=1 ";

            if (BaseUtils.isNotBlank(kmdto.getIsQueryToDay()) && kmdto.getIsQueryToDay().equals("Y")) {
                sql += " AND EXEC_TIME = ?";
                params.add(this.sdf.format(new Date()));

            } else if (BaseUtils.isNotBlank(kmdto.getExecTime())) {

                sql += " AND EXEC_TIME = ?";
                params.add(kmdto.getExecTime());
            }

            if (BaseUtils.isNotBlank(kmdto.getName())) {
                sql += " AND NAME LIKE ?";
                params.add("%" + kmdto.getName() + "%");
            }

            sql += " ORDER BY CREATER_TIME DESC ";

            return this.getDao().pageQuery(KMModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    kmdto.getTo_page(), kmdto.getPage_size());
        } catch (Exception e) {
            logger.error("查询任务记录失败", e);
            throw new BusinessException("查询任务记录失败");
        }
    }

    /**
     * 查询任务分页
     *
     * @param kmListDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<KMListModel> queryKMModelListByPage(KMListDTO kmListDTO) throws BusinessException {
        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM KM_LIST WHERE 1=1 ";

            if (BaseUtils.isNotBlank(kmListDTO.getIsQueryToDay()) && kmListDTO.getIsQueryToDay().equals("Y")) {
                sql += " AND EXEC_TIME = ?";
                params.add(this.sdf.format(new Date()));

            } else if (BaseUtils.isNotBlank(kmListDTO.getExecTime())) {
                sql += " AND EXEC_TIME = ?";
                params.add(kmListDTO.getExecTime());
            }

            if (BaseUtils.isNotBlank(kmListDTO.getTaskId())) {
                sql += " AND TASK_ID = ?";
                params.add(kmListDTO.getTaskId());
            }

            if (BaseUtils.isNotBlank(kmListDTO.getName())) {
                sql += " AND NAME LIKE ?";
                params.add("%" + kmListDTO.getName() + "%");
            }

            if (BaseUtils.isNotBlank(kmListDTO.getSortName())) {
                sql += " ORDER BY " + BaseUtils.getServerFiled(KMListModel.class, kmListDTO.getSortName());
                if (kmListDTO.getSortOrder().equals("asc"))
                    sql += " ASC";
                else
                    sql += " DESC";
            } else {
                sql += " ORDER BY START_TIME ";
            }

            return this.getDao().pageQuery(KMListModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    kmListDTO.getTo_page(), kmListDTO.getPage_size());
        } catch (Exception e) {
            logger.error("查询任务记录失败", e);
            throw new BusinessException("查询任务记录失败");
        }
    }


    /**
     * 随机获得一个正在运行中任务
     *
     * @return
     * @throws BusinessException
     */
    public KMListModel getRandRunningTask(Platform platform,String srciptName) throws BusinessException {
        try {
            Map<String, Object> params = new HashMap<>();
            String sql = "SELECT * FROM KM_LIST WHERE NOW() >= START_TIME AND NOW() < END_TIME AND STATE =:STATE AND SUCCEED_NUMBER - FAILED_NUMBER < TOTAL AND PLATFORM = :PLATFORM AND SCRIPT_NAMES = :SCRIPT_NAMES  ORDER BY RAND() LIMIT 1 ";
            params.put("STATE", TaskDetail.RUNNING.name());
            params.put("PLATFORM", platform.name());
            params.put("SCRIPT_NAMES",srciptName);
            return this.getDao().selectOne(KMListModel.class, sql, params);
        } catch (Exception e) {
            logger.error("获得随机运行中的任务失败", e);
            throw new BusinessException("获得随机运行中的任务失败");
        }
    }


}
