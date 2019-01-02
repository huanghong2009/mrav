package com.mobcolor.ms.youjia.rest;

import com.mobcolor.ms.youjia.enums.Platform;
import com.mobcolor.ms.youjia.model.PlatformChannelModel;
import com.mobcolor.ms.youjia.model.dto.PlatformChannelDTO;
import com.mobcolor.ms.youjia.service.PlatformService;
import com.mobcolor.framework.common.BaseRestController;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.common.PageVO;
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
@RequestMapping(value = "/platform")
public class PlatformController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(PlatformController.class);

    @Resource
    private PlatformService platformService;


    /**
     * @api {post} /platform/add-platform [渠道管理]添加一个渠道
     * @apiSampleRequest /platform/add-platform
     * @apiName add-platform
     * @apiGroup /platform
     * @apiDescription [渠道管理]添加一个渠道
     * @apiParam {String} name 渠道名称
     * @apiParam {String} platform 平台   ANDROID("安卓")，IOS("苹果")
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺'，
     *  platform ：'IOS'
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
    @RequestMapping(value = "/add-platform", method = RequestMethod.POST)
    public ServerResponse addPlatform(String name, Platform platform) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            platformService.addPlatform(name,platform);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /platform/query-platform-by-page [渠道管理]分页查询渠道
     * @apiSampleRequest /platform/query-platform-by-page
     * @apiName query-platform-by-page
     * @apiGroup /platform
     * @apiDescription [渠道管理]分页查询渠道
     * @apiParam {String} [name] 渠道名称
     * @apiParam {String} [platform] 平台 ANDROID("安卓")，IOS("苹果")
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺',
     *  platform:'ANDROID',
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name  资源名称
     * @apiSuccess {String} platform  平台 ANDROID("安卓")，IOS("苹果")
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'platform':'IOS'
     *      'createTime':‘206-0-08  12:35:32’
     *  }
     * }
     */
    @RequestMapping(value = "/query-platform-by-page", method = RequestMethod.POST)
    public ServerResponse queryPlatformByPage(PlatformChannelDTO platformChannelDTO) {
        ServerResponse response = new ServerResponse();
        try {
            PageVO<PlatformChannelModel> platformChannelModelPageVO = platformService.queryPlatformByPage(platformChannelDTO);
            response.setCode(ServerResponse.SUCCESS);
            response.setData(platformChannelModelPageVO);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {post} /platform/update-platform [渠道管理]修改一个渠道名称
     * @apiSampleRequest /platform/update-platform
     * @apiName update-platform
     * @apiGroup /platform
     * @apiDescription [渠道管理]修改一个渠道名称
     * @apiParam {String} id 渠道id主键
     * @apiParam {String} name 渠道名称
     * @apiParamExample {json} 参数示例:
     * {
     *  id:'12376899'，
     *  'name' ：'爱奇艺_修改后名字'
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
    @RequestMapping(value = "/update-platform", method = RequestMethod.POST)
    public ServerResponse updatePlatform(String id,String name) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(platformService.updatePlatform(id,name));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {POST} /platform/select-platform-list [渠道管理]查询渠道
     * @apiSampleRequest /platform/select-platform-list
     * @apiName select-platform-list
     * @apiGroup /platform
     * @apiDescription [渠道管理]查询渠道
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺',
     *  platform:'ANDROID',
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 渠道id
     * @apiSuccess {String} name  资源名称
     * @apiSuccess {String} platform  平台 ANDROID("安卓")，IOS("苹果")
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *      'id':'82839383',
     *      'name':'爱奇艺',
     *      'platform':'IOS'
     *      'createTime':‘206-0-08  12:35:32’
     *  }
     * }
     */
    @RequestMapping(value = "/select-platform-list", method = RequestMethod.POST)
    public ServerResponse queryPlatformByPage() {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setData(platformService.selectPlatformChannelModelList());
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
