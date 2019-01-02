package com.mobcolor.ms.timingTask.controller;


import com.mobcolor.ms.timingTask.TimingTaskPool;
import com.mobcolor.ms.timingTask.enums.TimingTaskType;
import com.mobcolor.ms.timingTask.model.TimingTaskModel;
import com.mobcolor.ms.timingTask.service.TimingTaskService;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/25
 */
@RestController
@RequestMapping(value = "/task")
public class TimingTaskController extends BaseRestController {

    @Resource
    private TimingTaskService timingTaskService;

    @Resource
    private TimingTaskPool timingTaskPool;


    /**
     * @api {post} /task/add-task [定时任务]新增定时任务
     * @apiSampleRequest /task/add-task
     * @apiName add-task
     * @apiGroup /task
     * @apiDescription [定时任务]新增定时任务
     * @apiParam {String} taskType DIRECT_EXECUTION("立即执行"),TIMEING_EXECUTION("定时执行"),TIMER_EXECUTION("定时器执行")
     * @apiParam {String} execModel 日期/cron
     * @apiParam {String} beanName bean
     * @apiParam {String} methodName 方法名
     * @apiParamExample {json} 参数示例:
     * {
     *  time:'2019....'
     * }
     * @apiSuccess {String} msg 请求消息
     * @apiSuccess {Object} data 返回数据
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'',
     * data:[
     * {
     * <p>
     * }
     * ]
     * }
     */
    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public ServerResponse addTask(TimingTaskModel timingTaskModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(timingTaskService.addTimingTasks(TimingTaskType.valueOf(
                    timingTaskModel.getTaskType()),
                    timingTaskModel.getExecModel(),
                    timingTaskModel.getBeanName(),
                    timingTaskModel.getMethodName()
                    ));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /task/load-task [定时任务]加载定时任务
     * @apiSampleRequest /task/load-task
     * @apiName load-task
     * @apiGroup /task
     * @apiDescription [定时任务]加载定时任务
     * @apiParam {String} taskId 任务id
     * @apiParamExample {json} 参数示例:
     * {
     *  time:'2019....'
     * }
     * @apiSuccess {String} msg 请求消息
     * @apiSuccess {Object} data 返回数据
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'',
     * data:[
     * {
     * <p>
     * }
     * ]
     * }
     */
    @RequestMapping(value = "/load-task", method = RequestMethod.POST)
    public ServerResponse loadTask(String taskId) {
        ServerResponse response = new ServerResponse();
        try {
            timingTaskPool.loadTask(taskId);
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {post} /task/init-task [定时任务]重新加载任务
     * @apiSampleRequest /task/init-task
     * @apiName init-task
     * @apiGroup /task
     * @apiDescription [定时任务]重新加载任务
     * @apiParamExample {json} 参数示例:
     * {
     *
     * }
     * @apiSuccess {String} msg 请求消息
     * @apiSuccess {Object} data 返回数据
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'',
     * data:[
     * {
     * <p>
     * }
     * ]
     * }
     */
    @RequestMapping(value = "/init-task", method = RequestMethod.POST)
    public ServerResponse initTask() {
        ServerResponse response = new ServerResponse();
        try {
            timingTaskPool.loadTasks();
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

}
