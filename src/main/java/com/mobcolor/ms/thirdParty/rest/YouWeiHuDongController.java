package com.mobcolor.ms.thirdParty.rest;


import com.mobcolor.ms.thirdParty.model.YouWeiHuDongModel;
import com.mobcolor.ms.thirdParty.model.dto.YouWeiHuDongDTO;
import com.mobcolor.ms.thirdParty.service.YouWeiHuDongService;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@RestController
@RequestMapping(value = "/third-party")
public class YouWeiHuDongController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(YouWeiHuDongController.class);


    @Resource
    private YouWeiHuDongService youWeiHuDongService;


    /**
     * @api {post} /third-party/get-youweihudong-task [有为互动]获得一条任务
     * @apiSampleRequest /third-party/get-youweihudong-task
     * @apiName get-youweihudong-task
     * @apiGroup /third-party/youweihudong
     * @apiDescription [有为互动]获得一条任务
     * @apiParam {String} appName 应用名称
     * @apiParam {String} appId 应用id
     * @apiParam {String} platformChannelId 应用渠道id
     * @apiParam {String} platformChannelName 应用渠道名称
     * @apiParam {String} word 用户搜索的关键词 UTF-8 -urlencode编码
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'网易应用'，
     * }
     *
     * @apiSuccess {String} id 任务id
     * @apiSuccess {String} idfa  idfa信息
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     *  'id':"4567890",
     *  'idfa':'xxxxxxxvxvvxvxvxvxvvx'
     * }
     * }
     * }
     */
    @RequestMapping(value = "/get-youweihudong-task", method = RequestMethod.POST)
    public ServerResponse getYouWeiHuDongTask(YouWeiHuDongModel youWeiHuDongModel) {
        ServerResponse response = new ServerResponse();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            youWeiHuDongModel.setIp(BaseUtils.getIpAddr(request));
            response.setData(youWeiHuDongService.getYouWeiHuDongTask(youWeiHuDongModel));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /third-party/query-youweihudong-by-page [有为互动]分页查询有为互动任务
     * @apiSampleRequest /third-party/query-youweihudong-by-page
     * @apiName query-youweihudong-by-page
     * @apiGroup /third-party/youweihudong
     * @apiDescription [应用管理]分页查询应用
     * @apiParam {String} [cid] 有位互动分配给己方的ID
     * @apiParam {String} [appName] 应用名称（模糊搜索）
     * @apiParam {String} [appId] 应用苹果id
     * @apiParam {String} [type]  RETAINED("留存"),ADD("新增");
     * @apiParam {String} [state] SUCCEED("成功"),RUNNING("运行中"),FAILED("失败"),SUSPEND("暂停");
     * @apiParam {String} [isCallBcak] 是否收到回调 Y/N
     * @apiParam {String} [createrTime] 创建时间
     * @apiParam {String} [updateTime] 修改时间
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  appName:'爱奇艺',
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 任务id
     * @apiSuccess {String} cid  有位互动分配给己方的ID
     * @apiSuccess {String} appName  应用名称
     * @apiSuccess {String} ip  ip地址
     * @apiSuccess {String} idfa  idfa
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'createTime':‘206-0-08  12:35:32’
     *  }
     * }
     */
    @RequestMapping(value = "/query-youweihudong-by-page", method = RequestMethod.POST)
    public ServerResponse queryYouWeiHuDongByPage(YouWeiHuDongDTO youWeiHuDongDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(youWeiHuDongService.queryYouWeiHuDongByPage(youWeiHuDongDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }



    /**
     * @api {post} /third-party/youweihudong-call-back-device [有为互动]任务回调-设备
     * @apiSampleRequest /third-party/youweihudong-call-back-device
     * @apiName youweihudong-call-back-device
     * @apiGroup /third-party/youweihudong
     * @apiDescription [有为互动]任务回调-设备
     * @apiParam {String} id 任务id
     * @apiParam {String} state 任务状态 SUCCEED("成功"),FAILED("失败"),
     * @apiParam {String} [device] 设备信息
     * @apiParam {String} [otherInfo] 其他信息
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'fhjj1233',
     *  'state':'SUCCEED',
     *  'device':'ft821',
     *  'otherInfo':'你自己想存什么什么，你怎么存，后台怎么返回给你'
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * }
     */
    @RequestMapping(value = "/youweihudong-call-back-device", method = RequestMethod.POST)
    public ServerResponse updateYouWeiHuDong(String id, TaskDetail state, String device, String otherInfo) {
        this.loggerParam("updateYouWeiHuDong",id,state,device,otherInfo);
        ServerResponse response = new ServerResponse();
        if (BaseUtils.isBlank(id) || null == state) {
            throw new BusinessException("缺少必要参数...");
        }
        try {
            YouWeiHuDongModel youWeiHuDongModel  = new YouWeiHuDongModel();
            youWeiHuDongModel.setId(id);
            youWeiHuDongModel.setUpdateTime(new Date());
            youWeiHuDongModel.setState(state);
            if (BaseUtils.isNotBlank(device) ) {
                youWeiHuDongModel.setDevice(device);
            }
            if (BaseUtils.isNotBlank(otherInfo)){
                youWeiHuDongModel.setOtherInfo(otherInfo);
            }
            youWeiHuDongService.updateYouWeiHuDong(youWeiHuDongModel);

            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }



    /**
     * @api {get} /third-party/youweihudong-call-back-advertiser [有为互动]任务回调-广告主
     * @apiSampleRequest /third-party/youweihudong-call-back-advertiser
     * @apiName youweihudong-call-back-advertiser
     * @apiGroup /third-party/youweihudong
     * @apiDescription [有为互动]任务回调-广告主
     * @apiParam {String} taskId 任务id
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'fhjj1233'
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     *
     * }
     */
    @RequestMapping(value = "/youweihudong-call-back-advertiser", method = RequestMethod.GET)
    public ServerResponse updateYouWeiHuDongAdvertiser(String taskId) {
        this.loggerParam("updateYouWeiHuDongAdvertiser",taskId);
        ServerResponse response = new ServerResponse();
        try {
            YouWeiHuDongModel youWeiHuDongModel  = new YouWeiHuDongModel();
            youWeiHuDongModel.setId(taskId);
            youWeiHuDongModel.setUpdateTime(new Date());
            youWeiHuDongModel.setIsCallBcak("Y");
            youWeiHuDongService.updateYouWeiHuDong(youWeiHuDongModel);

            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }



}
