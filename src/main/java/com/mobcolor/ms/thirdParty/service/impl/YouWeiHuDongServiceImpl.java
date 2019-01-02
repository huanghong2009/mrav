
package com.mobcolor.ms.thirdParty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobcolor.ms.thirdParty.model.YouWeiHuDongModel;
import com.mobcolor.ms.thirdParty.model.dto.YouWeiHuDongDTO;
import com.mobcolor.ms.thirdParty.service.YouWeiHuDongService;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.httpCilent.HttpClientUtil;
import com.mobcolor.framework.utils.*;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.*;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018-01-18 19:15:31
 */
@Service
public class YouWeiHuDongServiceImpl extends BaseSupportServiceImpl implements YouWeiHuDongService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(YouWeiHuDongServiceImpl.class);
    private static final String CHECK_IDFA_IP_ADRESS = "http://47.93.68.176/channel/checkIdfa";
    private static final String CLICK_IDFA_IP_ADRESS = "http://47.93.68.176/channel/clickIdfa";

    private static final String CALL_BACK_URL = "http://118.190.151.150:8080/third-party/youweihudong-call-back-advertiser";

    private static final String CID="1046";

    @Override
    public YouWeiHuDongModel getYouWeiHuDongTask(YouWeiHuDongModel youWeiHuDongModel) throws BusinessException {
        if (BaseUtils.isBlank(youWeiHuDongModel.getAppId()) ||
                BaseUtils.isBlank(youWeiHuDongModel.getAppName()) ||
                BaseUtils.isBlank(youWeiHuDongModel.getWord()) ||
                BaseUtils.isBlank(youWeiHuDongModel.getPlatformChannelId()) ||
                BaseUtils.isBlank(youWeiHuDongModel.getPlatformChannelName())
                ) {
            throw new BusinessException("缺少必要参数...");
        }
        youWeiHuDongModel.setCid(CID);

        try {
            String id = PrimaryUtil.getId();
            String idfa = chaeckIdfa(youWeiHuDongModel,0);
            clickIdfa(youWeiHuDongModel,idfa,id);

//            String idfa  = UUIDUtils.createId_36().toUpperCase();
            youWeiHuDongModel.setId(id);
            youWeiHuDongModel.setIdfa(idfa);
            youWeiHuDongModel.setType(TaskList.ADD);
            youWeiHuDongModel.setState(TaskDetail.RUNNING);
            youWeiHuDongModel.setIsCallBcak("N");
            addYouWeiHuDong(youWeiHuDongModel);
            return youWeiHuDongModel;
        } catch (Exception e) {
            logger.error("获取有为互动任务失败", e);
            throw new BusinessException("获取有为互动任务失败:"+e.getMessage());
        }
    }

    /**
     * 检查idfa
     * @param youWeiHuDongModel
     * @param num
     * @return
     * @throws BusinessException
     */
    public String chaeckIdfa(YouWeiHuDongModel youWeiHuDongModel,int num) throws BusinessException {
        String idfa  = UUIDUtils.createId_36().toUpperCase();
//        String idfa ="BA8943AE-E8C3-4EA8-AA42-98529B261ED0";
        try {
            Map<String,String> params = new HashMap<>();
            params.put("cid",youWeiHuDongModel.getCid());
            params.put("appid",youWeiHuDongModel.getAppId());
            params.put("idfa",idfa);
            String result = HttpClientUtil.doGet(BaseUtils.httpGetParamsSplic(CHECK_IDFA_IP_ADRESS,params));
            logger .info("-------------{}:{}--------",CHECK_IDFA_IP_ADRESS,result);
            JSONObject resultData = (JSONObject) JSONObject.parse(result);
            int status = resultData.getInteger("status");

            if (status != 20000){
                throw new BusinessException("---------"+String.valueOf(status)+":"+resultData.getString("message"));
            }

            JSONObject httpResult = resultData.getJSONObject("result");

            boolean  allowed = httpResult.getBoolean("allowed");

            if (!allowed){
                num ++;
                if (num <5){
                    idfa = chaeckIdfa(youWeiHuDongModel,num);
                }else {
                    logger.error("---------连续生成5次idfa都判断为重复------");
                    throw new BusinessException("---------连续生成5次idfa都判断为重复------");
                }
            }
        } catch (Exception e) {
            logger.error("判断idfa是否重复出现异常", e);
            throw new BusinessException("判断idfa是否重复出现异常："+e.getMessage());
        }
        return idfa;
    }

    /**
     * 点击idfa
     * @param youWeiHuDongModel
     * @param idfa
     * @return
     * @throws BusinessException
     */
    public void clickIdfa(YouWeiHuDongModel youWeiHuDongModel,String idfa,String id) throws BusinessException {
        try {
            Map<String,String> params = new HashMap<>();
            params.put("cid",youWeiHuDongModel.getCid());
            params.put("appid",youWeiHuDongModel.getAppId());
            params.put("idfa",idfa);
            params.put("ip",youWeiHuDongModel.getIp());
            params.put("word", URLEncoder.encode(youWeiHuDongModel.getWord(), "utf-8"));
            params.put("callbackUrl",URLEncoder.encode(CALL_BACK_URL+"?taskId="+id, "utf-8"));
            String result = HttpClientUtil.doGet(BaseUtils.httpGetParamsSplic(CLICK_IDFA_IP_ADRESS,params));
            logger.info(result);
            JSONObject resultData = (JSONObject) JSONObject.parse(result);
            int status = resultData.getInteger("status");

            if (status != 20000){
                throw new BusinessException("---------"+String.valueOf(status)+":"+resultData.getString("message"));
            }

            JSONObject httpResult = resultData.getJSONObject("result");

            boolean  success = httpResult.getBoolean("success");

            if (!success){
                logger.error("---------通知失败------{}",idfa);
                throw new BusinessException("---------通知失败:"+idfa+"------");
            }
        } catch (Exception e) {
            logger.error("判断idfa是否重复出现异常", e);
            throw new BusinessException("判断idfa是否重复出现异常："+e.getMessage());
        }
    }

    /**
     * 新增一个有为互动数据
     *
     * @param youWeiHuDongModel 有为互动数据
     */
    public void addYouWeiHuDong(YouWeiHuDongModel youWeiHuDongModel) {

        if (BaseUtils.isBlank(youWeiHuDongModel.getId())){
            youWeiHuDongModel.setId(PrimaryUtil.getId());
        }

        youWeiHuDongModel.setCreaterTime(new Date());
        youWeiHuDongModel.setUpdateTime(new Date());
        youWeiHuDongModel.setState(TaskDetail.RUNNING);
        try {
            this.getDao().insert(youWeiHuDongModel);
        } catch (Exception e) {
            logger.error("新增有为互动数据失败", e);
            throw new BusinessException("新增有为互动数据失败");
        }
    }

    /**
     * 分页查询有为互动数据
     *
     * @param youWeiHuDongDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageVO<YouWeiHuDongModel> queryYouWeiHuDongByPage(YouWeiHuDongDTO youWeiHuDongDTO) throws BusinessException {

        try {
            List<String> params = new ArrayList<String>();
            String sql = "SELECT * FROM OTHER_YOUWEIHUDONG WHERE 1=1 ";

            if (BaseUtils.isNotBlank(youWeiHuDongDTO.getCid())) {
                sql += " AND CID = ?";
                params.add(youWeiHuDongDTO.getCid());
            }

            if (BaseUtils.isNotBlank( youWeiHuDongDTO.getAppId())) {
                sql += " AND APP_ID = ?";
                params.add(youWeiHuDongDTO.getAppId());
            }

            if (null != youWeiHuDongDTO.getType()) {
                sql += " AND TYPE = ?";
                params.add(youWeiHuDongDTO.getType().name());
            }

            if (null != youWeiHuDongDTO.getState()) {
                sql += " AND STATE = ?";
                params.add(youWeiHuDongDTO.getState().name());
            }

            if (BaseUtils.isNotBlank(youWeiHuDongDTO.getIsCallBcak())) {
                sql += " AND IS_CALL_BACK = ?";
                params.add(youWeiHuDongDTO.getIsCallBcak());
            }

            if (BaseUtils.isNotBlank(youWeiHuDongDTO.getCreaterTime())) {
                sql += " AND CREATER_TIME LIKE ?";
                params.add(youWeiHuDongDTO.getCreaterTime() + "%");
            }
            if (BaseUtils.isNotBlank(youWeiHuDongDTO.getUpdateTime())) {
                sql += " AND UPDATE_TIME LIKE ?";
                params.add(youWeiHuDongDTO.getUpdateTime() + "%");
            }

            if (BaseUtils.isNotBlank(youWeiHuDongDTO.getAppName())) {
                sql += " AND APP_NAME LIKE ?";
                params.add("%" + youWeiHuDongDTO.getAppName() + "%");
            }

            PageVO<YouWeiHuDongModel> youWeiHuDongModelPageVO = this.getDao().pageQuery(YouWeiHuDongModel.class, sql,
                    params.size() > 0 ? params.toArray(new Object[params.size()]) : null,
                    youWeiHuDongDTO.getTo_page(), youWeiHuDongDTO.getPage_size());
            return youWeiHuDongModelPageVO;
        } catch (Exception e) {
            logger.error("分页查询有为互动数据失败", e);
            throw new BusinessException("分页查询有为互动数据失败");
        }

    }

    /**
     * 修改有为互动数据
     *
     * @param youWeiHuDongModel 有为互动数据内容
     * @throws BusinessException
     */
    @Override
    public int updateYouWeiHuDong(YouWeiHuDongModel youWeiHuDongModel) throws BusinessException {
        try {
            if (BaseUtils.isBlank(youWeiHuDongModel.getId()) ) {
                throw new BusinessException("缺少必要参数...");
            }
            return this.getDao().update(youWeiHuDongModel);
        } catch (Exception e) {
            logger.error("修改有为互动数据信息失败", e);
            throw new BusinessException("修改有为互动数据信息失败");
        }
    }

    /**
     * 根据id删除某个有为互动数据
     *
     * @param id 主键id
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteYouWeiHuDong(String id) throws BusinessException {
        if (BaseUtils.isBlank(id)) {
            throw new BusinessException("缺少必要参数...");

        }
        try {
            return this.getDao().delete(YouWeiHuDongModel.class, id);
        } catch (Exception e) {
            logger.error("删除数据失败", e);
            throw new BusinessException("删除数据失败");
        }
    }


   
}
