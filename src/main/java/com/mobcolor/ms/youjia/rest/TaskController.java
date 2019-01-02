package com.mobcolor.ms.youjia.rest;


import com.mobcolor.ms.youjia.model.CakeDockModel;
import com.mobcolor.ms.youjia.model.TaskDetailModel;
import com.mobcolor.ms.youjia.model.TaskListModel;
import com.mobcolor.ms.youjia.model.dto.*;
import com.mobcolor.ms.youjia.service.*;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/20
 */
@RestController
@RequestMapping(value = "/tasks")
public class TaskController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(TaskController.class);

    @Resource
    private TaskService taskService;

    @Resource
    private TaskDetailService taskDetailService;

    @Resource
    private TaskListService taskListService;

    @Resource
    private TaskForcetService taskForcetService;

    @Resource
    private CakeDockService cakeDockService;

    private HashSet<String> taskDetailIds = new HashSet<>();

    /**
     * @api {post} /tasks/add-tasks [任务管理]添加一个任务
     * @apiSampleRequest /tasks/add-tasks
     * @apiName add-tasks
     * @apiGroup /tasks
     * @apiDescription [任务管理]添加一个任务
     * @apiParam {String} num 任务新增执行次数
     * @apiParam {String} isNeedCallBack 是否需要回调
     * @apiParam {String} startTime 开始执行时间 yyyy-MM-dd
     * @apiParam {String} advertisementId 内部广告id
     * @apiParam {String} patchClicksType    补点击方式 ADVERTISER_CALL_BACK("激活回调"),DEVICE_CALL_BACK("设备回调");
     * @apiParamExample {json} 参数示例:
     * * {
     * 'startTime':'2018-01-08'
     * 'num':'200'
     * 'isNeedCallBack':'Y'
     * 'advertisementId'：'133132'
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
    @RequestMapping(value = "/add-tasks", method = RequestMethod.POST)
    public ServerResponse addTask(TaskDTO taskDTO) {
        this.loggerParam("addTask", taskDTO);
        ServerResponse response = new ServerResponse();
        try {
            taskService.addTask(taskDTO);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /tasks/create-today-task [任务管理]创建今天的任务详细
     * @apiSampleRequest /tasks/create-today-task
     * @apiName create-today-task
     * @apiGroup /tasks
     * @apiDescription [任务管理]创建今天的任务详细
     * @apiParam {String} taskId 任务id
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskId':'12336'
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
    @RequestMapping(value = "/create-today-task", method = RequestMethod.POST)
    public ServerResponse createTodayTask(String taskId) {
        this.loggerParam("createTodayTask", taskId);
        ServerResponse response = new ServerResponse();
        try {
            taskService.createTodayTask(taskId);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /tasks/load-repeatedly-data [任务管理]加载任务多次唤醒
     * @apiSampleRequest /tasks/load-repeatedly-data
     * @apiName load-repeatedly-data
     * @apiGroup /tasks
     * @apiDescription [任务管理]加载任务多次唤醒
     * @apiParam {String} taskId 任务id
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskId':'12336'
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
    @RequestMapping(value = "/load-repeatedly-data", method = RequestMethod.POST)
    public ServerResponse loadRepeatedlyData(String taskId) {
        this.loggerParam("loadRepeatedlyData", taskId);
        ServerResponse response = new ServerResponse();
        try {
            taskService.loadRepeatedlyData(taskId);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /tasks/add-task-to-list [任务管理]将任务重新放入消息队列
     * @apiSampleRequest /tasks/add-task-to-list
     * @apiName add-task-to-list
     * @apiGroup /tasks
     * @apiParam {String} id 任务id
     * @apiDescription [任务管理]将任务重新放入消息队列
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'12scx2'
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
    @RequestMapping(value = "/add-task-to-list", method = RequestMethod.POST)
    public ServerResponse addTaskToList(String id) {
        ServerResponse response = new ServerResponse();
        try {
            taskService.addTaskToList(id);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /tasks/load-today-task [任务管理]加载今天的任务详细
     * @apiSampleRequest /tasks/load-today-task
     * @apiName load-today-task
     * @apiGroup /tasks
     * @apiDescription [任务管理]加载今天的任务详细
     * @apiParamExample {json} 参数示例:
     * {
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
    @RequestMapping(value = "/load-today-task", method = RequestMethod.POST)
    public ServerResponse loadTodayTask() {
        ServerResponse response = new ServerResponse();
        try {
            taskService.loadTodayTask();
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /tasks/get-task [任务管理]获得一条任务
     * @apiSampleRequest /tasks/get-task
     * @apiName get-task
     * @apiGroup /tasks
     * @apiDescription [任务管理]获得一条任务
     * @apiParam {String} deviceNumber 设备编号(唯一)
     * @apiParamExample {json} 参数示例:
     * {
     * 'device'：'2edw2f'
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
    public ServerResponse getTask(String deviceNumber) {
        ServerResponse response = new ServerResponse();
        try {
            response.setData(taskService.getTask(deviceNumber));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /tasks/task-call-back-device [任务管理]任务回调-设备
     * @apiSampleRequest /tasks/task-call-back-device
     * @apiName task-call-back-device
     * @apiGroup /tasks
     * @apiDescription [任务管理]任务回调-设备
     * @apiParam {String} id 任务id
     * @apiParam {String} taskListId 任务list id
     * @apiParam {String} state 任务状态 SUCCEED("成功"),FAILED("失败"),
     * @apiParam {String} [device] 设备信息
     * @apiParam {String} [otherInfo] 其他信息
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'fhjj1233',
     * 'state':'SUCCEED',
     * 'state':'ft821',
     * 'otherInfo':'你自己想存什么什么，你怎么存，后台怎么返回给你'
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
    @RequestMapping(value = "/task-call-back-device", method = RequestMethod.POST)
    public ServerResponse updateTaskDetail(TaskDetailModel taskDetailModel) {
        this.loggerParam("updateTaskDetail{}", taskDetailModel.getId(), taskDetailModel);
        ServerResponse response = new ServerResponse();
        try {
            String taskListId = taskDetailModel.getTaskListId();
            response.setData(taskDetailService.updateTaskDetail(taskDetailModel));
            /**
             * 任务进度+1
             */
            if (!taskDetailIds.contains(taskDetailModel.getId())) {
                taskDetailIds.add(taskDetailModel.getId());
                taskListService.updateTaskListTaskScheduleStart(taskListId);
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
     * @api {get} /tasks/task-call-back-advertiser [任务管理]任务回调-广告主
     * @apiSampleRequest /tasks/task-call-back-advertiser
     * @apiName task-call-back-advertiser
     * @apiGroup /tasks
     * @apiDescription [任务管理]任务回调-广告主
     * @apiParam {String} id 任务id
     * @apiParamExample {json} 参数示例:
     * {
     * 'id':'fhjj1233'
     * }
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {1
     * }
     * }
     */
    @RequestMapping(value = "/task-call-back-advertiser", method = RequestMethod.GET)
    public ServerResponse updateTaskDetailAdvertiser(TaskDetailModel taskDetailModel) {
        this.loggerParam("updateTaskDetailAdvertiser{}", taskDetailModel.getId(), taskDetailModel);
        logger.info("---------------task-call-back-advertiser:{} -----------------", taskDetailModel.getId());
        ServerResponse response = new ServerResponse();
        try {
            taskDetailModel.setIsCallBcak("Y");
            response.setData(taskDetailService.updateTaskDetail(taskDetailModel));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {get} /tasks/task-call-back-cake [任务管理]任务回调-cake
     * @apiSampleRequest /tasks/task-call-back-cake
     * @apiName task-call-back-cake
     * @apiGroup /tasks
     * @apiDescription [任务管理]任务回调-cake
     * @apiParam {String} idfa idfa
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
    @RequestMapping(value = "/task-call-back-cake", method = RequestMethod.GET)
    public ServerResponse updateTaskDetailCake(String idfa) {
        this.loggerParam("updateTaskDetailCake", idfa);
        ServerResponse response = new ServerResponse();
        logger.info("---------------task-call-back-cake:{} -----------------", idfa);
        try {
            CakeDockModel cakeDockModel = cakeDockService.loadCakeDock(idfa);
            TaskDetailModel taskDetailModel = new TaskDetailModel();
            if (cakeDockModel == null) {
                logger.error("----------------cake对接失败:没有查询到idfa:{}---------", idfa);
                throw new BusinessException("cake对接失败:没有查询到idfa");
            }
            taskDetailModel.setId(cakeDockModel.getTaskDetailId());
            taskDetailModel.setIsCallBcak("Y");

            response.setData(taskDetailService.updateTaskDetail(taskDetailModel));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /tasks/query-task-details-by-page [任务管理]查询任务详细
     * @apiSampleRequest /tasks/query-task-details-by-page
     * @apiName query-task-details-by-page
     * @apiGroup /tasks
     * @apiDescription [任务管理]查询任务详细
     * @apiParam {String} [taskForcetId] 任务组id
     * @apiParam {String} [taskListId] 任务list id
     * @apiParam {String} [PatchClicksType] 任务类型 RETAINED("留存")/ADD("新增")
     * @apiParam {String} [state] 任务状态 WAIT("等待"),SUCCEED("成功"),FAILED("失败"),SUSPEND("暂停");
     * @apiParam {String} [createrTime] 创建时间
     * @apiParam {String} [isCallBcak] 是否收到回调Y/N
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     * 'PatchClicksType':'RETAINED',
     * 'state':'WAIT',
     * 'to_page':1,
     * 'page_size':10
     * }
     * @apiSuccess {String} taskForcetId 任务组id
     * @apiSuccess {String} taskListId  任务list id
     * @apiSuccess {String} name App名称或渠道名称模糊查询
     * @apiSuccess {String} serverConfig 服务配置
     * @apiSuccess {String} PatchClicksType 任务类型 RETAINED("留存")/ADD("新增")
     * @apiSuccess {String} state 任务状态 WAIT("等待"),SUCCEED("成功"),FAILED("失败"),SUSPEND("暂停");
     * @apiSuccess {String} isCallBcak 是否收到回调 Y/N
     * @apiSuccess {String} scriptNames 脚本名称
     * @apiSuccess {String} device 设备信息
     * @apiSuccess {String} otherInfo 其他信息
     * @apiSuccess {String} updateTime 最后修改时间
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {[
     * {
     * 'taskForcetId':'82839383',
     * 'taskListId':'768i611',
     * 'appName':'爱奇艺',
     * 'serverConfig':'com.aiqiyi.com',
     * 'PatchClicksType':'RETAINED',
     * 'state':'WAIT',
     * 'isCallBcak':'N',
     * 'scriptNames':‘主脚本’,
     * 'device':'xxxxssssss',
     * 'otherInfo':'这是一个备注',
     * 'updateTime':'206-0-08  12:35:32',
     * 'createrTime':‘206-0-08  12:35:32’
     * }
     * }
     * }
     */
    @RequestMapping(value = "/query-task-details-by-page", method = RequestMethod.GET)
    public ServerResponse queryTaskDetailsByPage(TaskDetailDTO taskDetailDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setData(taskDetailService.queryTaskDetailsByPage(taskDetailDTO));
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /tasks/count-task-details-by-page [任务管理]统计任务详细
     * @apiSampleRequest /tasks/count-task-details-by-page
     * @apiName count-task-details-by-page
     * @apiGroup /tasks
     * @apiDescription [任务管理]统计任务详细
     * @apiParam {String} [taskListId] 任务list id
     * @apiParam {String} [name] 任务名称前缀模糊查询
     * @apiParam {String} [countType]  统计类型  DAY("今天"),WEEK("本周")，MONTH("本月"),CUSTOM("自定义时间段");
     * @apiParam {String} [startTime] 创建时间
     * @apiParam {String} [endTime] 结束时间
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     * 'PatchClicksType':'RETAINED',
     * 'state':'WAIT',
     * 'to_page':1,
     * 'page_size':10
     * }
     * @apiSuccess {String} taskForcetId 任务组id
     * @apiSuccess {String} taskListId  任务list id
     * @apiSuccess {String} name App名称或渠道名称模糊查询
     * @apiSuccess {String} serverConfig 服务配置
     * @apiSuccess {String} PatchClicksType 任务类型 RETAINED("留存")/ADD("新增")
     * @apiSuccess {String} state 任务状态 WAIT("等待"),SUCCEED("成功"),FAILED("失败"),SUSPEND("暂停");
     * @apiSuccess {String} isCallBcak 是否收到回调 Y/N
     * @apiSuccess {String} scriptNames 脚本名称
     * @apiSuccess {String} device 设备信息
     * @apiSuccess {String} otherInfo 其他信息
     * @apiSuccess {String} updateTime 最后修改时间
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {[
     * {
     * 'taskForcetId':'82839383',
     * 'taskListId':'768i611',
     * 'appName':'爱奇艺',
     * 'serverConfig':'com.aiqiyi.com',
     * 'PatchClicksType':'RETAINED',
     * 'state':'WAIT',
     * 'isCallBcak':'N',
     * 'scriptNames':‘主脚本’,
     * 'device':'xxxxssssss',
     * 'otherInfo':'这是一个备注',
     * 'updateTime':'206-0-08  12:35:32',
     * 'createrTime':‘206-0-08  12:35:32’
     * }
     * }
     * }
     */
    @RequestMapping(value = "/count-task-details-by-page", method = RequestMethod.GET)
    public ServerResponse countTaskDetailsByPage(TaskDetailDTO taskDetailDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setData(taskDetailService.countTaskDetailsByPage(taskDetailDTO));
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /tasks/task-batch-abled [任务管理]任务批量启用/暂停
     * @apiSampleRequest /tasks/task-batch-abled
     * @apiName task-batch-abled
     * @apiGroup /tasks
     * @apiDescription [任务管理]任务批量启用/暂停
     * @apiParam {String} id 任务list id
     * @apiParam {String} state 任务状态 RUNNING("运行"),SUSPEND("暂停"),
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskListId':'fhjj1233',
     * 'state':'SUSPEND',
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
    @RequestMapping(value = "/task-batch-abled", method = RequestMethod.POST)
    public ServerResponse batchAbled(TaskListModel taskListModel) {
        this.loggerParam("BatchAbled", taskListModel);
        ServerResponse response = new ServerResponse();
        try {
            response.setData(taskService.batchAbled(taskListModel.getId(), taskListModel.getState()));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /tasks/task-list-reset [任务管理]任务list重置
     * @apiSampleRequest /tasks/task-list-reset
     * @apiName task-list-reset
     * @apiGroup /tasks
     * @apiDescription [任务管理]任务list重置
     * @apiParam {String} taskForcetId 任务组 id
     * @apiParam {String} execTime 执行时间(yyyy-MM-dd)
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskForcetId':'fhjj1233',
     * 'execTime':'2018-05-23'
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
    @RequestMapping(value = "/task-list-reset", method = RequestMethod.POST)
    public ServerResponse taskListReset(String taskForcetId, String execTime) {
        this.loggerParam("taskListReset", taskForcetId, execTime);
        ServerResponse response = new ServerResponse();
        try {
            taskService.listReset(taskForcetId, execTime);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /tasks/task-all-abled [任务管理]任务全部启用/暂停
     * @apiSampleRequest /tasks/task-all-abled
     * @apiName task-all-abled
     * @apiGroup /tasks
     * @apiDescription [任务管理]任务批量启用/暂停
     * @apiParam {String} state 任务状态 RUNNING("运行"),SUSPEND("暂停"),
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskListId':'fhjj1233',
     * 'state':'SUSPEND',
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
    @RequestMapping(value = "/task-all-abled", method = RequestMethod.POST)
    public ServerResponse allAbled(TaskListModel taskListModel) {
        this.loggerParam("BatchAbled", taskListModel);
        ServerResponse response = new ServerResponse();
        try {
            response.setData(taskService.allAbled(taskListModel.getState()));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /tasks/exec-query-sql [独立功能]执行查询sql
     * @apiSampleRequest /tasks/exec-query-sql
     * @apiName exec-query-sql
     * @apiGroup /other/demand
     * @apiDescription 【独立功能】读取一个账号
     * @apiParam {String} sql 查询sql
     * @apiParamExample {json} 参数示例:
     * {
     * sql:"select * from"
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * [
     * 13134562357
     * ]
     * }
     * }
     */
    @RequestMapping(value = "/exec-query-sql", method = RequestMethod.POST)
    public ServerResponse execQuerySql(String sql) {
        this.loggerParam("execQuerySql", sql);
        ServerResponse response = new ServerResponse();
        try {
            response.setData(taskService.execQuerySql(sql));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /tasks/query-task-forcet-by-page [任务管理]查询任务组-分页
     * @apiSampleRequest /tasks/query-task-forcet-by-page
     * @apiName query-task-forcet-by-page
     * @apiGroup /tasks
     * @apiDescription [任务管理]查询任务组-分页
     * @apiParam {String} [originalAdvertisementId] 原广告id
     * @apiParam {String} [name] app 名称或渠道名称
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     * 'originalAdvertisementId':'1234455',
     * 'name':'抖音视频',
     * 'to_page':1,
     * 'page_size':10
     * }
     * @apiSuccess {String} id 任务组id
     * @apiSuccess {String} appName App名称
     * @apiSuccess {String} serverConfig 服务配置
     * @apiSuccess {String} platformChannelId 渠道id
     * @apiSuccess {String} platformChannelName 渠道名称
     * @apiSuccess {String} scriptNames 脚本名称
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {[
     * {
     * 'id':'82839383',
     * 'appName':'爱奇艺',
     * 'serverConfig':'com.aiqiyi.com',
     * 'platformChannelId':'12233',
     * 'platformChannelName':'papay',
     * 'scriptNames':‘主脚本’,
     * 'createrTime':‘206-0-08  12:35:32’
     * }
     * }
     * }
     */
    @RequestMapping(value = "/query-task-forcet-by-page", method = RequestMethod.GET)
    public ServerResponse queryTaskForcetByPage(TaskForcetDTO taskForcetDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setData(taskForcetService.queryTaskForcetByPage(taskForcetDTO));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /tasks/query-task-list-by-page [任务管理]查询任务list-分页
     * @apiSampleRequest /tasks/query-task-list-by-page
     * @apiName query-task-list-by-page
     * @apiGroup /tasks
     * @apiDescription [任务管理]查询任务list-分页
     * @apiParam {String} [execTime] 执行时间
     * @apiParam {String} [name] app 名称或渠道名称
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     * 'execTime':'2017-01-11',
     * 'name':'抖音视频',
     * 'to_page':1,
     * 'page_size':10
     * }
     * @apiSuccess {String} id 任务组id
     * @apiSuccess {String} appName App名称
     * @apiSuccess {String} serverConfig 服务配置
     * @apiSuccess {String} platformChannelId 渠道id
     * @apiSuccess {String} platformChannelName 渠道名称
     * @apiSuccess {String} scriptNames 脚本名称
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * "code": 0,
     * "msg": null,
     * "data": {[
     * {
     * 'id':'82839383',
     * 'appName':'爱奇艺',
     * 'serverConfig':'com.aiqiyi.com',
     * 'platformChannelId':'12233',
     * 'platformChannelName':'papay',
     * 'scriptNames':‘主脚本’,
     * 'createrTime':‘206-0-08  12:35:32’
     * }
     * }
     * }
     */
    @RequestMapping(value = "/query-task-list-by-page", method = RequestMethod.GET)
    public ServerResponse queryTaskLIstByPage(TaskListDTO taskListDTO) {
        ServerResponse response = new ServerResponse();
        try {
            response.setData(taskListService.queryTaskListByPage(taskListDTO));
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /tasks/update-is-need-call-back [任务管理]修改是否需要回调
     * @apiSampleRequest /tasks/update-is-need-call-back
     * @apiName update-is-need-call-back
     * @apiGroup /tasks
     * @apiDescription [任务管理]修改是否需要回调
     * @apiParam {String} taskForcetId 任务组 id
     * @apiParam {String} isNeedCallBack 是否需要回调(Y/N)
     * @apiParamExample {json} 参数示例:
     * {
     * 'taskForcetId':'fhjj1233',
     * 'isNeedCallBack':'Y'
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
    @RequestMapping(value = "/update-is-need-call-back", method = RequestMethod.POST)
    public ServerResponse updateIsNeedCallBack(String taskForcetId, String isNeedCallBack) {
        this.loggerParam("updateIsNeedCallBack", taskForcetId, isNeedCallBack);
        ServerResponse response = new ServerResponse();
        try {
            taskListService.updateIsNeedCallBack(taskForcetId, isNeedCallBack);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (Exception e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

}
