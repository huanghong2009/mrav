package com.mobcolor.ms.kuaima.rest;


import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import com.mobcolor.ms.kuaima.model.KMListModel;
import com.mobcolor.ms.kuaima.model.KMModel;
import com.mobcolor.ms.kuaima.model.dto.KMDTO;
import com.mobcolor.ms.kuaima.model.dto.KMListDTO;
import com.mobcolor.ms.kuaima.service.KMService;
import com.mobcolor.ms.youjia.enums.Platform;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@RestController
@RequestMapping(value = "/km")
public class KMController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(KMController.class);

    @Resource
    private KMService kmService;

    /**
     * @api {post} /km/add-km [快马任务管理]添加一个快马任务
     * @apiSampleRequest /km/add-km
     * @apiName add-km
     * @apiGroup /km
     * @apiDescription [快马任务管理]添加一个快马任务
     * @apiParam {String} name 任务名称
     * @apiParam {String} scriptNames 脚本名称
     * @apiParam {String} taskTotal 总数
     * @apiParam {String} config 自定义配置 ['开时时间-结束时间-比例']
     * @apiParam {String} execTime 执行时间(yyyy-MM-dd)
     * @apiParamExample {json} 参数示例:
     * {
     * 'name':'快马',
     * 'scriptNames':'快马脚本',
     * 'taskTotal':'任务数量',
     * 'config':"['00:00:00-01:00:00-20','01:00:00-02:00:00-30'...]",
     * 'execTime':'2018-09-01'
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {}
     * }
     * }
     */
    @RequestMapping(value = "/add-km", method = RequestMethod.POST)
    public ServerResponse addKM(KMModel KMModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            kmService.createTask(KMModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /km/query-km-by-page [快马任务管理]分页查询快马任务
     * @apiSampleRequest /km/query-km-by-page
     * @apiName query-km-by-page
     * @apiGroup /km
     * @apiDescription [快马任务管理]分页查询快马任务
     * @apiParam {String} [name] 名称(支持模糊搜索)
     * @apiParam {String} [isQueryToDay] 是否只查询今天(y/n)
     * @apiParam {String} [execTime] 查询日期:yyyy-MM-dd(当isQueryToDay !=Y 时,才生效)
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     * name:'爱奇艺',
     * to_page:1,
     * page_size:10
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name 类别名称
     * @apiSuccess {String} createrTime 创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * 'id':'82839383',
     * 'name':'爱奇艺',
     * 'createTime':‘206-0-08  12:35:32’
     * }
     * }
     */
    @RequestMapping(value = "/query-km-by-page", method = RequestMethod.GET)
    public ServerResponse queryKMByPage(KMDTO KMDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(kmService.queryKMModelByPage(KMDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /km/query-km-list-by-page [快马任务管理]分页查询快马任务list
     * @apiSampleRequest /km/query-km-list-by-page
     * @apiName query-km-list-by-page
     * @apiGroup /km
     * @apiDescription [快马任务管理]分页查询快马任务list
     * @apiParam {String} [name] 名称(支持模糊搜索)
     * @apiParam {String} [isQueryToDay] 是否只查询今天(y/n)
     * @apiParam {String} [execTime] 查询日期:yyyy-MM-dd(当isQueryToDay !=Y 时,才生效)
     * @apiParam {String} [taskId] 任务id
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     * name:'爱奇艺',
     * to_page:1,
     * page_size:10
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name 类别名称
     * @apiSuccess {String} createrTime 创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * 'id':'82839383',
     * 'name':'爱奇艺',
     * 'createTime':‘206-0-08  12:35:32’
     * }
     * }
     */
    @RequestMapping(value = "/query-km-list-by-page", method = RequestMethod.GET)
    public ServerResponse queryKMListByPage(KMListDTO kmListDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            response.setData(kmService.queryKMModelListByPage(kmListDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /km/get-task [快马任务管理]获得一条任务
     * @apiSampleRequest /km/get-task
     * @apiName get-task
     * @apiGroup /km
     * @apiDescription [快马任务管理]获得一条任务
     * @apiParam {String} platform ANDROID("安卓"),IOS("苹果");
     * @apiParam {String} srciptName 脚本名称
     * @apiParamExample {json} 参数示例:
     * {
     * <p>
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
    @RequestMapping(value = "/get-task", method = RequestMethod.POST)
    public ServerResponse getTask(Platform platform,String srciptName) {
        ServerResponse response = new ServerResponse();
        try {
            response.setData(kmService.getTask(platform,srciptName));
            if (response.getData() != null){
                logger.info("------getKmTask--:{},{}",platform,response.getData());
            }
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /km/task-call-back [快马任务管理]任务回调
     * @apiSampleRequest /km/task-call-back
     * @apiName task-call-back
     * @apiGroup /km
     * @apiDescription [快马任务管理]任务回调
     * @apiParam {String} taskId 任务id
     * @apiParam {String} taskListId 任务list id
     * @apiParam {String} state 完成状态: SUCCEED("成功"),FAILED("失败"),
     * @apiParam {String} idfa 设备编号
     * @apiParam {String} uuid 随机数（每台设备每次提交都得提交一个新的uuid，否则视为重复提交）
     * @apiParamExample {json} 参数示例:
     * {
     * taskId:'12sw13',
     * taskListId:'122x2221',
     * state:'SUCCEED'
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name 类别名称
     * @apiSuccess {String} createrTime 创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * 'id':'82839383',
     * 'name':'爱奇艺',
     * 'createTime':‘206-0-08  12:35:32’
     * }
     * }
     */
    @RequestMapping(value = "/task-call-back", method = RequestMethod.POST)
    public ServerResponse taskKMCallBack(String taskId, String taskListId, TaskDetail state, String idfa, String uuid) {
        this.loggerParam("------taskKMCallBack---------", taskId, taskListId, state, idfa, uuid);
        ServerResponse response = new ServerResponse();
        try {
            response.setMsg("操作成功");
            response.setCode(ServerResponse.SUCCESS);
            kmService.taskCallBack(taskId, taskListId, state, idfa, uuid);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {POST} /km/update-km-state [快马任务管理]修改任务状态
     * @apiSampleRequest /km/update-km-state
     * @apiName update-km-state
     * @apiGroup /km
     * @apiDescription [快马任务管理]修改任务状态
     * @apiParam {String} taskId 任务id
     * @apiParam {String} state 完成状态: SUCCEED("成功"),FAILED("失败"),
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'fhjj1233'
     * }
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * }
     * }
     */
    @RequestMapping(value = "/update-km-state", method = RequestMethod.POST)
    public ServerResponse updateKMState(String taskId, TaskDetail state) {
        this.loggerParam("updateKMState", taskId, state);
        ServerResponse response = new ServerResponse();

        try {
            kmService.updateKMState(taskId, state);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {POST} /km/update-km-list [快马任务管理]修改任务
     * @apiSampleRequest /km/update-km-list
     * @apiName update-km-list
     * @apiGroup /km
     * @apiDescription [快马任务管理]修改任务状态
     * @apiParam {String} id 任务id
     * @apiParam {String} state 完成状态: SUCCEED("成功"),FAILED("失败"),
     * @apiParam {String} total 任务总数
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'fhjj1233'
     * }
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * }
     * }
     */
    @RequestMapping(value = "/update-km-list", method = RequestMethod.POST)
    public ServerResponse updateKMListState(KMListModel kmListModel) {
        this.loggerParam("updateKMListState", kmListModel);
        ServerResponse response = new ServerResponse();

        try {
            kmService.updateKMListState(kmListModel);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /km/delete-km [快马任务管理]删除任务
     * @apiSampleRequest /km/delete-km
     * @apiName delete-km
     * @apiGroup /km
     * @apiDescription [快马任务管理]删除任务
     * @apiParam {String} taskId 任务id
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskId':'fhjj1233'
     * }
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * }
     * }
     */
    @RequestMapping(value = "/delete-km", method = RequestMethod.POST)
    public ServerResponse deleteKM(String taskId) {
        this.loggerParam("deleteKM", taskId);
        logger.info("---------------deleteKM:{} -----------------", taskId);
        ServerResponse response = new ServerResponse();

        try {
            kmService.deleteKMModel(taskId);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {POST} /km/delete-km-list [快马任务管理]删除任务list
     * @apiSampleRequest /km/delete-km-list
     * @apiName delete-km-list
     * @apiGroup /km
     * @apiDescription [快马任务管理]删除任务list
     * @apiParam {String} id 任务list id
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskId':'fhjj1233'
     * }
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {
     * }
     * }
     */
    @RequestMapping(value = "/delete-km-list", method = RequestMethod.POST)
    public ServerResponse deleteKMList(String id) {
        this.loggerParam("deleteKMList", id);
        ServerResponse response = new ServerResponse();

        try {
            kmService.deleteKMListModel(id);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    public static void main(String[] args) {
        BaseUtils.cerateModelSql(new KMModel());
        BaseUtils.cerateModelSql(new KMListModel());
    }

}
