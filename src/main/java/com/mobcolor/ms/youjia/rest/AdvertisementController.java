package com.mobcolor.ms.youjia.rest;


import com.mobcolor.ms.youjia.model.AdvertisementModel;
import com.mobcolor.ms.youjia.model.dto.AdvertisementDTO;
import com.mobcolor.ms.youjia.service.AdvertisementService;
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
@RequestMapping(value = "/advertisement")
public class AdvertisementController extends BaseRestController {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(AdvertisementController.class);

    @Resource
    private AdvertisementService advertisementService;

    /**apimanage
     * @api {post} /advertisement/add-advertisement [广告管理]添加一个广告
     * @apiSampleRequest /advertisement/add-advertisement
     * @apiName add-advertisement
     * @apiGroup /advertisement
     * @apiDescription [广告管理]添加一个广告
     * @apiParam {String} originalAdvertisementId 原广告id
     * @apiParam {String} appName 应用名称
     * @apiParam {String} serverConfig 服务器配置
     * @apiParam {String} platformChannelId 渠道id
     * @apiParam {String} platformChannelName 渠道名称
     * @apiParam {String} clickRulesId 规则id
     * @apiParam {Number} minClickNum 最小补点击数
     * @apiParam {Number} conversionRate 转换率
     * @apiParam {String} scriptNames 脚本名称，多个脚本','分隔
     * @apiParam {String} isNeedRepeatedly 是否需要多次唤醒
     * @apiParam {String} repeatedlyDays 多次唤醒天数
     * @apiParam {String} repeatedlyConfig 多次唤醒配置
     * @apiParam {String} taskLists 留存模版，留存比例数组
     * @apiParam {String} addExecGroup 新增任务组(id&&name)
     * @apiParam {String} retainedExecGroup 留存任务组(id&&name)
     *
     * @apiParamExample {json} 参数示例:
     * {
     *  'originalAdvertisementId':'123125',
     *  'appName' ：'爱奇艺',
     *  'serverConfig':'',
     *  'platformChannelId':'123452',
     *  'platformChannelName' ：'广告主xx',
     *  'scriptNames':'脚本1,脚本2,脚本3',
     *  'taskLists':'[50,45,40,35,30,25,20]',
     *  'clickRulesId':'1233',
     *  'minClickNum':1233,
     *  'conversionRate':100
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
    @RequestMapping(value = "/add-advertisement", method = RequestMethod.POST)
    public ServerResponse addAdvertisement(AdvertisementModel advertisementModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            advertisementService.addAdvertisement(advertisementModel);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * @api {GET} /advertisement/query-advertisement-by-page [广告管理]分页查询广告
     * @apiSampleRequest /advertisement/query-advertisement-by-page
     * @apiName query-advertisement-by-page
     * @apiGroup /advertisement
     * @apiDescription [广告管理]分页查询广告
     * @apiParam {String} [name] 应用名称和渠道名称模糊查询
     * @apiParam {String} [originalAdvertisementId] 原广告id
     * @apiParam {Number} to_page  当前页
     * @apiParam {Number} page_size  每页记录数
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺',
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 广告id
     * @apiSuccess {String} originalAdvertisementId 原广告id
     * @apiSuccess {String} appName 应用名称
     * @apiSuccess {String} bundleId bid
     * @apiSuccess {String} platformChannelId 渠道id
     * @apiSuccess {String} platformChannelName 渠道名称
     * @apiSuccess {String} scriptNames 脚本名称，多个脚本','分隔
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {[
     *      {
     *      'id':'82839383',
     *      'originalAdvertisementId':'12345432',
     *      'appName':'爱奇艺',
     *      'bundleId':'com.weee.wss',
     *      'platformChannelId':'12344',
     *      'platformChannelName':'渠道1',
     *      'scriptNames':'脚本1',
     *      'createTime':‘206-0-08  12:35:32’
     *      }
     *  }
     * }
     */
    @RequestMapping(value = "/query-advertisement-by-page", method = RequestMethod.GET)
    public ServerResponse queryAdvertisementByPage(AdvertisementDTO advertisementDTO) {
        ServerResponse response = new ServerResponse();
        try {
            PageVO<AdvertisementModel> advertisementModelPageVO = advertisementService.queryAdvertisementByPage(advertisementDTO);
            response.setCode(ServerResponse.SUCCESS);
            response.setData(advertisementModelPageVO);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    /**
     * @api {GET} /advertisement/load-advertisement [广告管理]加载一条广告
     * @apiSampleRequest /advertisement/load-advertisement
     * @apiName load-advertisement
     * @apiGroup /advertisement
     * @apiDescription [广告管理]加载一条广告
     * @apiParam {String} id  id
     * @apiParamExample {json} 参数示例:
     * {
     *  name:'爱奇艺',
     *  to_page:1,
     *  page_size:10
     * }
     * @apiSuccess {String} id 广告id
     * @apiSuccess {String} originalAdvertisementId 原广告id
     * @apiSuccess {String} appName 应用名称
     * @apiSuccess {String} bundleId bid
     * @apiSuccess {String} platformChannelId 渠道id
     * @apiSuccess {String} platformChannelName 渠道名称
     * @apiSuccess {String} scriptNames 脚本名称，多个脚本','分隔
     * @apiSuccess {String} createrTime  创建时间
     * @apiSuccessExample {json} 返回示例:
     * HTTP/1.1 200 OK
     * {
     *  "code": 0,
     *  "msg": null,
     *  "data": {
     *
     *    'id':'82839383',
     *    'originalAdvertisementId':'12345432',
     *    'appName':'爱奇艺',
     *    'bundleId':'com.weee.wss',
     *    'platformChannelId':'12344',
     *    'platformChannelName':'渠道1',
     *    'scriptNames':'脚本1',
     *    'createTime':‘206-0-08  12:35:32’
     *
     *  }
     * }
     */
    @RequestMapping(value = "/load-advertisement", method = RequestMethod.GET)
    public ServerResponse loadAdvertisement(String id) {
        ServerResponse response = new ServerResponse();
        try {
            AdvertisementModel advertisementModelPageVO = advertisementService.loadAdvertisement(id);
            response.setCode(ServerResponse.SUCCESS);
            response.setData(advertisementModelPageVO);
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }



    /**
     * @api {post} /advertisement/update-advertisement [广告管理]修改广告内容
     * @apiSampleRequest /advertisement/update-advertisement
     * @apiName update-advertisement
     * @apiGroup /advertisement
     * @apiDescription [广告管理]修改一个广告内容
     * @apiParam {String} [id] id
     * @apiParam {String} [originalAdvertisementId] 原广告id
     * @apiParam {String} [appName] 应用名称
     * @apiParam {String} [serverConfig] 服务配置
     * @apiParam {String} [platformChannelId] 渠道id
     * @apiParam {String} [platformChannelName] 渠道名称
     * @apiParam {String} [clickRulesId] 规则id
     * @apiParam {Number} [minClickNum] 最小补点击数
     * @apiParam {Number} [conversionRate] 转换率
     * @apiParam {String} [scriptNames] 脚本名称，多个脚本','分隔
     * @apiParam {String} [taskLists] 留存模版，留存比例数组
     * @apiParamExample {json} 参数示例:
     * {
     *  'id':'12376899'，
     *  'content' ：'mmjshhsjjjsh'
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
    @RequestMapping(value = "/update-advertisement", method = RequestMethod.POST)
    public ServerResponse updateAdvertisement(AdvertisementModel advertisementModel) {
        ServerResponse response = new ServerResponse();
        try {
            response.setCode(ServerResponse.SUCCESS);
            response.setMsg("操作成功");
            response.setData(advertisementService.updateAdvertisement(advertisementModel));
        } catch (BusinessException e) {
            response.setCode(ServerResponse.ERROR_BBUSINESS);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
