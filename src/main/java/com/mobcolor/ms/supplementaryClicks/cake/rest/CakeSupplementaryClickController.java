package com.mobcolor.ms.supplementaryClicks.cake.rest;

import com.alibaba.fastjson.JSONArray;

import com.mobcolor.ms.supplementaryClicks.cake.model.CakeSupplementaryClickModel;
import com.mobcolor.ms.supplementaryClicks.cake.model.dto.CakeSupplementaryClickDTO;
import com.mobcolor.ms.supplementaryClicks.cake.service.CakeSupplementaryClickService;
import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.enums.SupplementaryClick;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@RestController
@RequestMapping(value = "/cake-supplementary-click")
public class CakeSupplementaryClickController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(CakeSupplementaryClickController.class);


    @Resource
    private CakeSupplementaryClickService cakeSupplementaryClickService;


    /**
     * @api {post} /cake-supplementary-click/add-cake-supplementary-click [cake补量]添加cake补量
     * @apiSampleRequest /cake-supplementary-click/add-cake-supplementary-click
     * @apiName add-cake-supplementary-click
     * @apiGroup /cake-supplementary-click
     * @apiDescription [cake补量]添加cake补量
     * @apiParam {String} taskListId 任务列表id
     * @apiParam {String} originalAdvertisementId 原广告id
     * @apiParam {String} clickUrl 点击连接
     * @apiParam {Number} scheduleStart 开始进度
     * @apiParam {Number} scheduleEnd 结束进度
     * @apiParamExample {json} 参数示例:
     * {
     *  'taskListId':'1233',
     *  'originalAdvertisementId':'234321',
     *  'clickUrl':'www.baidu.com',
     *  'scheduleStart':200,
     *  'scheduleEnd':300
     * }
     *
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     *  1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/add-cake-supplementary-click", method = RequestMethod.POST)
    public ServerResponse addCakeSupplementaryClick(CakeSupplementaryClickModel cakeSupplementaryClickModel) {
        ServerResponse response = new ServerResponse();
        try {
            cakeSupplementaryClickService.addCakeSupplementaryClick(cakeSupplementaryClickModel);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /cake-supplementary-click/select-cake-supplementary-click [cake补量]查询cake补量
     * @apiSampleRequest /cake-supplementary-click/select-cake-supplementary-click
     * @apiName select-cake-supplementary-click
     * @apiGroup /cake-supplementary-click
     * @apiDescription [cake补量]查询cake补量
     * @apiParam {String} execTime 执行时间
     * @apiParam {String} state 状态  RUNNING("运行中"),SUSPEND("暂停");
     * @apiParamExample {json} 参数示例:
     * {
     *  'execTime'：'2010-01-28'，
     *  'state'：'RUNNING'
     * }
     * @apiSuccess {String} id 主键
     * @apiSuccess {String} taskListId 任务list id
     * @apiSuccess {String} originalAdvertisementId 原广告id
     * @apiSuccess {String} scheduleStart 点击开始进度
     * @apiSuccess {String} scheduleEnd 点击结束进度
     * @apiSuccess {String} clickUrl 点击连接
     * @apiSuccess {String} state 状态
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *
     *  }
     * }
     */
    @RequestMapping(value = "/select-cake-supplementary-click", method = RequestMethod.POST)
    public ServerResponse selectCakeSupplementaryClicks(CakeSupplementaryClickDTO cakeSupplementaryClickDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(cakeSupplementaryClickService.selectCakeSupplementaryClicks(cakeSupplementaryClickDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /cake-supplementary-click/get-cake-supplementary-click [cake补量]获得cake补量
     * @apiSampleRequest /cake-supplementary-click/get-cake-supplementary-click
     * @apiName get-cake-supplementary-click
     * @apiGroup /cake-supplementary-click
     * @apiDescription [cake补量]获得cake补量
     * @apiParam {String} region 地区
     * @apiParamExample {json} 参数示例:
     * {
     * }
     * @apiSuccess {String} id 主键
     * @apiSuccess {String} taskListId 任务list id
     * @apiSuccess {String} originalAdvertisementId 原广告id
     * @apiSuccess {String} scheduleStart 点击开始进度
     * @apiSuccess {String} scheduleEnd 点击结束进度
     * @apiSuccess {String} clickUrl 点击连接
     * @apiSuccess {String} state 状态
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *
     *  }
     * }
     */
    @RequestMapping(value = "/get-cake-supplementary-click", method = RequestMethod.GET)
    public ServerResponse getCakeSupplementaryClicks(String region) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(cakeSupplementaryClickService.getCakeSupplementaryClicks(region));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /cake-supplementary-click/update-cake-supplementary-click-schedule-start [cake补量]给补点击开始进度数量+1
     * @apiSampleRequest /cake-supplementary-click/update-cake-supplementary-click-schedule-start
     * @apiName update-cake-supplementary-click-schedule-start
     * @apiGroup /cake-supplementary-click
     * @apiDescription [cake补量]给补点击开始进度数量+1
     * @apiParam {String} execTime 执行时间
     * @apiParamExample {json} 参数示例:
     * {
     *    'execTime'：'2010-01-28'
     * }
     *
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     *  1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/update-cake-supplementary-click-schedule-start", method = RequestMethod.POST)
    public ServerResponse updateCakeSupplementaryClickScheduleStart(String execTime) {
        ServerResponse response = new ServerResponse();
        try {
            response.setData(cakeSupplementaryClickService.updateCakeSupplementaryClickScheduleStart(execTime));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /cake-supplementary-click/update-cake-supplementary-click-schedule-end [cake补量]修改补点击结束进度
     * @apiSampleRequest /cake-supplementary-click/update-cake-supplementary-click-schedule-end
     * @apiName update-cake-supplementary-click-schedule-end
     * @apiGroup /cake-supplementary-click
     * @apiDescription [cake补量]修改补点击结束进度
     * @apiParamExample {json} 参数示例:
     * {
     *    'taskListId'：'123454'，
     *    'num':100
     * }
     *
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     *  1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/update-cake-supplementary-click-schedule-end", method = RequestMethod.POST)
    public ServerResponse updateCakeSupplementaryClickScheduleEnd(String taskListId) {
        ServerResponse response = new ServerResponse();
        try {
            response.setData(cakeSupplementaryClickService.updateCakeSupplementaryClickScheduleEnd(taskListId));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /cake-supplementary-click/update-cake-supplementary-click-state [cake补量]修改补点击状态
     * @apiSampleRequest /cake-supplementary-click/update-cake-supplementary-click-state
     * @apiName update-cake-supplementary-click-state
     * @apiGroup /cake-supplementary-click
     * @apiDescription [cake补量]修改补点击状态
     * @apiParam {String} taskListIds json 格式的 任务list id
     * @apiParam {String} supplementaryClick 状态 RUNNING("运行中"),SUSPEND("暂停");
     * @apiParamExample {json} 参数示例:
     * {
     *    'taskListId'：'["124321","233213"]'
     *    'num':100
     * }
     *
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     *  1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/update-cake-supplementary-click-state", method = RequestMethod.POST)
    public ServerResponse updateCakeSupplementaryClickState(String taskListIds,SupplementaryClick supplementaryClick) {
        ServerResponse response = new ServerResponse();
        try {
            JSONArray ids = JSONArray.parseArray(taskListIds);
            response.setData(cakeSupplementaryClickService.updateCakeSupplementaryClickState(JSONArray.toJavaObject(ids,List.class),supplementaryClick));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
