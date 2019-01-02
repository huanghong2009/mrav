package com.mobcolor.ms.youjia.rest;


import com.mobcolor.ms.youjia.model.AppManageModel;
import com.mobcolor.ms.youjia.model.dto.AppManageDTO;
import com.mobcolor.ms.youjia.service.AppManageService;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.ServerResponse;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@RestController
@RequestMapping(value = "/app-manage")
public class AppManageController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AppManageController.class);

    @Resource
    private AppManageService appManageService;

    /**
     * @api {post} /app-manage/add-app-manage [应用管理]添加一个应用
     * @apiSampleRequest /app-manage/add-app-manage
     * @apiName add-app-manage
     * @apiGroup /app-manage
     * @apiDescription [应用管理]添加一个应用
     * @apiParam {String} name 应用名称
     * @apiParam {String} bundleId 应用bid
     * @apiParam {String} platformChannelId 应用渠道id
     * @apiParam {String} platformChannelName 应用渠道名称
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'网易应用'，
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
    @RequestMapping(value = "/add-app-manage", method = RequestMethod.POST)
    public ServerResponse addAppManage(AppManageModel appManageModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            appManageService.addAppManage(appManageModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /app-manage/query-app-manage-by-page [应用管理]分页查询应用
     * @apiSampleRequest /app-manage/query-app-manage-by-page
     * @apiName query-app-manage-by-page
     * @apiGroup /app-manage
     * @apiDescription [应用管理]分页查询应用
     * @apiParam {String} [name] 名称
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺',
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name  资源名称
     * @apiSuccess {String} createrTime  创建时间
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
    @RequestMapping(value = "/query-app-manage-by-page", method = RequestMethod.POST)
    public ServerResponse queryAppManageByPage(AppManageDTO appManageDTO) {
        ServerResponse response = new ServerResponse();
        try {

            response.setCode(ServerResponse.SUCCESS);
            response.setData(appManageService.queryAppManageByPage(appManageDTO));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /app-manage/update-app-manage [应用管理]修改一个应用
     * @apiSampleRequest /app-manage/update-app-manage
     * @apiName update-app-manage
     * @apiGroup /app-manage
     * @apiDescription 【[应用管理]修改一个应用
     * @apiParam {String} id 应用id主键
     * @apiParam {String} [name] 应用名称
     * @apiParam {String} [bundleId] 应用bid
     * @apiParam {String} [platformChannelId] 应用渠道id
     * @apiParam {String} [platformChannelName] 应用渠道名称
     * @apiParamExample {json} 参数示例:
     * {
     *  id:'12376899'，
     *  'name' ：'网易应用_修改后名字'
     * }
     * @apiSuccessExample {json} 返回示例:
     * <p/>
     * HTTP/1.1 200 OK
     * {
     * code:0,
     * msg:'操作成功',
     * data:{
     * {
     *     1
     * }
     * }
     * }
     */
    @RequestMapping(value = "/update-app-manage", method = RequestMethod.POST)
    public ServerResponse updateAppManage(AppManageModel appManageModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(appManageService.updateAppManage(appManageModel));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
